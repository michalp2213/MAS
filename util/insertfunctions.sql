CREATE OR REPLACE FUNCTION nulls(VARIADIC a ANYARRAY)
  RETURNS INTEGER
AS $$
DECLARE amount NUMERIC;
        i      INTEGER;
BEGIN
  amount = 0;
  FOR i IN 1..array_length(a, 1) LOOP
    IF a [i] IS NULL
    THEN amount = amount + 1;
    END IF;
  END LOOP;
  RETURN amount;
END;
$$
language plpgsql;

CREATE OR REPLACE FUNCTION role_pracownika(id INTEGER)
  RETURNS TABLE(id_roli INTEGER, nazwa VARCHAR)
AS $$
BEGIN
  RETURN QUERY
  SELECT r.*
  FROM pracownicy_role p
    JOIN role r ON p.id_roli = r.id_roli AND p.id_pracownika = id;
END;
$$
language plpgsql;

CREATE OR REPLACE FUNCTION specjalizacje_lekarza(id INTEGER)
  RETURNS TABLE(id_specjalizazji INTEGER, nazwa VARCHAR)
AS $$
BEGIN
  RETURN QUERY
  SELECT s.*
  FROM lekarze_specjalizacje ls
    JOIN specjalizacje s ON ls.id_specjalizacji = s.id_specjalizacji AND ls.id_lekarza = id;
END;
$$
language plpgsql;

CREATE OR REPLACE FUNCTION wizyte_odbyte_pacjenta(id INTEGER)
  RETURNS TABLE(id_wizyty  INTEGER, id_pacjenta INTEGER,
                id_lekarza INTEGER, cel INTEGER, specjalizacja INTEGER, data TIMESTAMP, czas_trwania INTERVAL)
AS $$
BEGIN
  RETURN QUERY
  SELECT *
  FROM wizyty_odbyte w
  WHERE w.id_pacjenta = id;
END;
$$
language plpgsql;

CREATE OR REPLACE FUNCTION wizyte_planowane_pacjenta(id INTEGER)
  RETURNS TABLE(id_wizyty  INTEGER, id_pacjenta INTEGER,
                id_lekarza INTEGER, cel INTEGER, specjalizacja INTEGER, data TIMESTAMP, szacowany_czas INTERVAL)
AS $$
BEGIN
  RETURN QUERY
  SELECT *
  FROM wizyty_planowane w
  WHERE w.id_pacjenta = id;
END;
$$
language plpgsql;

CREATE OR REPLACE FUNCTION czy_aktywny_lekarz(id INTEGER, d1 DATE, d2 DATE)
  RETURNS BOOLEAN AS
$$
DECLARE
  zod DATE;
  zdo DATE;
BEGIN
  zod = (SELECT zatrudniony_od
         FROM pracownicy
         WHERE id_pracownika = id);
  zdo = (SELECT zatrudniony_do
         FROM pracownicy
         WHERE id_pracownika = id);
  RETURN (d1 IS NULL OR zod <= d1) AND (zdo IS NULL OR zdo >= d2) AND (SELECT count(*)
                                                                       FROM pracownicy_role
                                                                       WHERE id_pracownika = id AND id_roli = 1) > 0;
END;
$$
language plpgsql;

CREATE OR REPLACE FUNCTION pesel_check(p CHAR(11))
  RETURNS BOOLEAN AS
$$
DECLARE
  pesel CHAR [];
  s     INTEGER;
BEGIN
  pesel = string_to_array(p, NULL);
  s =
  (pesel [1] :: INT) + (pesel [2] :: INT) * 3 + (pesel [3] :: INT) * 7 + (pesel [4] :: INT) * 9 + pesel [5] :: INT * 1 +
  pesel [6] :: INT * 3 + pesel [7] :: INT * 7 + pesel [8] :: INT * 9 + pesel [9] :: INT + pesel [10] :: INT * 3;
  s = s % 10;
  RETURN (10 - s) = pesel [11] :: INT;
END;
$$
language plpgsql;

CREATE OR REPLACE FUNCTION pacjent_check()
  RETURNS TRIGGER AS $pacjent_check$
