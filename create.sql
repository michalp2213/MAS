DROP TABLE IF EXISTS ankiety_lekarze CASCADE;
DROP TABLE IF EXISTS skierowania CASCADE;
DROP TABLE IF EXISTS historia_medyczna CASCADE;
DROP TABLE IF EXISTS wydarzenia_medyczne CASCADE;
DROP TABLE IF EXISTS wizyty_planowane CASCADE;
DROP TABLE IF EXISTS wizyty_odbyte CASCADE;
DROP TABLE IF EXISTS cele_wizyty CASCADE;
DROP TABLE IF EXISTS pracownicy_role CASCADE;
DROP TABLE IF EXISTS role CASCADE;
DROP TABLE IF EXISTS pacjenci_lpk CASCADE;
DROP TABLE IF EXISTS lekarze_specjalizacje CASCADE;
DROP TABLE IF EXISTS specjalizacje CASCADE;
DROP TABLE IF EXISTS pracownicy CASCADE;
DROP TABLE IF EXISTS pacjenci CASCADE;

CREATE TABLE pacjenci
(
  id_pacjenta    SERIAL  NOT NULL
    PRIMARY KEY,
  imie           VARCHAR NOT NULL,
  nazwisko       VARCHAR NOT NULL,
  pesel          CHAR(11)
    UNIQUE,
  nr_paszportu   VARCHAR
    UNIQUE,
  data_urodzenia date    NOT NULL,
  plec           CHAR(1) NOT NULL
    CHECK ((plec = 'M' :: bpCHAR) OR (plec = 'F' :: bpCHAR)),
  CHECK ((pesel IS NOT NULL) OR (nr_paszportu IS NOT NULL))
);

CREATE TABLE pracownicy
(
  id_pracownika SERIAL   NOT NULL
    PRIMARY KEY,
  imie          VARCHAR  NOT NULL,
  nazwisko      VARCHAR  NOT NULL,
  pesel         CHAR(11) NOT NULL
    UNIQUE
);

CREATE TABLE specjalizacje
(
  id_specjalizacji SERIAL  NOT NULL
    PRIMARY KEY,
  nazwa            VARCHAR NOT NULL
    UNIQUE
);

CREATE TABLE lekarze_specjalizacje
(
  id_lekarza       INTEGER NOT NULL
    REFERENCES pracownicy (id_pracownika),
  id_specjalizacji INTEGER NOT NULL
    REFERENCES specjalizacje (id_specjalizacji),
  PRIMARY KEY (id_lekarza, id_specjalizacji)
);

CREATE TABLE pacjenci_lpk
(
  id_pacjenta INTEGER NOT NULL
    REFERENCES pacjenci (id_pacjenta),
  id_lekarza  INTEGER NOT NULL
    REFERENCES pracownicy (id_pracownika),
  od          date    NOT NULL,
  "do"        date,
  PRIMARY KEY (id_pacjenta, od)
);


CREATE TABLE role
(
  id_roli SERIAL  NOT NULL
    PRIMARY KEY,
  nazwa   VARCHAR NOT NULL
    UNIQUE
);

CREATE TABLE pracownicy_role
(
  id_roli       INTEGER NOT NULL
    REFERENCES role (id_roli),
  id_pracownika INTEGER NOT NULL
    REFERENCES pracownicy (id_pracownika),
  PRIMARY KEY (id_roli, id_pracownika)
);

CREATE TABLE cele_wizyty
(
  id_celu               SERIAL                  NOT NULL
    PRIMARY KEY,
  nazwa                 VARCHAR                 NOT NULL
    UNIQUE,
  specjalizacja_lekarza INTEGER
    REFERENCES specjalizacje (id_specjalizacji) NOT NULL
);

CREATE TABLE wizyty_odbyte
(
  id_wizyty    INTEGER   NOT NULL
    PRIMARY KEY,
  id_pacjenta  INTEGER   NOT NULL
    REFERENCES pacjenci (id_pacjenta),
  id_lekarza   INTEGER   NOT NULL
    REFERENCES pracownicy (id_pracownika),
  cel          INTEGER   NOT NULL
    REFERENCES cele_wizyty (id_celu),
  data         TIMESTAMP NOT NULL,
  czas_trwania INTERVAL,
  UNIQUE (id_pacjenta, id_lekarza, data)
);

CREATE TABLE wizyty_planowane
(
  id_wizyty      SERIAL    NOT NULL
    PRIMARY KEY,
  id_pacjenta    INTEGER   NOT NULL
    REFERENCES pacjenci (id_pacjenta),
  id_lekarza     INTEGER   NOT NULL
    REFERENCES pracownicy (id_pracownika),
  cel            INTEGER   NOT NULL
    REFERENCES cele_wizyty (id_celu),
  data           TIMESTAMP NOT NULL,
  szacowany_czas INTERVAL,
  UNIQUE (id_pacjenta, id_lekarza, data)
);

CREATE TABLE wydarzenia_medyczne
(
  id_wydarzenia SERIAL  NOT NULL
    PRIMARY KEY,
  nazwa         VARCHAR NOT NULL
    UNIQUE
);

