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
  zod = (SELECT zatrudniony_od FROM pracownicy WHERE id_pracownika = id);
  zdo = (SELECT zatrudniony_do FROM pracownicy WHERE id_pracownika = id);
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
BEGIN
  IF NEW.pesel IS NULL OR length(NEW.pesel) <> 11
  THEN RETURN NEW; END IF;
  IF pesel_check(NEW.pesel)
  THEN RETURN NEW;
  ELSE RAISE EXCEPTION 'Niepoprawny PESEL';
  END IF;
END;
$pacjent_check$
language plpgsql;

CREATE TRIGGER pacjent_check
  BEFORE INSERT OR UPDATE
  ON pacjenci
  FOR EACH ROW EXECUTE PROCEDURE pacjent_check();

CREATE OR REPLACE FUNCTION pracownik_check()
  RETURNS TRIGGER AS $pracownik_check$
BEGIN
  IF pesel_check(NEW.pesel)
  THEN RETURN NEW;
  ELSE RAISE EXCEPTION 'Niepoprawny PESEL';
  END IF;
END;
$pracownik_check$
language plpgsql;

CREATE TRIGGER pracownik_check
  BEFORE INSERT OR UPDATE
  ON pracownicy
  FOR EACH ROW EXECUTE PROCEDURE pracownik_check();

CREATE OR REPLACE FUNCTION pacjent_lpk_check()
  RETURNS TRIGGER AS $pacjent_lpk_check$
BEGIN
  IF czy_aktywny_lekarz(NEW.id_lekarza, NEW.od, NEW."do")
  THEN RETURN NEW;
  ELSE RAISE EXCEPTION 'Nie aktywny lekarz';
  END IF;
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
  FOR r in SELECT
             data                AS d1,
             data + czas_trwania AS d2
           FROM wizyty_odbyte
           WHERE id_lekarza = NEW.id_lekarza LOOP
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
  IF NEW.data < current_time
  THEN RAISE EXCEPTION 'Potrzebuje Panstwo DeLorean.';
  END IF;
  IF czy_aktywny_lekarz(NEW.id_lekarza, DATE(NEW.data), DATE(NEW.data + NEW.szacowany_czas)) = FALSE
  THEN RAISE EXCEPTION 'Nie jest lekarzem';
  END IF;
  FOR r in SELECT
             data                  AS d1,
             data + szacowany_czas AS d2
           FROM wizyty_planowane
           WHERE id_lekarza = NEW.id_lekarza LOOP
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