DECLARE pesel CHAR [];
        p     INTEGER;
        d     DATE;
BEGIN
  IF NEW.pesel IS NULL OR length(NEW.pesel) <> 11
  THEN RETURN NEW; END IF;
  IF pesel_check(NEW.pesel) = FALSE
  THEN RAISE EXCEPTION 'Niepoprawny PESEL';
  END IF;
  pesel = string_to_array(NEW.pesel, NULL);
  p = pesel [10] :: INTEGER;
  IF (p % 2 = 0 AND NEW.plec = 'M') OR (p % 2 = 1 AND NEW.plec = 'F')
  THEN RAISE EXCEPTION 'Zla plec';
  END IF;
  p = pesel [3] :: INT * 10 + pesel [4] :: INT;
  IF p > 20
  THEN
    d = DATE('20' || pesel [1] || pesel [2] || '.' || (p - 20) :: TEXT || '.' || pesel [5] || pesel [6]);
  ELSE
    d = DATE('19' || pesel [1] || pesel [2] || '.' || p :: TEXT || '.' || pesel [5] || pesel [6]);
  END IF;
  IF d <> NEW.data_urodzenia
  THEN
    RAISE EXCEPTION 'Nie poprawna data urodzenia';
  END IF;
  RETURN NEW;
END;
$pacjent_check$
language plpgsql;

CREATE TRIGGER pacjent_check
  BEFORE INSERT OR UPDATE
  ON pacjenci
  FOR EACH ROW EXECUTE PROCEDURE pacjent_check();

CREATE OR REPLACE FUNCTION pracownik_check()
  RETURNS TRIGGER AS $pracownik_check$
DECLARE r  RECORD;
        id INTEGER;
        d  TIMESTAMP;
        m  DATE;
BEGIN
  IF pesel_check(NEW.pesel) = FALSE
  THEN RAISE EXCEPTION 'Niepoprawny PESEL';
  END IF;
  IF NEW.zatrudniony_do IS NULL OR czy_aktywny_lekarz(NEW.id_pracownika, null, null) = FALSE
  THEN RETURN NEW;
  END IF;
  IF (SELECT count(*)
      FROM wizyty_planowane
      WHERE id_lekarza = NEW.id_pracownika AND data + szacowany_czas > NEW.zatrudniony_do) = 0
  THEN RETURN NEW;
  END IF;
  IF (SELECT count(*)
      FROM specjalizacje_lekarza(NEW.id_pracownika) s
      WHERE (SELECT count(*)
             FROM lekarze_specjalizacje ls
             WHERE ls.id_specjalizacji = s.id_specjalizazji AND ls.id_lekarza <> NEW.id_pracownika) = 0 AND
            (SELECT count(*)
             FROM wizyty_planowane
             WHERE specjalizacja = s.id_specjalizazji AND data + szacowany_czas > NEW.zatrudniony_do) > 0) > 0
  THEN RAISE EXCEPTION 'Nie da sie przeniesc wizyte';
  END IF;
  m = (SELECT "do"
       FROM pacjenci_lpk
       WHERE id_lekarza = NEW.id_pracownika AND "do" > NEW.zatrudniony_do
       ORDER BY "do" DESC NULLS FIRST
       LIMIT 1);
  IF (SELECT count(*)
      FROM pracownicy
      WHERE czy_aktywny_lekarz(id_pracownika, DATE(current_timestamp + INTERVAL '1 DAY'), m)) = 0
  THEN RAISE EXCEPTION 'Nie da sie zmienic lpk';
  END IF;
  FOR r IN (SELECT
              id_wizyty      AS idw,
              specjalizacja  AS spec,
              data,
              szacowany_czas AS sc
            FROM wizyty_planowane
            WHERE id_lekarza = NEW.id_pracownika AND data + szacowany_czas > NEW.zatrudniony_do) LOOP
    id = -1;
    WHILE id = -1 LOOP
      id = coalesce((SELECT ls.id_lekarza
                     FROM lekarze_specjalizacje ls
                     WHERE ls.id_specjalizacji = r.spec AND
                           czy_aktywny_lekarz(ls.id_lekarza, DATE(r.data), DATE(r.data + r.sc))
                           AND (SELECT count(*)
                                FROM wizyty_planowane w
                                WHERE ls.id_lekarza = w.id_lekarza AND
                                      (w.data, w.data + w.szacowany_czas)
                                      OVERLAPS
                                      (r.data, r.data + r.sc)) = 0
                     ORDER BY id_lekarza
                     LIMIT 1), -1);
      IF id = -1
      THEN
        r.data = r.data + INTERVAL '1 DAY';
      END IF;
    END LOOP;
    UPDATE wizyty_planowane
    SET id_lekarza = id, data = r.data
    WHERE id_wizyty = r.idw;
  END LOOP;

  FOR r IN (SELECT
              p.id_pacjenta AS idp,
              p.od,
              p."do"
            FROM pacjenci_lpk p
            WHERE p.id_lekarza = NEW.id_pracownika AND (p."do" IS NULL OR p."do" > NEW.zatrudniony_do)) LOOP
    id = (SELECT p.id_pracownika
          FROM pracownicy p
          WHERE czy_aktywny_lekarz(p.id_pracownika, DATE(NEW.zatrudniony_do + INTERVAL '1 DAY'), r."do") AND
                p.id_pracownika <> NEW.id_pracownika
          LIMIT 1);
    m = r."do";
    INSERT INTO pacjenci_lpk VALUES (r.idp, id, DATE(NEW.zatrudniony_do), m);
  END LOOP;
  RETURN NEW;

