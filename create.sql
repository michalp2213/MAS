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
DROP VIEW IF EXISTS terminaz;
DROP FUNCTION IF EXISTS wizyte_odbyte_pacjenta( INTEGER );
DROP FUNCTION IF EXISTS wizyte_planowane_pacjenta( INTEGER );
DROP FUNCTION IF EXISTS czy_aktywny_lekarz( INTEGER );
DROP FUNCTION IF EXISTS lekarze_specjalizacje_check();
DROP FUNCTION IF EXISTS pacjent_check();
DROP FUNCTION IF EXISTS pesel_check( CHAR );
DROP FUNCTION IF EXISTS pacjent_lpk_check();
DROP FUNCTION IF EXISTS pracownik_check();
DROP FUNCTION IF EXISTS ranking( DATE, DATE );
DROP FUNCTION IF EXISTS ranking_alfabetyczny( DATE, DATE );
DROP FUNCTION IF EXISTS ranking_cecha( DATE, DATE, VARCHAR );
DROP FUNCTION IF EXISTS ranking_specjalizacje( DATE, DATE, INTEGER );
DROP FUNCTION IF EXISTS role_pracownika( INTEGER );
DROP FUNCTION IF EXISTS terminarz_insert();
DROP FUNCTION IF EXISTS specjalizacje_lekarza( INTEGER );
DROP FUNCTION IF EXISTS wizyta_odbyta_check();
DROP FUNCTION IF EXISTS wizyta_planowana_check();
DROP FUNCTION IF EXISTS nulls(ANYARRAY);

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
  data_urodzenia DATE    NOT NULL,
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
  od          DATE    NOT NULL,
  "do"        DATE CHECK (od <= "do"),
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
  id_celu SERIAL  NOT NULL
    PRIMARY KEY,
  nazwa   VARCHAR NOT NULL
    UNIQUE
);

CREATE TABLE wizyty_odbyte
(
  id_wizyty     INTEGER   NOT NULL
    PRIMARY KEY,
  id_pacjenta   INTEGER   NOT NULL
    REFERENCES pacjenci (id_pacjenta),
  id_lekarza    INTEGER   NOT NULL
    REFERENCES pracownicy (id_pracownika),
  cel           INTEGER   NOT NULL
    REFERENCES cele_wizyty (id_celu),
  specjalizacja INTEGER   NOT NULL
    REFERENCES specjalizacje (id_specjalizacji),
  data          TIMESTAMP NOT NULL,
  czas_trwania  INTERVAL  NOT NULL CHECK (czas_trwania > INTERVAL '0'),
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
  specjalizacja  INTEGER   NOT NULL
    REFERENCES specjalizacje (id_specjalizacji),
  data           TIMESTAMP NOT NULL,
  szacowany_czas INTERVAL CHECK (szacowany_czas > INTERVAL '0' ),
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
  dokladnosc_badan INTEGER CHECK (dokladnosc_badan >= 1 AND dokladnosc_badan <= 5),
  CHECK (uprzejmosc IS NOT NULL OR opanowanie IS NOT NULL OR
         informacyjnosc IS NOT NULL OR dokladnosc_badan IS NOT NULL)
);

create function nulls(VARIADIC a anyarray)
  returns integer
language plpgsql
as $$
DECLARE amount NUMERIC;
        i      INTEGER;
BEGIN
  amount = 0;
  FOR i IN 1..array_length(a, 1) LOOP
    IF a[i] IS NULL
    THEN amount = amount + 1;
    END IF;
  END LOOP;
  RETURN amount;
END;
$$;



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
  IF czy_aktywny_lekarz(NEW.id_lekarza) = FALSE
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
  IF czy_aktywny_lekarz(NEW.id_lekarza) = FALSE
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
  RETURNS TABLE(id_lekarza INTEGER, imie VARCHAR, nazwisko VARCHAR, wynik NUMERIC(3,2))
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
    ((v1 + v2 + v3 + v4) / (nulls(nullif(v1, 0)) + nulls(nullif(v2, 0)) + nulls(nullif(v3, 0)) + nulls(nullif(v4, 0))))::NUMERIC(3,2)
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
  IF czy_aktywny_lekarz(NEW.id_lekarza)
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
          al.id_lekarza                                                      AS id,
          (sum(coalesce(al.dokladnosc_badan, 0)) / count(al.dokladnosc_badan))::NUMERIC(3,2) AS v
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
            (sum(coalesce(al.informacyjnosc, 0)) / count(al.informacyjnosc))::NUMERIC(3,2) AS v
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
            (sum(coalesce(al.opanowanie, 0)) / count(al.opanowanie))::NUMERIC(3,2) AS v
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
          (sum(coalesce(al.uprzejmosc, 0)) / count(al.uprzejmosc))::NUMERIC(3,2) AS v
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