CREATE OR REPLACE VIEW terminaz AS
  SELECT
    id_pacjenta,
    cel,
    specjalizacja,
    data
  FROM wizyty_planowane
  WHERE FALSE;

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
  IF NEW.data < current_time
  THEN RAISE EXCEPTION 'Potrzebuje Panstwo DeLorean';
  END IF;
  s = 0;
  val = 0;
  a = 40;
  age = (SELECT extract(YEARS FROM (current_time - data_urodzenia))
         FROM pacjenci
         WHERE NEW.id_pacjenta = pacjenci.id_pacjenta);
  FOR r IN SELECT
             extract(MINUTES FROM w.czas_trwania)         AS t,
             (SELECT extract(years from (current_time - data_urodzenia))
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
  IF id = -1
  THEN RAISE EXCEPTION 'Nie mamy specjalisty';
  END IF;
  INSERT INTO wizyty_planowane (id_pacjenta, id_lekarza, cel, data, szacowany_czas)
  VALUES (NEW.id_pacjenta, id, NEW.cel, NEW.data, i);
  RETURN NEW;
END;
$terminarz_insert$
language plpgsql;

CREATE TRIGGER terminarz_insert
  INSTEAD OF INSERT
  ON terminaz
  FOR EACH ROW EXECUTE PROCEDURE terminarz_insert();

CREATE OR REPLACE FUNCTION ranking(d1 DATE, d2 DATE)
  RETURNS TABLE(id_lekarza INTEGER, imie VARCHAR, nazwisko VARCHAR, wynik NUMERIC(3, 2))
AS
$$
BEGIN
  d1 = coalesce(d1, min((SELECT data
                         FROM ankiety_lekarze)));
  d2 = coalesce(d2, max((SELECT data
                         FROM ankiety_lekarze)));
  RETURN QUERY
  WITH temp AS (
      SELECT
        al.id_lekarza                                                               AS id,
        sum(coalesce(al.dokladnosc_badan, 0)) / CASE WHEN count(al.dokladnosc_badan) = 0
          THEN 1
                                                ELSE count(al.dokladnosc_badan) END AS v1,
        sum(coalesce(al.informacyjnosc, 0)) / CASE WHEN count(al.informacyjnosc) = 0
          THEN 1
                                              ELSE count(al.informacyjnosc) END     AS v2,
        sum(coalesce(al.opanowanie, 0)) / CASE WHEN count(al.opanowanie) = 0
          THEN 1
                                          ELSE count(al.opanowanie) END             AS v3,
        sum(coalesce(al.uprzejmosc, 0)) / CASE WHEN count(al.uprzejmosc) = 0
          THEN 1
                                          ELSE count(al.uprzejmosc) END             AS v4
      FROM ankiety_lekarze al
      WHERE data >= d1 AND data <= d2
      GROUP BY al.id_lekarza
  )
  SELECT
    id,
    p.imie,
    p.nazwisko,
    ((v1 + v2 + v3 + v4) /
     (nulls(nullif(v1, 0)) + nulls(nullif(v2, 0)) + nulls(nullif(v3, 0)) + nulls(nullif(v4, 0)))) :: NUMERIC(3, 2)
  FROM temp
    JOIN pracownicy p ON id = p.id_pracownika
  ORDER BY (v1 + v2 + v3 + v4) /
           (nulls(nullif(v1, 0)) + nulls(nullif(v2, 0)) + nulls(nullif(v3, 0)) + nulls(nullif(v4, 0))) DESC, id;
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
  d1 = coalesce(d1, min((SELECT data
                         FROM ankiety_lekarze)));
  d2 = coalesce(d2, max((SELECT data
                         FROM ankiety_lekarze)));
  IF cecha <> 'dokladnosc_badan' AND cecha <> 'informacyjnosc' AND cecha <> 'opanowanie' AND cecha <> 'uprzejmosc'
  THEN RAISE EXCEPTION 'Nie mamy takiej cechy';
  END IF;
  IF cecha = 'dokladnosc_badan'
  THEN
    RETURN QUERY
    WITH temp AS (
        SELECT
          al.id_lekarza                                                                         AS id,
          (sum(coalesce(al.dokladnosc_badan, 0)) / count(al.dokladnosc_badan)) :: NUMERIC(3, 2) AS v
        FROM ankiety_lekarze al
        WHERE data >= d1 AND data <= d2
        GROUP BY al.id_lekarza
        HAVING count(al.dokladnosc_badan) > 0
    )
    SELECT
      p.id_pracownika,
      p.imie,
      p.nazwisko,
      v
    FROM temp
      JOIN pracownicy p ON id = p.id_pracownika
    ORDER BY v DESC, id;
  ELSEIF cecha = 'informacyjnosc'
    THEN
      RETURN QUERY
      WITH temp AS (
          SELECT
            al.id_lekarza                                                                     AS id,
            (sum(coalesce(al.informacyjnosc, 0)) / count(al.informacyjnosc)) :: NUMERIC(3, 2) AS v
          FROM ankiety_lekarze al
          WHERE data >= d1 AND data <= d2
          GROUP BY al.id_lekarza
          HAVING count(al.informacyjnosc) > 0
      )
      SELECT
        p.id_pracownika,
        p.imie,
        p.nazwisko,
        v
      FROM temp
        JOIN pracownicy p ON id = p.id_pracownika
      ORDER BY v DESC, id;
  ELSEIF cecha = 'opanowanie'
    THEN
      RETURN QUERY
      WITH temp AS (
          SELECT
            al.id_lekarza                                                             AS id,
            (sum(coalesce(al.opanowanie, 0)) / count(al.opanowanie)) :: NUMERIC(3, 2) AS v
          FROM ankiety_lekarze al
          WHERE data >= d1 AND data <= d2
          GROUP BY al.id_lekarza
          HAVING count(al.opanowanie) > 0
      )
      SELECT
        p.id_pracownika,
        p.imie,
        p.nazwisko,
        v
      FROM temp
        JOIN pracownicy p ON id = p.id_pracownika
      ORDER BY v DESC, id;
  ELSE
    RETURN QUERY
    WITH temp AS (
        SELECT
          al.id_lekarza                                                             AS id,
          (sum(coalesce(al.uprzejmosc, 0)) / count(al.uprzejmosc)) :: NUMERIC(3, 2) AS v
        FROM ankiety_lekarze al
        WHERE data >= d1 AND data <= d2
        GROUP BY al.id_lekarza
        HAVING count(al.uprzejmosc) > 0
    )
    SELECT
      p.id_pracownika,
      p.imie,
      p.nazwisko,
      v
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
  d1 = coalesce(d1, min((SELECT data
                         FROM ankiety_lekarze)));
  d2 = coalesce(d2, max((SELECT data
                         FROM ankiety_lekarze)));
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
  d1 = coalesce(d1, min((SELECT data
                         FROM ankiety_lekarze)));
  d2 = coalesce(d2, max((SELECT data
                         FROM ankiety_lekarze)));
  RETURN QUERY SELECT r.*
               FROM ranking(d1, d2) r
               ORDER BY r.nazwisko, r.imie, r.wynik DESC;
END;
$$
language plpgsql;