END;
$pracownik_check$
language plpgsql;

CREATE TRIGGER pracownik_check
  BEFORE INSERT OR UPDATE
  ON pracownicy
  FOR EACH ROW EXECUTE PROCEDURE pracownik_check();

CREATE OR REPLACE FUNCTION pacjent_lpk_check()
  RETURNS TRIGGER AS $pacjent_lpk_check$
DECLARE
  d1 DATE;
  d2 DATE;
BEGIN
  IF czy_aktywny_lekarz(NEW.id_lekarza, NEW.od, NEW."do") = FALSE
  THEN RAISE EXCEPTION 'Nie aktywny lekarz';
  END IF;
  d1 = NEW.od;
  d2 = coalesce(New."do", DATE(current_timestamp + INTERVAL '1000 YEARS'));
  IF (TG_OP = 'INSERT')
  THEN
    IF (SELECT count(*)
        FROM pacjenci_lpk
        WHERE id_pacjenta = NEW.id_pacjenta AND od = NEW.od) > 0
    THEN
      DELETE FROM pacjenci_lpk
      WHERE id_pacjenta = NEW.id_pacjenta AND od = NEW.od;
      RETURN NEW;
    ELSE
      UPDATE pacjenci_lpk
      SET "do" = NEW.od - INTERVAL '1 DAY'
      WHERE
        id_pacjenta = NEW.id_pacjenta AND coalesce("do", DATE(current_timestamp + INTERVAL '1000 YEARS')) >= NEW.od;
      RETURN NEW;
    END IF;
  ELSE
    IF (SELECT count(*)
        FROM pacjenci_lpk p
        WHERE p.id_pacjenta = NEW.id_pacjenta
              AND NEW.od <> p.od AND
              (d1, d2) OVERLAPS (p.od, coalesce(p."do", DATE(CURRENT_TIMESTAMP + INTERVAL '100 YEARS')))) > 0
    THEN RAISE EXCEPTION 'Pacjent posiada lpk w zadanym terminie';
    END IF;
  END IF;
  RETURN NEW;
END;
$pacjent_lpk_check$
language plpgsql;

CREATE TRIGGER pacjent_lpk_check
  BEFORE INSERT OR UPDATE
  ON pacjenci_lpk
  FOR EACH ROW EXECUTE PROCEDURE pacjent_lpk_check();

CREATE OR REPLACE FUNCTION wizyta_odbyta_check()
  RETURNS TRIGGER AS $wizyta_odbyta_check$