INSERT INTO role (nazwa) VALUES ('Lekarz');
INSERT INTO role (nazwa) VALUES ('Zwolniony');
INSERT INTO role (nazwa) VALUES ('Panek');
INSERT INTO role (nazwa) VALUES ('Pielęgniarka');
INSERT INTO role (nazwa) VALUES ('Pracownik techniczny');
INSERT INTO role (nazwa) VALUES ('Ochroniarz');
INSERT INTO role (nazwa) VALUES ('Zarząd');
INSERT INTO role (nazwa) VALUES ('Konserwator powierzchni płaskich');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Józef', 'Wasilewski', '67102435341', '24.10.1967', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Mateusz', 'Wójcik', '59091439262', '14.09.1959', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Waldemar', 'Wysocki', '02312279846', '22.11.2002', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Janusz', 'Kozłowski', '60051035989', '10.05.1960', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Tadeusz', 'Wojciechowski', '88042172628', '21.04.1988', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Wojciech', 'Górski', '63120726568', '07.12.1963', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Grzegorz', 'Bąk', '82013145947', '31.01.1982', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Władysław', 'Brzeziński', '71091543282', '15.09.1971', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Robert', 'Szewczyk', '61032585921', '25.03.1961', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Jacek', 'Sikorski', '83081848167', '18.08.1983', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Marcin', 'Jakubowski', '93100778683', '07.10.1993', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Janusz', 'Szymański', '64101132385', '11.10.1964', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Kazimierz', 'Rutkowski', '97111842447', '18.11.1997', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Tomasz', 'Krawczyk', '72112067189', '20.11.1972', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Jan', 'Jankowski', '54111394541', '13.11.1954', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Ryszard', 'Wiśniewski', '58050737223', '07.05.1958', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Łukasz', 'Borkowski', '94070365284', '03.07.1994', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Mariusz', 'Witkowski', '45071191965', '11.07.1945', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Michał', 'Stępień', '79081091469', '10.08.1979', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Michał', 'Rutkowski', '57073131241', '31.07.1957', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Piotr', 'Baranowski', '64111922549', '19.11.1964', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Waldemar', 'Kucharski', '65121145243', '11.12.1965', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Rafał', 'Brzeziński', '91100759288', '07.10.1991', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Józef', 'Kwiatkowski', '50030653145', '06.03.1950', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Sebastian', 'Wasilewski', '49110391446', '03.11.1949', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Grzegorz', 'Borkowski', '90080555547', '05.08.1990', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Jarosław', 'Chmielewski', '58072845467', '28.07.1958', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Przemysław', 'Witkowski', '65032579265', '25.03.1965', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Zdzisław', 'Kowalski', '56051759721', '17.05.1956', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Jarosław', 'Zając', '80070626324', '06.07.1980', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Jakub', 'Baranowski', '93120789665', '07.12.1993', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Wiesław', 'Wilk', '63012813747', '28.01.1963', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Artur', 'Adamski', '67032997483', '29.03.1967', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Marian', 'Wieczorek', '44091647447', '16.09.1944', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Kamil', 'Wiśniewski', '74102872445', '28.10.1974', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Daniel', 'Zakrzewski', '75101422167', '14.10.1975', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Roman', 'Chmielewski', '52052714268', '27.05.1952', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Marcin', 'Ostrowski', '55031924845', '19.03.1955', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Jacek', 'Czarnecki', '78092095862', '20.09.1978', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Grzegorz', 'Kozłowski', '87111153764', '11.11.1987', 'M');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Monika', 'Zakrzewska', '73110429148', '04.11.1973', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Urszula', 'Kucharska', '57062384649', '23.06.1957', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Patrycja', 'Baranowska', '74031472882', '14.03.1974', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Krystyna', 'Czerwińska', '64071584865', '15.07.1964', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Janina', 'Stępień', '02280826686', '08.08.2002', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Marzena', 'Gajewska', '44080853181', '08.08.1944', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Stefania', 'Nowakowska', '75040438984', '04.04.1975', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Stefania', 'Jasińska', '68081285925', '12.08.1968', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Aleksandra', 'Wojciechowska', '51122641628', '26.12.1951', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Danuta', 'Szewczyk', '75071711588', '17.07.1975', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Danuta', 'Mazur', '56082443983', '24.08.1956', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Magdalena', 'Ziółkowska', '60051975885', '19.05.1960', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Marzena', 'Rutkowska', '50110998663', '09.11.1950', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Natalia', 'Nowicka', '89011363366', '13.01.1989', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Dorota', 'Wieczorek', '63040163625', '01.04.1963', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Aneta', 'Głowacka', '77012219441', '22.01.1977', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Grażyna', 'Malinowska', '93122243424', '22.12.1993', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Grażyna', 'Sokołowska', '00272628786', '26.07.2000', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Teresa', 'Czarnecka', '60041452422', '14.04.1960', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Halina', 'Jankowska', '95112456168', '24.11.1995', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Jolanta', 'Kowalska', '84100214424', '02.10.1984', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Ewelina', 'Michalska', '68081254727', '12.08.1968', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Irena', 'Szulc', '62050988183', '09.05.1962', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Bożena', 'Malinowska', '59041562549', '15.04.1959', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Danuta', 'Zalewska', '53081336146', '13.08.1953', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Stefania', 'Borkowska', '43020735262', '07.02.1943', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Teresa', 'Duda', '43082686586', '26.08.1943', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Justyna', 'Wiśniewska', '87112327267', '23.11.1987', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Agata', 'Lis', '65031015368', '10.03.1965', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Elżbieta', 'Sikorska', '62022645687', '26.02.1962', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Agnieszka', 'Kowalczyk', '84102329267', '23.10.1984', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Natalia', 'Szewczyk', '49052319982', '23.05.1949', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Dorota', 'Gajewska', '90022063644', '20.02.1990', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Agata', 'Szczepańska', '90112128882', '21.11.1990', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Patrycja', 'Ziółkowska', '57112476285', '24.11.1957', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Bożena', 'Sikorska', '81053097227', '30.05.1981', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Danuta', 'Kaźmierczak', '54122515924', '25.12.1954', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Danuta', 'Kaczmarek', '78031127261', '11.03.1978', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Aleksandra', 'Tomaszewska', '97122423246', '24.12.1997', 'F');
INSERT INTO pacjenci (imie, nazwisko, pesel, data_urodzenia, plec)
VALUES ('Alicja', 'Kucharska', '49010814683', '08.01.1949', 'F');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Kazimierz', 'Kalinowski', '67102435341');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Maciej', 'Zając', '59091439262');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Czesław', 'Mazur', '02312279846');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Ryszard', 'Baranowski', '60051035989');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Waldemar', 'Chmielewski', '88042172628');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Tadeusz', 'Duda', '63120726568');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Michał', 'Stępień', '82013145947');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Władysław', 'Marciniak', '71091543282');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Jakub', 'Szymczak', '61032585921');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Kamil', 'Majewski', '83081848167');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Jan', 'Szewczyk', '93100778683');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Mieczysław', 'Dąbrowski', '64101132385');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Henryk', 'Walczak', '97111842447');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Rafał', 'Duda', '72112067189');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Marek', 'Wasilewski', '54111394541');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Zbigniew', 'Szczepański', '58050737223');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Józef', 'Piotrowski', '94070365284');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Jerzy', 'Nowicki', '45071191965');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Piotr', 'Olszewski', '79081091469');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Adam', 'Sikorski', '57073131241');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Mateusz', 'Wysocki', '64111922549');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Mateusz', 'Wiśniewski', '65121145243');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Michał', 'Adamski', '91100759288');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Kamil', 'Gajewski', '50030653145');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Henryk', 'Lis', '49110391446');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Roman', 'Ziółkowski', '90080555547');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Marcin', 'Pawłowski', '58072845467');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Jakub', 'Malinowski', '65032579265');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Wojciech', 'Lis', '56051759721');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Michał', 'Piotrowski', '80070626324');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Ryszard', 'Brzeziński', '93120789665');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Dariusz', 'Zawadzki', '63012813747');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Mirosław', 'Kowalski', '67032997483');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Henryk', 'Wróbel', '44091647447');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Andrzej', 'Kaźmierczak', '74102872445');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Maciej', 'Wojciechowski', '75101422167');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Dawid', 'Andrzejewski', '52052714268');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Roman', 'Kubiak', '55031924845');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Mateusz', 'Kowalczyk', '78092095862');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Stanisław', 'Wojciechowski', '87111153764');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Ewa', 'Lewandowska', '73110429148');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Grażyna', 'Makowska', '57062384649');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Ewelina', 'Bąk', '74031472882');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Paulina', 'Rutkowska', '64071584865');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Monika', 'Pietrzak', '02280826686');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Wiesława', 'Kalinowska', '44080853181');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Janina', 'Zalewska', '75040438984');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Danuta', 'Kwiatkowska', '68081285925');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Stefania', 'Zakrzewska', '51122641628');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Halina', 'Borkowska', '75071711588');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Izabela', 'Grabowska', '56082443983');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Izabela', 'Baranowska', '60051975885');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Ewelina', 'Witkowska', '50110998663');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Krystyna', 'Nowak', '89011363366');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Marta', 'Lis', '63040163625');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Jadwiga', 'Wojciechowska', '77012219441');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Stefania', 'Głowacka', '93122243424');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Jadwiga', 'Nowakowska', '00272628786');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Patrycja', 'Szczepańska', '60041452422');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Agata', 'Sobczak', '95112456168');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Barbara', 'Baran', '84100214424');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Stefania', 'Baran', '68081254727');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Aneta', 'Pawłowska', '62050988183');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Jolanta', 'Zając', '59041562549');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Maria', 'Wiśniewska', '53081336146');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Magdalena', 'Zając', '43020735262');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Marta', 'Duda', '43082686586');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Agata', 'Andrzejewska', '87112327267');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Bożena', 'Sikorska', '65031015368');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Grażyna', 'Adamska', '62022645687');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Dorota', 'Adamczyk', '84102329267');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Kazimiera', 'Wróbel', '49052319982');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Bożena', 'Wiśniewska', '90022063644');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Agnieszka', 'Baran', '90112128882');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Grażyna', 'Majewska', '57112476285');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Helena', 'Baran', '81053097227');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Patrycja', 'Michalska', '54122515924');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Paulina', 'Cieślak', '78031127261');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Katarzyna', 'Duda', '97122423246');
INSERT INTO pracownicy (imie, nazwisko, pesel) VALUES ('Magdalena', 'Kaczmarek', '49010814683');
INSERT INTO pracownicy_role VALUES (8, 1);
INSERT INTO pracownicy_role VALUES (1, 2);
INSERT INTO pracownicy_role VALUES (5, 3);
INSERT INTO pracownicy_role VALUES (1, 4);
INSERT INTO pracownicy_role VALUES (8, 5);
INSERT INTO pracownicy_role VALUES (1, 6);
INSERT INTO pracownicy_role VALUES (6, 7);
INSERT INTO pracownicy_role VALUES (1, 8);
INSERT INTO pracownicy_role VALUES (8, 9);
INSERT INTO pracownicy_role VALUES (1, 10);
INSERT INTO pracownicy_role VALUES (7, 11);
INSERT INTO pracownicy_role VALUES (1, 12);
INSERT INTO pracownicy_role VALUES (5, 13);
INSERT INTO pracownicy_role VALUES (4, 14);
INSERT INTO pracownicy_role VALUES (6, 15);
INSERT INTO pracownicy_role VALUES (1, 16);
INSERT INTO pracownicy_role VALUES (7, 17);
INSERT INTO pracownicy_role VALUES (4, 18);
INSERT INTO pracownicy_role VALUES (6, 19);
INSERT INTO pracownicy_role VALUES (4, 20);
INSERT INTO pracownicy_role VALUES (7, 21);
INSERT INTO pracownicy_role VALUES (1, 22);
INSERT INTO pracownicy_role VALUES (6, 23);
INSERT INTO pracownicy_role VALUES (1, 24);
INSERT INTO pracownicy_role VALUES (5, 25);
INSERT INTO pracownicy_role VALUES (1, 26);
INSERT INTO pracownicy_role VALUES (6, 27);
INSERT INTO pracownicy_role VALUES (1, 28);
INSERT INTO pracownicy_role VALUES (7, 29);
INSERT INTO pracownicy_role VALUES (4, 30);
INSERT INTO pracownicy_role VALUES (7, 31);
INSERT INTO pracownicy_role VALUES (1, 32);
INSERT INTO pracownicy_role VALUES (7, 33);
INSERT INTO pracownicy_role VALUES (1, 34);
INSERT INTO pracownicy_role VALUES (7, 35);
INSERT INTO pracownicy_role VALUES (1, 36);
INSERT INTO pracownicy_role VALUES (5, 37);
INSERT INTO pracownicy_role VALUES (4, 38);
INSERT INTO pracownicy_role VALUES (5, 39);
INSERT INTO pracownicy_role VALUES (4, 40);
INSERT INTO pracownicy_role VALUES (5, 41);
INSERT INTO pracownicy_role VALUES (4, 42);
INSERT INTO pracownicy_role VALUES (6, 43);
INSERT INTO pracownicy_role VALUES (1, 44);
INSERT INTO pracownicy_role VALUES (8, 45);
INSERT INTO pracownicy_role VALUES (1, 46);
INSERT INTO pracownicy_role VALUES (8, 47);
INSERT INTO pracownicy_role VALUES (1, 48);
INSERT INTO pracownicy_role VALUES (5, 49);
INSERT INTO pracownicy_role VALUES (4, 50);
INSERT INTO pracownicy_role VALUES (6, 51);
INSERT INTO pracownicy_role VALUES (4, 52);
INSERT INTO pracownicy_role VALUES (5, 53);
INSERT INTO pracownicy_role VALUES (1, 54);
INSERT INTO pracownicy_role VALUES (6, 55);
INSERT INTO pracownicy_role VALUES (1, 56);
INSERT INTO pracownicy_role VALUES (8, 57);
INSERT INTO pracownicy_role VALUES (4, 58);
INSERT INTO pracownicy_role VALUES (6, 59);
INSERT INTO pracownicy_role VALUES (1, 60);
INSERT INTO pracownicy_role VALUES (5, 61);
INSERT INTO pracownicy_role VALUES (4, 62);
INSERT INTO pracownicy_role VALUES (8, 63);
INSERT INTO pracownicy_role VALUES (4, 64);
INSERT INTO pracownicy_role VALUES (5, 65);
INSERT INTO pracownicy_role VALUES (1, 66);
INSERT INTO pracownicy_role VALUES (8, 67);
INSERT INTO pracownicy_role VALUES (1, 68);
INSERT INTO pracownicy_role VALUES (8, 69);
INSERT INTO pracownicy_role VALUES (1, 70);
INSERT INTO pracownicy_role VALUES (5, 71);
INSERT INTO pracownicy_role VALUES (4, 72);
INSERT INTO pracownicy_role VALUES (5, 73);
INSERT INTO pracownicy_role VALUES (4, 74);
INSERT INTO pracownicy_role VALUES (6, 75);
INSERT INTO pracownicy_role VALUES (1, 76);
INSERT INTO pracownicy_role VALUES (8, 77);
INSERT INTO pracownicy_role VALUES (4, 78);
INSERT INTO pracownicy_role VALUES (5, 79);
INSERT INTO pracownicy_role VALUES (4, 80);
INSERT INTO specjalizacje (nazwa) VALUES ('Alergologia');
INSERT INTO specjalizacje (nazwa) VALUES ('Anestezjologia');
INSERT INTO specjalizacje (nazwa) VALUES ('Chirurgia');
INSERT INTO specjalizacje (nazwa) VALUES ('Dermatologia');
INSERT INTO specjalizacje (nazwa) VALUES ('Epidemiologia');
INSERT INTO specjalizacje (nazwa) VALUES ('Gastroenterologia');
INSERT INTO specjalizacje (nazwa) VALUES ('Ginekologia');
INSERT INTO specjalizacje (nazwa) VALUES ('Immunologia');
INSERT INTO specjalizacje (nazwa) VALUES ('Kardiologia');
INSERT INTO specjalizacje (nazwa) VALUES ('Medycyna rodzinna');
INSERT INTO specjalizacje (nazwa) VALUES ('Neurologia');
INSERT INTO specjalizacje (nazwa) VALUES ('Okulistyka');
INSERT INTO specjalizacje (nazwa) VALUES ('Onkologia');
INSERT INTO specjalizacje (nazwa) VALUES ('Ortopedia');
INSERT INTO specjalizacje (nazwa) VALUES ('Pediatria');
INSERT INTO specjalizacje (nazwa) VALUES ('Psychiatria');
INSERT INTO specjalizacje (nazwa) VALUES ('Pulmonologia');
INSERT INTO specjalizacje (nazwa) VALUES ('Radiologia');
INSERT INTO specjalizacje (nazwa) VALUES ('Reumatologia');
INSERT INTO specjalizacje (nazwa) VALUES ('Seksuologia');
INSERT INTO specjalizacje (nazwa) VALUES ('Urologia');
INSERT INTO specjalizacje (nazwa) VALUES ('Wenerologia');
INSERT INTO lekarze_specjalizacje VALUES (2, 8);
INSERT INTO lekarze_specjalizacje VALUES (2, 7);
INSERT INTO lekarze_specjalizacje VALUES (4, 18);
INSERT INTO lekarze_specjalizacje VALUES (4, 4);
INSERT INTO lekarze_specjalizacje VALUES (4, 1);
INSERT INTO lekarze_specjalizacje VALUES (6, 13);
INSERT INTO lekarze_specjalizacje VALUES (6, 5);
INSERT INTO lekarze_specjalizacje VALUES (8, 15);
INSERT INTO lekarze_specjalizacje VALUES (8, 8);
INSERT INTO lekarze_specjalizacje VALUES (8, 22);
INSERT INTO lekarze_specjalizacje VALUES (10, 6);
INSERT INTO lekarze_specjalizacje VALUES (12, 13);
INSERT INTO lekarze_specjalizacje VALUES (12, 7);
INSERT INTO lekarze_specjalizacje VALUES (12, 8);
INSERT INTO lekarze_specjalizacje VALUES (16, 7);
INSERT INTO lekarze_specjalizacje VALUES (16, 20);
INSERT INTO lekarze_specjalizacje VALUES (16, 9);
INSERT INTO lekarze_specjalizacje VALUES (22, 18);
INSERT INTO lekarze_specjalizacje VALUES (22, 22);
INSERT INTO lekarze_specjalizacje VALUES (24, 22);
INSERT INTO lekarze_specjalizacje VALUES (26, 6);
INSERT INTO lekarze_specjalizacje VALUES (26, 5);
INSERT INTO lekarze_specjalizacje VALUES (26, 8);
INSERT INTO lekarze_specjalizacje VALUES (28, 4);
INSERT INTO lekarze_specjalizacje VALUES (32, 4);
INSERT INTO lekarze_specjalizacje VALUES (32, 8);
INSERT INTO lekarze_specjalizacje VALUES (32, 6);
INSERT INTO lekarze_specjalizacje VALUES (34, 22);
INSERT INTO lekarze_specjalizacje VALUES (34, 20);
INSERT INTO lekarze_specjalizacje VALUES (34, 4);
INSERT INTO lekarze_specjalizacje VALUES (36, 16);
INSERT INTO lekarze_specjalizacje VALUES (44, 1);
INSERT INTO lekarze_specjalizacje VALUES (44, 16);
INSERT INTO lekarze_specjalizacje VALUES (46, 6);
INSERT INTO lekarze_specjalizacje VALUES (48, 7);
INSERT INTO lekarze_specjalizacje VALUES (48, 16);
INSERT INTO lekarze_specjalizacje VALUES (48, 21);
INSERT INTO lekarze_specjalizacje VALUES (54, 11);
INSERT INTO lekarze_specjalizacje VALUES (56, 16);
INSERT INTO lekarze_specjalizacje VALUES (56, 1);
INSERT INTO lekarze_specjalizacje VALUES (56, 3);
INSERT INTO lekarze_specjalizacje VALUES (60, 17);
INSERT INTO lekarze_specjalizacje VALUES (60, 12);
INSERT INTO lekarze_specjalizacje VALUES (66, 18);
INSERT INTO lekarze_specjalizacje VALUES (66, 15);
INSERT INTO lekarze_specjalizacje VALUES (66, 3);
INSERT INTO lekarze_specjalizacje VALUES (68, 2);
INSERT INTO lekarze_specjalizacje VALUES (68, 14);
INSERT INTO lekarze_specjalizacje VALUES (68, 3);
INSERT INTO lekarze_specjalizacje VALUES (70, 11);
INSERT INTO lekarze_specjalizacje VALUES (70, 7);
INSERT INTO lekarze_specjalizacje VALUES (76, 21);
INSERT INTO lekarze_specjalizacje VALUES (76, 20);
INSERT INTO cele_wizyty(nazwa) VALUES ('Profilaktyka');
INSERT INTO cele_wizyty(nazwa) VALUES ('Badanie');
INSERT INTO cele_wizyty(nazwa) VALUES ('');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES ('Ciąża');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES ('Złamanie ręki');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES ('Złamanie nogi');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES ('Złamanie kręgosłupa');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES ('Złamanie szyi');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES ('Złamanie palca');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Padaczka');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Stwardnienie zanikowe boczne');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Choroba i zespół Parkinsona');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Choroba Alzheimera');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Choroba Huntingtona');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Choroba Creutzfeldta-Jakoba');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Zespół Guillaina-Barrégo');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Stwardnienie rozsiane');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Migrena');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Klasterowy ból głowy');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Udar mózgu');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Zapalenie opon mózgowych');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Encefalopatia wątrobowa');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Kiła');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Rzeżączka');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Chlamydiozy');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Wrzód weneryczny');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Czerwonka bakteryjna');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Ziarniniak pachwinowy');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Mycoplasma genitalium');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('AIDS');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Opryszczka genitaliów, opryszczka wargowa');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('WZW');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Kłykciny kończyste');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('HTLV');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Kandydoza');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Rzęsistkowica');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Świerzb');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Wszawica łonowa');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Ameboza');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Toczeń rumieniowaty układowy');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Twardzina');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Układowe zapalenia naczyń');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Reumatoidalne zapalenie stawów');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Reaktywne zapalenie stawów');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Zesztywniające zapalenie stawów kręgosłupa');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Łuszczycowe zapalenie stawów');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Miażdżyca');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Choroba niedokrwienna serca');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Zaburzenia rytmu i przewodzenia');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Wady serca (wrodzone i nabyte)');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Choroby wsierdzia');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Choroby mięśnia sercowego');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Choroby osierdzia');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Nowotwór serca');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Niewydolność serca');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Nadciśnienie tętnicze');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Nadciśnienie płucne');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Choroby aorty i naczyń obwodowych');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Choroby żył obwodowych');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Żylna choroba zakrzepowo-zatorowa');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Choroby mikrokrążenia');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Choroby naczyń chłonnych');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Jęczmyk');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Ropień powieki');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Półpasiec powieki i gałki ocznej');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Ospica');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Zapalenie brzegów powiek');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Kępki żółte');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Brodawka powieki');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Naczyniak powieki');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Rak powieki');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Niedomykalność szpary powiekowej');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Wywinięcie powieki');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Podwinięcie powieki');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Nieprawidłowy wzrost rzęs');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Tik powieki');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Nadmierne częste mruganie');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Akrofobia');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Alkoholowy zespół abstynencyjny');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Anhedonia');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Anoreksja');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Anxiety neurosis');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Autyzm');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Bezsenność');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Błędne koło');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Bulimia');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Choroba afektywna');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Choroba sieroca');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Ciąża rzekoma');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Depresja');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Depresja poporodowa');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Dysgrafia');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Dyskalkulia rozwojowa');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Dysortografia');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Dystymia');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Egocentryzm');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Fobia');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Hipochondria');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Histeria');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Homofobia');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Jadłowstręt psychiczny');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Klaustrofobia');
INSERT INTO wydarzenia_medyczne(nazwa) VALUES('Lęk');
INSERT INTO pacjenci_lpk VALUES(1, 68, DATE '1967-10-24' + INTERVAL '10 YEARS', DATE '1967-10-24' + INTERVAL '10 YEARS'+ INTERVAL '8 MONTHS');
INSERT INTO pacjenci_lpk VALUES(2, 68, DATE '1959-09-14' + INTERVAL '16 YEARS', DATE '1959-09-14' + INTERVAL '16 YEARS'+ INTERVAL '18 MONTHS');
INSERT INTO pacjenci_lpk VALUES(3, 68, DATE '2002-11-22' + INTERVAL '20 YEARS', DATE '2002-11-22' + INTERVAL '20 YEARS'+ INTERVAL '3 MONTHS');
INSERT INTO pacjenci_lpk VALUES(4, 60, DATE '1960-05-10' + INTERVAL '11 YEARS', DATE '1960-05-10' + INTERVAL '11 YEARS'+ INTERVAL '14 MONTHS');
INSERT INTO pacjenci_lpk VALUES(5, 12, DATE '1988-04-21' + INTERVAL '17 YEARS', DATE '1988-04-21' + INTERVAL '17 YEARS'+ INTERVAL '17 MONTHS');
INSERT INTO pacjenci_lpk VALUES(6, 54, DATE '1963-12-07' + INTERVAL '9 YEARS', DATE '1963-12-07' + INTERVAL '9 YEARS'+ INTERVAL '17 MONTHS');
INSERT INTO pacjenci_lpk VALUES(7, 70, DATE '1982-01-31' + INTERVAL '9 YEARS', DATE '1982-01-31' + INTERVAL '9 YEARS'+ INTERVAL '5 MONTHS');
INSERT INTO pacjenci_lpk VALUES(8, 26, DATE '1971-09-15' + INTERVAL '14 YEARS', DATE '1971-09-15' + INTERVAL '14 YEARS'+ INTERVAL '8 MONTHS');
INSERT INTO pacjenci_lpk VALUES(9, 36, DATE '1961-03-25' + INTERVAL '14 YEARS', DATE '1961-03-25' + INTERVAL '14 YEARS'+ INTERVAL '1 MONTHS');
INSERT INTO pacjenci_lpk VALUES(10, 28, DATE '1983-08-18' + INTERVAL '12 YEARS', DATE '1983-08-18' + INTERVAL '12 YEARS'+ INTERVAL '2 MONTHS');
INSERT INTO pacjenci_lpk VALUES(11, 36, DATE '1993-10-07' + INTERVAL '17 YEARS', DATE '1993-10-07' + INTERVAL '17 YEARS'+ INTERVAL '13 MONTHS');
INSERT INTO pacjenci_lpk VALUES(12, 44, DATE '1964-10-11' + INTERVAL '11 YEARS', DATE '1964-10-11' + INTERVAL '11 YEARS'+ INTERVAL '18 MONTHS');
INSERT INTO pacjenci_lpk VALUES(13, 56, DATE '1997-11-18' + INTERVAL '10 YEARS', DATE '1997-11-18' + INTERVAL '10 YEARS'+ INTERVAL '2 MONTHS');
INSERT INTO pacjenci_lpk VALUES(14, 54, DATE '1972-11-20' + INTERVAL '15 YEARS', DATE '1972-11-20' + INTERVAL '15 YEARS'+ INTERVAL '6 MONTHS');
INSERT INTO pacjenci_lpk VALUES(15, 26, DATE '1954-11-13' + INTERVAL '14 YEARS', DATE '1954-11-13' + INTERVAL '14 YEARS'+ INTERVAL '17 MONTHS');
INSERT INTO pacjenci_lpk VALUES(16, 32, DATE '1958-05-07' + INTERVAL '20 YEARS', DATE '1958-05-07' + INTERVAL '20 YEARS'+ INTERVAL '8 MONTHS');
INSERT INTO pacjenci_lpk VALUES(17, 6, DATE '1994-07-03' + INTERVAL '13 YEARS', DATE '1994-07-03' + INTERVAL '13 YEARS'+ INTERVAL '15 MONTHS');
INSERT INTO pacjenci_lpk VALUES(18, 16, DATE '1945-07-11' + INTERVAL '14 YEARS', DATE '1945-07-11' + INTERVAL '14 YEARS'+ INTERVAL '8 MONTHS');
INSERT INTO pacjenci_lpk VALUES(19, 56, DATE '1979-08-10' + INTERVAL '20 YEARS', DATE '1979-08-10' + INTERVAL '20 YEARS'+ INTERVAL '15 MONTHS');
INSERT INTO pacjenci_lpk VALUES(20, 36, DATE '1957-07-31' + INTERVAL '15 YEARS', DATE '1957-07-31' + INTERVAL '15 YEARS'+ INTERVAL '19 MONTHS');
INSERT INTO pacjenci_lpk VALUES(21, 48, DATE '1964-11-19' + INTERVAL '11 YEARS', DATE '1964-11-19' + INTERVAL '11 YEARS'+ INTERVAL '9 MONTHS');
INSERT INTO pacjenci_lpk VALUES(22, 54, DATE '1965-12-11' + INTERVAL '14 YEARS', DATE '1965-12-11' + INTERVAL '14 YEARS'+ INTERVAL '8 MONTHS');
INSERT INTO pacjenci_lpk VALUES(23, 6, DATE '1991-10-07' + INTERVAL '9 YEARS', DATE '1991-10-07' + INTERVAL '9 YEARS'+ INTERVAL '12 MONTHS');
INSERT INTO pacjenci_lpk VALUES(24, 44, DATE '1950-03-06' + INTERVAL '15 YEARS', DATE '1950-03-06' + INTERVAL '15 YEARS'+ INTERVAL '16 MONTHS');
INSERT INTO pacjenci_lpk VALUES(25, 26, DATE '1949-11-03' + INTERVAL '18 YEARS', DATE '1949-11-03' + INTERVAL '18 YEARS'+ INTERVAL '15 MONTHS');
INSERT INTO pacjenci_lpk VALUES(26, 76, DATE '1990-08-05' + INTERVAL '20 YEARS', DATE '1990-08-05' + INTERVAL '20 YEARS'+ INTERVAL '12 MONTHS');
INSERT INTO pacjenci_lpk VALUES(27, 16, DATE '1958-07-28' + INTERVAL '11 YEARS', DATE '1958-07-28' + INTERVAL '11 YEARS'+ INTERVAL '16 MONTHS');
INSERT INTO pacjenci_lpk VALUES(28, 70, DATE '1965-03-25' + INTERVAL '13 YEARS', DATE '1965-03-25' + INTERVAL '13 YEARS'+ INTERVAL '6 MONTHS');
INSERT INTO pacjenci_lpk VALUES(29, 48, DATE '1956-05-17' + INTERVAL '10 YEARS', DATE '1956-05-17' + INTERVAL '10 YEARS'+ INTERVAL '3 MONTHS');
INSERT INTO pacjenci_lpk VALUES(30, 36, DATE '1980-07-06' + INTERVAL '17 YEARS', DATE '1980-07-06' + INTERVAL '17 YEARS'+ INTERVAL '18 MONTHS');
INSERT INTO pacjenci_lpk VALUES(31, 22, DATE '1993-12-07' + INTERVAL '12 YEARS', DATE '1993-12-07' + INTERVAL '12 YEARS'+ INTERVAL '14 MONTHS');
INSERT INTO pacjenci_lpk VALUES(32, 22, DATE '1963-01-28' + INTERVAL '13 YEARS', DATE '1963-01-28' + INTERVAL '13 YEARS'+ INTERVAL '19 MONTHS');
INSERT INTO pacjenci_lpk VALUES(33, 8, DATE '1967-03-29' + INTERVAL '14 YEARS', DATE '1967-03-29' + INTERVAL '14 YEARS'+ INTERVAL '1 MONTHS');
INSERT INTO pacjenci_lpk VALUES(34, 32, DATE '1944-09-16' + INTERVAL '20 YEARS', DATE '1944-09-16' + INTERVAL '20 YEARS'+ INTERVAL '6 MONTHS');
INSERT INTO pacjenci_lpk VALUES(35, 12, DATE '1974-10-28' + INTERVAL '20 YEARS', DATE '1974-10-28' + INTERVAL '20 YEARS'+ INTERVAL '2 MONTHS');
INSERT INTO pacjenci_lpk VALUES(36, 34, DATE '1975-10-14' + INTERVAL '9 YEARS', DATE '1975-10-14' + INTERVAL '9 YEARS'+ INTERVAL '20 MONTHS');
INSERT INTO pacjenci_lpk VALUES(37, 32, DATE '1952-05-27' + INTERVAL '9 YEARS', DATE '1952-05-27' + INTERVAL '9 YEARS'+ INTERVAL '15 MONTHS');
INSERT INTO pacjenci_lpk VALUES(38, 76, DATE '1955-03-19' + INTERVAL '11 YEARS', DATE '1955-03-19' + INTERVAL '11 YEARS'+ INTERVAL '4 MONTHS');
INSERT INTO pacjenci_lpk VALUES(39, 56, DATE '1978-09-20' + INTERVAL '15 YEARS', DATE '1978-09-20' + INTERVAL '15 YEARS'+ INTERVAL '18 MONTHS');
INSERT INTO pacjenci_lpk VALUES(40, 12, DATE '1987-11-11' + INTERVAL '20 YEARS', DATE '1987-11-11' + INTERVAL '20 YEARS'+ INTERVAL '18 MONTHS');
INSERT INTO pacjenci_lpk VALUES(41, 56, DATE '1973-11-04' + INTERVAL '14 YEARS', DATE '1973-11-04' + INTERVAL '14 YEARS'+ INTERVAL '10 MONTHS');
INSERT INTO pacjenci_lpk VALUES(42, 34, DATE '1957-06-23' + INTERVAL '20 YEARS', DATE '1957-06-23' + INTERVAL '20 YEARS'+ INTERVAL '5 MONTHS');
INSERT INTO pacjenci_lpk VALUES(43, 10, DATE '1974-03-14' + INTERVAL '19 YEARS', DATE '1974-03-14' + INTERVAL '19 YEARS'+ INTERVAL '14 MONTHS');
INSERT INTO pacjenci_lpk VALUES(44, 76, DATE '1964-07-15' + INTERVAL '9 YEARS', DATE '1964-07-15' + INTERVAL '9 YEARS'+ INTERVAL '4 MONTHS');
INSERT INTO pacjenci_lpk VALUES(45, 4, DATE '2002-08-08' + INTERVAL '9 YEARS', DATE '2002-08-08' + INTERVAL '9 YEARS'+ INTERVAL '18 MONTHS');
INSERT INTO pacjenci_lpk VALUES(46, 56, DATE '1944-08-08' + INTERVAL '17 YEARS', DATE '1944-08-08' + INTERVAL '17 YEARS'+ INTERVAL '19 MONTHS');
INSERT INTO pacjenci_lpk VALUES(47, 66, DATE '1975-04-04' + INTERVAL '20 YEARS', DATE '1975-04-04' + INTERVAL '20 YEARS'+ INTERVAL '20 MONTHS');
INSERT INTO pacjenci_lpk VALUES(48, 28, DATE '1968-08-12' + INTERVAL '20 YEARS', DATE '1968-08-12' + INTERVAL '20 YEARS'+ INTERVAL '7 MONTHS');
INSERT INTO pacjenci_lpk VALUES(49, 66, DATE '1951-12-26' + INTERVAL '14 YEARS', DATE '1951-12-26' + INTERVAL '14 YEARS'+ INTERVAL '11 MONTHS');
INSERT INTO pacjenci_lpk VALUES(50, 22, DATE '1975-07-17' + INTERVAL '20 YEARS', DATE '1975-07-17' + INTERVAL '20 YEARS'+ INTERVAL '19 MONTHS');
INSERT INTO pacjenci_lpk VALUES(51, 54, DATE '1956-08-24' + INTERVAL '16 YEARS', DATE '1956-08-24' + INTERVAL '16 YEARS'+ INTERVAL '4 MONTHS');
INSERT INTO pacjenci_lpk VALUES(52, 10, DATE '1960-05-19' + INTERVAL '17 YEARS', DATE '1960-05-19' + INTERVAL '17 YEARS'+ INTERVAL '7 MONTHS');
INSERT INTO pacjenci_lpk VALUES(53, 60, DATE '1950-11-09' + INTERVAL '10 YEARS', DATE '1950-11-09' + INTERVAL '10 YEARS'+ INTERVAL '10 MONTHS');
INSERT INTO pacjenci_lpk VALUES(54, 12, DATE '1989-01-13' + INTERVAL '20 YEARS', DATE '1989-01-13' + INTERVAL '20 YEARS'+ INTERVAL '8 MONTHS');
INSERT INTO pacjenci_lpk VALUES(55, 4, DATE '1963-04-01' + INTERVAL '20 YEARS', DATE '1963-04-01' + INTERVAL '20 YEARS'+ INTERVAL '16 MONTHS');
INSERT INTO pacjenci_lpk VALUES(56, 48, DATE '1977-01-22' + INTERVAL '11 YEARS', DATE '1977-01-22' + INTERVAL '11 YEARS'+ INTERVAL '10 MONTHS');
INSERT INTO pacjenci_lpk VALUES(57, 16, DATE '1993-12-22' + INTERVAL '13 YEARS', DATE '1993-12-22' + INTERVAL '13 YEARS'+ INTERVAL '7 MONTHS');
INSERT INTO pacjenci_lpk VALUES(58, 10, DATE '2000-07-26' + INTERVAL '11 YEARS', DATE '2000-07-26' + INTERVAL '11 YEARS'+ INTERVAL '16 MONTHS');
INSERT INTO pacjenci_lpk VALUES(59, 36, DATE '1960-04-14' + INTERVAL '9 YEARS', DATE '1960-04-14' + INTERVAL '9 YEARS'+ INTERVAL '14 MONTHS');
INSERT INTO pacjenci_lpk VALUES(60, 10, DATE '1995-11-24' + INTERVAL '15 YEARS', DATE '1995-11-24' + INTERVAL '15 YEARS'+ INTERVAL '3 MONTHS');
INSERT INTO pacjenci_lpk VALUES(61, 32, DATE '1984-10-02' + INTERVAL '12 YEARS', DATE '1984-10-02' + INTERVAL '12 YEARS'+ INTERVAL '18 MONTHS');
INSERT INTO pacjenci_lpk VALUES(62, 22, DATE '1968-08-12' + INTERVAL '11 YEARS', DATE '1968-08-12' + INTERVAL '11 YEARS'+ INTERVAL '7 MONTHS');
INSERT INTO pacjenci_lpk VALUES(63, 12, DATE '1962-05-09' + INTERVAL '14 YEARS', DATE '1962-05-09' + INTERVAL '14 YEARS'+ INTERVAL '2 MONTHS');
INSERT INTO pacjenci_lpk VALUES(64, 70, DATE '1959-04-15' + INTERVAL '10 YEARS', DATE '1959-04-15' + INTERVAL '10 YEARS'+ INTERVAL '12 MONTHS');
INSERT INTO pacjenci_lpk VALUES(65, 60, DATE '1953-08-13' + INTERVAL '16 YEARS', DATE '1953-08-13' + INTERVAL '16 YEARS'+ INTERVAL '20 MONTHS');
INSERT INTO pacjenci_lpk VALUES(66, 34, DATE '1943-02-07' + INTERVAL '9 YEARS', DATE '1943-02-07' + INTERVAL '9 YEARS'+ INTERVAL '13 MONTHS');
INSERT INTO pacjenci_lpk VALUES(67, 76, DATE '1943-08-26' + INTERVAL '20 YEARS', DATE '1943-08-26' + INTERVAL '20 YEARS'+ INTERVAL '14 MONTHS');
INSERT INTO pacjenci_lpk VALUES(68, 22, DATE '1987-11-23' + INTERVAL '17 YEARS', DATE '1987-11-23' + INTERVAL '17 YEARS'+ INTERVAL '6 MONTHS');
INSERT INTO pacjenci_lpk VALUES(69, 66, DATE '1965-03-10' + INTERVAL '11 YEARS', DATE '1965-03-10' + INTERVAL '11 YEARS'+ INTERVAL '14 MONTHS');
INSERT INTO pacjenci_lpk VALUES(70, 28, DATE '1962-02-26' + INTERVAL '17 YEARS', DATE '1962-02-26' + INTERVAL '17 YEARS'+ INTERVAL '17 MONTHS');
INSERT INTO pacjenci_lpk VALUES(71, 4, DATE '1984-10-23' + INTERVAL '17 YEARS', DATE '1984-10-23' + INTERVAL '17 YEARS'+ INTERVAL '12 MONTHS');
INSERT INTO pacjenci_lpk VALUES(72, 10, DATE '1949-05-23' + INTERVAL '20 YEARS', DATE '1949-05-23' + INTERVAL '20 YEARS'+ INTERVAL '3 MONTHS');
INSERT INTO pacjenci_lpk VALUES(73, 66, DATE '1990-02-20' + INTERVAL '9 YEARS', DATE '1990-02-20' + INTERVAL '9 YEARS'+ INTERVAL '5 MONTHS');
INSERT INTO pacjenci_lpk VALUES(74, 56, DATE '1990-11-21' + INTERVAL '15 YEARS', DATE '1990-11-21' + INTERVAL '15 YEARS'+ INTERVAL '19 MONTHS');
INSERT INTO pacjenci_lpk VALUES(75, 54, DATE '1957-11-24' + INTERVAL '18 YEARS', DATE '1957-11-24' + INTERVAL '18 YEARS'+ INTERVAL '6 MONTHS');
INSERT INTO pacjenci_lpk VALUES(76, 22, DATE '1981-05-30' + INTERVAL '15 YEARS', DATE '1981-05-30' + INTERVAL '15 YEARS'+ INTERVAL '3 MONTHS');
INSERT INTO pacjenci_lpk VALUES(77, 70, DATE '1954-12-25' + INTERVAL '15 YEARS', DATE '1954-12-25' + INTERVAL '15 YEARS'+ INTERVAL '4 MONTHS');
INSERT INTO pacjenci_lpk VALUES(78, 34, DATE '1978-03-11' + INTERVAL '9 YEARS', DATE '1978-03-11' + INTERVAL '9 YEARS'+ INTERVAL '4 MONTHS');
INSERT INTO pacjenci_lpk VALUES(79, 48, DATE '1997-12-24' + INTERVAL '11 YEARS', DATE '1997-12-24' + INTERVAL '11 YEARS'+ INTERVAL '6 MONTHS');
INSERT INTO pacjenci_lpk VALUES(80, 56, DATE '1949-01-08' + INTERVAL '19 YEARS', DATE '1949-01-08' + INTERVAL '19 YEARS'+ INTERVAL '14 MONTHS');