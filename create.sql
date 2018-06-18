\i clear.sql

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
    UNIQUE,
  zatrudniony_od DATE NOT NULL,
  zatrudniony_do DATE
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


\i util/insertfunctions.sql
\i util/insertrole.sql
\i util/insertpacj.sql
\i util/insertprac.sql
\i util/insertprac_role.sql
\i util/insertspecjalizacje.sql
\i util/insertlekarze_sp.sql
INSERT INTO cele_wizyty (nazwa) VALUES ('Profilaktyka');
INSERT INTO cele_wizyty (nazwa) VALUES ('Badanie');
INSERT INTO cele_wizyty (nazwa) VALUES ('');
\i util/insertwydarzenia.sql
\i util/insertlpk.sql