DECLARE r RECORD;
BEGIN
  IF czy_aktywny_lekarz(NEW.id_lekarza, DATE(NEW.data), DATE(NEW.data + NEW.czas_trwania)) = FALSE
  THEN RAISE EXCEPTION 'Nie jest lekarzem';
  END IF;
  IF NEW.DATA > current_timestamp
  THEN RAISE EXCEPTION 'Wizyta musiala sie odbyc w przeszlosci';
  END IF;
  IF (SELECT count(*)
      FROM lekarze_specjalizacje ls
        JOIN pracownicy p ON ls.id_lekarza = p.id_pracownika AND ls.id_specjalizacji = NEW.specjalizacja) = 0
  THEN RAISE EXCEPTION 'Lekarz nie posiada specjalizacji';
  END IF;
  IF (SELECT count(*)
      FROM wizyty_odbyte wo
      WHERE
        wo.id_pacjenta = NEW.id_pacjenta AND
        (wo.data, wo.data + wo.czas_trwania) OVERLAPS (NEW.data, NEW.data + NEW.czas_trwania) AND
        NEW.id_wizyty <> wo.id_wizyty) > 0
  THEN RAISE EXCEPTION 'Pacjent posiada wizyte w zadanym terminie';
  END IF;
  FOR r in SELECT
             data                AS d1,
             data + czas_trwania AS d2
           FROM wizyty_odbyte
           WHERE id_lekarza = NEW.id_lekarza AND id_wizyty <> NEW.id_wizyty LOOP
    IF (NEW.data, NEW.data + NEW.czas_trwania) OVERLAPS (r.d1, r.d2)
    THEN RAISE EXCEPTION 'Lekarz jest zajenty';
    END IF;
  END LOOP;
  RETURN NEW;
END;
$wizyta_odbyta_check$
language plpgsql;

CREATE TRIGGER wizyta_odbyta_check
  BEFORE INSERT OR UPDATE
  ON wizyty_odbyte
  FOR EACH ROW EXECUTE PROCEDURE wizyta_odbyta_check();

CREATE OR REPLACE FUNCTION wizyta_planowana_check()
  RETURNS TRIGGER AS $wizyta_planowana_check$
DECLARE r RECORD;
BEGIN
  IF NEW.data < current_timestamp
  THEN RAISE EXCEPTION 'Wizyta musi sie odbyc w przyszlosci';
  END IF;
  IF czy_aktywny_lekarz(NEW.id_lekarza, DATE(NEW.data), DATE(NEW.data + NEW.szacowany_czas)) = FALSE
  THEN RAISE EXCEPTION 'Nie jest lekarzem';
  END IF;
  IF (SELECT count(*)
      FROM lekarze_specjalizacje ls
        JOIN pracownicy p ON ls.id_lekarza = p.id_pracownika AND ls.id_specjalizacji = NEW.specjalizacja) = 0
  THEN RAISE EXCEPTION 'Lekarz nie posiada specjalizacji';
  END IF;
  IF (SELECT count(*)
      FROM wizyty_planowane wp
      WHERE
        wp.id_pacjenta = NEW.id_pacjenta AND
        (wp.data, wp.data + wp.szacowany_czas) OVERLAPS (NEW.data, NEW.data + NEW.szacowany_czas) AND
        NEW.id_wizyty <> wp.id_wizyty) > 0
  THEN RAISE EXCEPTION 'Pacjent posiada wizyte w zadanym terminie';
  END IF;
  FOR r in SELECT
             data                  AS d1,
             data + szacowany_czas AS d2
           FROM wizyty_planowane
           WHERE id_lekarza = NEW.id_lekarza AND id_wizyty <> NEW.id_wizyty LOOP
    IF (NEW.data, NEW.data + NEW.szacowany_czas) OVERLAPS (r.d1, r.d2)
    THEN RAISE EXCEPTION 'Lekarz jest zajenty';
    END IF;
  END LOOP;
  RETURN NEW;
END;
$wizyta_planowana_check$
language plpgsql;

CREATE TRIGGER wizyta_planowana_check
  BEFORE INSERT OR UPDATE
  ON wizyty_planowane
  FOR EACH ROW EXECUTE PROCEDURE wizyta_planowana_check();