CREATE TABLE historia_medyczna
(
  id_pacjenta   INTEGER   NOT NULL
    REFERENCES pacjenci (id_pacjenta),
  id_wydarzenia INTEGER   NOT NULL
    REFERENCES wydarzenia_medyczne (id_wydarzenia),
  wizyta        INTEGER
    REFERENCES wizyty_odbyte (id_wizyty),
  od            TIMESTAMP NOT NULL,
  "do"          TIMESTAMP,
  PRIMARY KEY (id_pacjenta, id_wydarzenia, od),
  CHECK (od < "do")
);

CREATE TABLE skierowania
(
  nr_skierowania  INTEGER NOT NULL
    PRIMARY KEY,
  wizyta          INTEGER NOT NULL
    REFERENCES wizyty_odbyte (id_wizyty),
  specjalizacja   INTEGER NOT NULL
    REFERENCES specjalizacje (id_specjalizacji),
  cel_skierowania INTEGER NOT NULL
    REFERENCES cele_wizyty (id_celu),
  opis            VARCHAR NOT NULL,
  UNIQUE (wizyta, specjalizacja, cel_skierowania)
);

CREATE TABLE ankiety_lekarze
(
  id_ankiety       SERIAL  NOT NULL PRIMARY KEY,
  id_lekarza       INTEGER NOT NULL REFERENCES pracownicy (id_pracownika),
  data             DATE    NOT NULL,
  uprzejmosc       INTEGER CHECK (uprzejmosc >= 1 AND uprzejmosc <= 5),
  opanowanie       INTEGER CHECK (opanowanie >= 1 AND opanowanie <= 5),
  informacyjnosc   INTEGER CHECK (informacyjnosc >= 1 AND informacyjnosc <= 5),
  dokladnosc_badan INTEGER CHECK (dokladnosc_badan >= 1 AND dokladnosc_badan <= 5)
);

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
                id_lekarza INTEGER, cel INTEGER, data TIMESTAMP, czas_trwania INTERVAL)
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
                id_lekarza INTEGER, cel INTEGER, data TIMESTAMP, szacowany_czas INTERVAL)
AS $$
BEGIN
  RETURN QUERY
  SELECT *
  FROM wizyty_planowane w
  WHERE w.id_pacjenta = id;
END;
$$
language plpgsql;

CREATE OR REPLACE FUNCTION czy_aktywny_lekarz(id INTEGER)
  RETURNS BOOLEAN AS
$$
BEGIN
  RETURN (SELECT count(*)
          FROM pracownicy_role
          WHERE id_pracownika = id AND id_roli = 1) = 1 AND (SELECT count(*)
                                                             FROM pracownicy_role
                                                             WHERE id_pracownika = id AND id_roli = 2) = 0;
END;
$$
language plpgsql;

CREATE OR REPLACE FUNCTION pesel_check(pesel CHAR(11))
  RETURNS BOOLEAN AS
$$
DECLARE
  s INTEGER;
BEGIN
  s = pesel [1] :: INT + pesel [2] :: INT * 3 + pesel [3] :: INT * 7 + pesel [4] * 9 + pesel [5] :: INT * 1 +
      pesel [6] :: INT * 3 + pesel [7] :: INT * 7 + pesel [8] :: INT * 9 + pesel [9] :: INT + pesel [10] :: INT * 3;
  s = s % 10;
  RETURN s = pesel [11] :: INT;
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
  IF czy_aktywny_lekarz(NEW.id_lekarza)
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
  THEN RAISE EXCEPTION 'Potrzebuje Panstwo DeLorean ';
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
  i = make_interval(mins := val :: INT);
  id = coalesce((SELECT ls.id_lekarza
                 FROM lekarze_specjalizacje ls
                 WHERE ls.id_specjalizacji = (SELECT id_specjalizacji
                                              FROM cele_wizyty
                                              WHERE id_celu = NEW.cel)
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
    p.id_pracownika,
    p.imie,
    p.nazwisko,
    (v1 + v2 + v3 + v4) / (count(nullif(v1, 0)) + count(nullif(v2, 0)) + count(nullif(v3, 0)) + count(nullif(v4, 0)))
  FROM temp
    JOIN pracownicy p ON id = p.id_pracownika
  ORDER BY (v1 + v2 + v3 + v4) /
           (count(nullif(v1, 0)) + count(nullif(v2, 0)) + count(nullif(v3, 0)) + count(nullif(v4, 0))) DESC, id;
END;
$$
language plpgsql;

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
  THEN RAISE EXCEPTION 'Nie mamy takiej cechy.';
  END IF;
  IF cecha = 'dokladnosc_badan'
  THEN
    RETURN QUERY
    WITH temp AS (
        SELECT
          al.id_lekarza                                                      AS id,
          sum(coalesce(al.dokladnosc_badan, 0)) / count(al.dokladnosc_badan) AS v
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
            al.id_lekarza                                                  AS id,
            sum(coalesce(al.informacyjnosc, 0)) / count(al.informacyjnosc) AS v
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
            al.id_lekarza                                          AS id,
            sum(coalesce(al.opanowanie, 0)) / count(al.opanowanie) AS v
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
          al.id_lekarza                                          AS id,
          sum(coalesce(al.uprzejmosc, 0)) / count(al.uprzejmosc) AS v
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

CREATE OR REPLACE FUNCTION ranking_alfabetyczny(d1 DATE, d2 DATE, id INTEGER)
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

INSERT INTO role (nazwa) VALUES ('lekarz');
INSERT INTO role (nazwa) VALUES ('zwolniony');