CREATE OR REPLACE FUNCTION terminarz_insert()
  RETURNS TRIGGER AS $terminarz_insert$
DECLARE r   RECORD;
        a   INTEGER;
        val NUMERIC;
        c   NUMERIC;
        s   NUMERIC;
        age INTEGER;
        i   INTERVAL;
        id  INTEGER;
BEGIN
  IF NEW.data < current_timestamp
  THEN RAISE EXCEPTION 'Wizyta musi sie odbyc w przyszlosci';
  END IF;
  s = 0;
  val = 0;
  a = 40;
  age = (SELECT extract(YEARS FROM (current_timestamp - data_urodzenia :: TIMESTAMP))
         FROM pacjenci
         WHERE NEW.id_pacjenta = pacjenci.id_pacjenta);
  FOR r IN SELECT
             extract(MINUTES FROM w.czas_trwania)         AS t,
             (SELECT extract(years from (current_timestamp - data_urodzenia :: TIMESTAMP))
              FROM pacjenci
              WHERE w.id_pacjenta = pacjenci.id_pacjenta) AS age
           FROM wizyty_odbyte w
           WHERE w.cel = NEW.cel LOOP
    c = exp(-(((@(r.age - age)) ^ 2) / (2 * (a ^ 2))));
    val = val + r.t * c;
    s = s + c;
  END LOOP;
  IF s = 0
  THEN val = 30;
  ELSE val = val / s;
  END IF;
  i = make_interval(mins := (val :: INT));
  id = -1;
  IF NEW.id_lekarza IS NOT NULL
  THEN
    IF (SELECT count(*)
        FROM wizyty_planowane wp
        WHERE
          wp.id_lekarza = NEW.id_lekarza AND
          (NEW.data, NEW.data + i) OVERLAPS (wp.data, wp.data + wp.szacowany_czas)) > 0
    THEN id = -1;
    ELSE id = NEW.id_lekarza;
    END IF;
  END IF;
  IF id = -1
  THEN
    id = coalesce((SELECT ls.id_lekarza
                   FROM lekarze_specjalizacje ls
                   WHERE ls.id_specjalizacji = NEW.specjalizacja
                         AND (SELECT count(*)
                              FROM wizyty_planowane w
                              WHERE ls.id_lekarza = w.id_lekarza AND
                                    (w.data, w.data + w.szacowany_czas)
                                    OVERLAPS
                                    (New.data, New.data + i)) = 0
                   ORDER BY id_lekarza
                   LIMIT 1), -1);
  END IF;
  IF id = -1
  THEN RAISE EXCEPTION 'Nie mamy specjalisty';
  END IF;
  INSERT INTO wizyty_planowane (id_pacjenta, id_lekarza, cel, specjalizacja, data, szacowany_czas)
  VALUES (NEW.id_pacjenta, id, NEW.cel, NEW.specjalizacja, NEW.data, i);
  RETURN NEW;
END;
$terminarz_insert$
language plpgsql;

CREATE TRIGGER terminarz_insert
  INSTEAD OF INSERT
  ON terminarz
  FOR EACH ROW EXECUTE PROCEDURE terminarz_insert();

CREATE OR REPLACE FUNCTION ranking(d1 DATE, d2 DATE)
  RETURNS TABLE(id_lekarza INTEGER, imie VARCHAR, nazwisko VARCHAR, wynik NUMERIC(3, 2))
AS
$$
BEGIN
  d1 = coalesce(d1, (SELECT min(data)
                     FROM ankiety_lekarze));
  d2 = coalesce(d2, (SELECT max(data)
                     FROM ankiety_lekarze));
  RETURN QUERY
  WITH temp AS (
      SELECT
        al.id_lekarza                                                                                      AS id,
        round((sum(coalesce(al.dokladnosc_badan, 0)) :: DECIMAL / CASE WHEN count(al.dokladnosc_badan) = 0
          THEN 1
                                                                  ELSE count(al.dokladnosc_badan) END), 2) AS v1,
        sum(coalesce(al.informacyjnosc, 0)) :: DECIMAL / CASE WHEN count(al.informacyjnosc) = 0
          THEN 1
                                                         ELSE count(al.informacyjnosc) END                 AS v2,
        sum(coalesce(al.opanowanie, 0)) :: DECIMAL / CASE WHEN count(al.opanowanie) = 0
          THEN 1
                                                     ELSE count(al.opanowanie) END                         AS v3,
        sum(coalesce(al.uprzejmosc, 0)) :: DECIMAL / CASE WHEN count(al.uprzejmosc) = 0
          THEN 1
                                                     ELSE count(al.uprzejmosc) END                         AS v4
      FROM ankiety_lekarze al
      WHERE data >= d1 AND data <= d2
      GROUP BY al.id_lekarza
  )
  SELECT
    id,
    p.imie,
    p.nazwisko,
    round(((v1 + v2 + v3 + v4) :: DECIMAL /
           (nulls(nullif(v1 <> 0, true)) + nulls(nullif(v2 <> 0, true)) + nulls(nullif(v3 <> 0, true)) +
            nulls(nullif(v4 <> 0, true)))), 2) :: NUMERIC(3, 2)
  FROM temp
    JOIN pracownicy p ON id = p.id_pracownika
  ORDER BY (v1 + v2 + v3 + v4) /
           (nulls(nullif(v1 <> 0, true)) + nulls(nullif(v2 <> 0, true)) + nulls(nullif(v3 <> 0, true)) +
            nulls(nullif(v4 <> 0, true))) DESC, id;
END;
$$
language plpgsql;

CREATE OR REPLACE FUNCTION lekarze_specjalizacje_check()
  RETURNS TRIGGER AS $lekarze_specjalizacje_check$
BEGIN
  IF czy_aktywny_lekarz(NEW.id_lekarza, NULL, NULL)
  THEN RETURN NEW;
  ELSE RAISE EXCEPTION 'Nie jest lekarzem';
  END IF;
END;
$lekarze_specjalizacje_check$
language plpgsql;

CREATE TRIGGER lekarze_specjalizacje_check
  BEFORE INSERT OR UPDATE
  ON lekarze_specjalizacje
  FOR EACH ROW EXECUTE PROCEDURE lekarze_specjalizacje_check();

CREATE OR REPLACE FUNCTION ranking_cecha(d1 DATE, d2 DATE, cecha VARCHAR)
  RETURNS TABLE(id_lekarza INTEGER, imie VARCHAR, nazwisko VARCHAR, wynik NUMERIC(3, 2))
AS
$$
BEGIN
  d1 = coalesce(d1, (SELECT min(data)
                     FROM ankiety_lekarze));
  d2 = coalesce(d2, (SELECT max(data)
                     FROM ankiety_lekarze));
  IF cecha <> 'dokladnosc_badan' AND cecha <> 'informacyjnosc' AND cecha <> 'opanowanie' AND cecha <> 'uprzejmosc'
  THEN RAISE EXCEPTION 'Nie mamy takiej cechy';
  END IF;
  IF cecha = 'dokladnosc_badan'
  THEN
    RETURN QUERY
    WITH temp AS (
        SELECT
          al.id_lekarza                                                                             AS id,
          round((sum(coalesce(al.dokladnosc_badan, 0)) :: DECIMAL / count(al.dokladnosc_badan)), 2) AS v
        FROM ankiety_lekarze al
        WHERE data >= d1 AND data <= d2
        GROUP BY al.id_lekarza
        HAVING count(al.dokladnosc_badan) > 0
    )
    SELECT
      p.id_pracownika,
      p.imie,
      p.nazwisko,
      v :: NUMERIC(3, 2)
    FROM temp
      JOIN pracownicy p ON id = p.id_pracownika
    ORDER BY v DESC, id;
  ELSEIF cecha = 'informacyjnosc'
    THEN
      RETURN QUERY
      WITH temp AS (
          SELECT
            al.id_lekarza                                                                         AS id,
            round((sum(coalesce(al.informacyjnosc, 0)) :: DECIMAL / count(al.informacyjnosc)), 2) AS v
          FROM ankiety_lekarze al
          WHERE data >= d1 AND data <= d2
          GROUP BY al.id_lekarza
          HAVING count(al.informacyjnosc) > 0
      )
      SELECT
        p.id_pracownika,
        p.imie,
        p.nazwisko,
        v :: NUMERIC(3, 2)
      FROM temp
        JOIN pracownicy p ON id = p.id_pracownika
      ORDER BY v DESC, id;
  ELSEIF cecha = 'opanowanie'
    THEN
      RETURN QUERY
      WITH temp AS (
          SELECT
            al.id_lekarza                                                                 AS id,
            round((sum(coalesce(al.opanowanie, 0)) :: DECIMAL / count(al.opanowanie)), 2) AS v
          FROM ankiety_lekarze al
          WHERE data >= d1 AND data <= d2
          GROUP BY al.id_lekarza
          HAVING count(al.opanowanie) > 0
      )
      SELECT
        p.id_pracownika,
        p.imie,
        p.nazwisko,
        v :: NUMERIC(3, 2)
      FROM temp
        JOIN pracownicy p ON id = p.id_pracownika
      ORDER BY v DESC, id;
  ELSE
    RETURN QUERY
    WITH temp AS (
        SELECT
          al.id_lekarza                                                                 AS id,
          round((sum(coalesce(al.uprzejmosc, 0)) :: DECIMAL / count(al.uprzejmosc)), 2) AS v
        FROM ankiety_lekarze al
        WHERE data >= d1 AND data <= d2
        GROUP BY al.id_lekarza
        HAVING count(al.uprzejmosc) > 0
    )
    SELECT
      p.id_pracownika,
      p.imie,
      p.nazwisko,
      v :: NUMERIC(3, 2)
    FROM temp
      JOIN pracownicy p ON id = p.id_pracownika
    ORDER BY v DESC, id;
  END IF;
END;
$$
language plpgsql;

CREATE OR REPLACE FUNCTION ranking_specjalizacje(d1 DATE, d2 DATE, id INTEGER)
  RETURNS TABLE(id_lekarza INTEGER, imie VARCHAR, nazwisko VARCHAR, wynik NUMERIC(3, 2))
AS
$$
BEGIN
  d1 = coalesce(d1, (SELECT min(data)
                     FROM ankiety_lekarze));
  d2 = coalesce(d2, (SELECT max(data)
                     FROM ankiety_lekarze));
  RETURN QUERY SELECT r.*
               FROM ranking(d1, d2) r
                 JOIN lekarze_specjalizacje ls ON ls.id_lekarza = r.id_lekarza AND ls.id_specjalizacji = id;
END;
$$
language plpgsql;

CREATE OR REPLACE FUNCTION ranking_alfabetyczny(d1 DATE, d2 DATE)
  RETURNS TABLE(id_lekarza INTEGER, imie VARCHAR, nazwisko VARCHAR, wynik NUMERIC(3, 2))
AS
$$
BEGIN
  d1 = coalesce(d1, (SELECT min(data)
                     FROM ankiety_lekarze));
  d2 = coalesce(d2, (SELECT max(data)
                     FROM ankiety_lekarze));
  RETURN QUERY SELECT r.*
               FROM ranking(d1, d2) r
               ORDER BY r.nazwisko, r.imie, r.wynik DESC;
END;
$$
language plpgsql;

CREATE OR REPLACE RULE pracownicy_delete AS ON DELETE TO pracownicy
DO INSTEAD UPDATE pracownicy
SET zatrudniony_do = least(DATE(current_timestamp), coalesce(zatrudniony_do, DATE(current_timestamp)))
WHERE id_pracownika = OLD.id_pracownika;

CREATE OR REPLACE FUNCTION ankiety_check()
  RETURNS TRIGGER AS $ankety_check$
BEGIN
  IF czy_aktywny_lekarz(NEW.id_lekarza, NEW.data, NULL) = FALSE
  THEN RAISE EXCEPTION 'Nie aktywny lekarz';
  END IF;
  IF (SELECT count(*)
      FROM wizyty_odbyte
      WHERE DATE(data) = NEW.data AND wizyty_odbyte.id_lekarza = NEW.id_lekarza) = 0
  THEN RAISE EXCEPTION 'Nie mial wizyty';
  END IF;
  RETURN NEW;
END;
$ankety_check$
language plpgsql;

CREATE TRIGGER ankiety_check
  BEFORE INSERT OR UPDATE
  ON ankiety_lekarze
  FOR EACH ROW EXECUTE PROCEDURE ankiety_check();

CREATE OR REPLACE FUNCTION lrul()
  RETURNS TABLE(id_pracownka INTEGER, imie VARCHAR, nazwisko VARCHAR) AS
$$
BEGIN
  RETURN QUERY (SELECT
                  p.id_pracownika,
                  p.imie,
                  p.nazwisko
                FROM pracownicy p LEFT JOIN pacjenci_lpk l on p.id_pracownika = l.id_lekarza
                WHERE czy_aktywny_lekarz(p.id_pracownika, DATE(current_timestamp),
                                         DATE(current_timestamp + INTERVAL '1 DAY'))
                GROUP BY p.id_pracownika
                ORDER BY count(p.id_pracownika), p.id_pracownika
                LIMIT 1);
END;
$$
language plpgsql;

CREATE RULE pracownicy_role_delete AS ON DELETE TO pracownicy_role
  WHERE old.id_roli = 1 AND ((SELECT count(*)
                              FROM pacjenci_lpk p
                              WHERE p.id_lekarza = OLD.id_pracownika) > 0 OR (SELECT count(*)
                                                                               FROM wizyty_odbyte
                                                                               WHERE id_lekarza = OLD.id_pracownika) > 0
                             OR (SELECT count(*)
                                 FROM lekarze_specjalizacje
                                 WHERE id_lekarza = OLD.id_pracownika) > 0 OR (SELECT count(*)
                                                                           FROM wizyty_planowane
                                                                           WHERE id_lekarza = OLD.id_pracownika) > 0
                             OR (SELECT count(*)
                                 FROM ankiety_lekarze
                                 WHERE id_lekarza = OLD.id_pracownika) > 0) DO INSTEAD NOTHING;

CREATE RULE pracownicy_update AS ON UPDATE TO pracownicy_role
  WHERE old.id_roli = 1 AND ((SELECT count(*)
                              FROM pacjenci_lpk p
                              WHERE p.id_lekarza = OLD.id_pracownika) > 0 OR (SELECT count(*)
                                                                               FROM wizyty_odbyte
                                                                               WHERE id_lekarza = OLD.id_pracownika) > 0
                             OR (SELECT count(*)
                                 FROM lekarze_specjalizacje
                                 WHERE id_lekarza = OLD.id_pracownika) > 0 OR (SELECT count(*)
                                                                           FROM wizyty_planowane
                                                                           WHERE id_lekarza = OLD.id_pracownika) > 0
                             OR (SELECT count(*)
                                 FROM ankiety_lekarze
                                 WHERE id_lekarza = OLD.id_pracownika) > 0) DO INSTEAD NOTHING;

CREATE OR REPLACE FUNCTION historia_check()
  RETURNS TRIGGER AS $historia_check$
BEGIN
  IF NEW.od < (SELECT data_urodzenia
               FROM pacjenci
               WHERE id_pacjenta = NEW.id_pacjenta)
  THEN RAISE EXCEPTION 'Pacjent jeszcze nie byl urodzony';
  END IF;
  IF NEW.wizyta IS NOT NULL AND (SELECT id_pacjenta
                                 FROM wizyty_odbyte
                                 WHERE id_wizyty = NEW.wizyta) <> NEW.id_pacjenta
  THEN RAISE EXCEPTION 'Pacjent nie byl na wizycie';
  END IF;
  RETURN NEW;
END;
$historia_check$
language plpgsql;

CREATE TRIGGER historia_check
  BEFORE INSERT OR UPDATE
  ON historia_medyczna
  FOR EACH ROW EXECUTE PROCEDURE historia_check();