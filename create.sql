create table pacjenci
(
  id_pacjenta    serial  not null
    constraint pacjenci_pkey
    primary key,
  imie           varchar not null,
  nazwisko       varchar not null,
  pesel          char(11)
    constraint pacjenci_pesel_key
    unique,
  nr_paszportu   varchar
    constraint pacjenci_nr_paszportu_key
    unique,
  data_urodzenia date    not null,
  plec           char(1)    not null
    constraint pacjenci_plec_check
    check ((plec = 'M' :: bpchar) OR (plec = 'F' :: bpchar)),
  constraint pacjenci_check
  check ((pesel IS NOT NULL) OR (nr_paszportu IS NOT NULL))
);

create table pracownicy
(
  id_pracownika serial   not null
    constraint pracownicy_pkey
    primary key,
  imie          varchar  not null,
  nazwisko      varchar  not null,
  pesel         char(11) not null
    constraint pracownicy_pesel_key
    unique
);

create table specjalizacje
(
  id_specjalizacji serial  not null
    constraint specjalizacje_pkey
    primary key,
  nazwa            varchar not null
    constraint specjalizacje_nazwa_key
    unique
);

create table lekarze_specjalizacje
(
  id_lekarza       integer not null
    constraint lekarze_specjalizacje_id_lekarza_fkey
    references pracownicy,
  id_specjalizacji integer not null
    constraint lekarze_specjalizacje_id_specjalizacji_fkey
    references specjalizacje,
  constraint lekarze_specjalizacje_pkey
  primary key (id_lekarza, id_specjalizacji)
);

create table pacjenci_lpk
(
  id_pacjenta integer not null
    constraint pacjenci_lpk_id_pacjenta_fkey
    references pacjenci,
  id_lekarza  integer not null
    constraint pacjenci_lpk_id_lekarza_fkey
    references pracownicy,
  od          date    not null,
  "do"        date,
  constraint pacjenci_lpk_pkey
  primary key (id_pacjenta, od)
);



create table role
(
  id_roli serial  not null
    constraint role_pkey
    primary key,
  nazwa   varchar not null
    constraint role_nazwa_key
    unique
);

create table pracownicy_role
(
  id_roli       integer not null
    constraint pracownicy_role_id_roli_fkey
    references role,
  id_pracownika integer not null
    constraint pracownicy_role_id_pracownika_fkey
    references pracownicy,
  constraint pracownicy_role_pkey
  primary key (id_roli, id_pracownika)
);

create table cele_wizyty
(
  id_celu serial  not null
    constraint cele_wizyty_pkey
    primary key,
  nazwa   varchar not null
    constraint cele_wizyty_nazwa_key
    unique
);

create table wizyty_odbyte
(
  id_wizyty    integer   not null
    constraint wizyty_odbyte_pkey
    primary key,
  id_pacjenta  integer   not null
    constraint wizyty_odbyte_id_pacjenta_fkey
    references pacjenci,
  id_lekarza   integer   not null
    constraint wizyty_odbyte_id_lekarza_fkey
    references pracownicy,
  cel          integer   not null
    constraint wizyty_odbyte_cel_fkey
    references cele_wizyty,
  data         timestamp not null,
  czas_trwania interval,
  constraint wizyty_odbyte_id_pacjenta_id_lekarza_data_key
  unique (id_pacjenta, id_lekarza, data)
);

create table wizyty_planowane
(
  id_wizyty      serial    not null
    constraint wizyty_planowane_pkey
    primary key,
  id_pacjenta    integer   not null
    constraint wizyty_planowane_id_pacjenta_fkey
    references pacjenci,
  id_lekarza     integer   not null
    constraint wizyty_planowane_id_lekarza_fkey
    references pracownicy,
  cel            integer   not null
    constraint wizyty_planowane_cel_fkey
    references cele_wizyty,
  data           timestamp not null,
  szacowany_czas interval,
  constraint wizyty_planowane_id_pacjenta_id_lekarza_data_key
  unique (id_pacjenta, id_lekarza, data)
);

create table wydarzenia_medyczne
(
  id_wydarzenia serial  not null
    constraint wydarzenia_medyczne_pkey
    primary key,
  nazwa         varchar not null
    constraint wydarzenia_medyczne_nazwa_key
    unique
);

create table historia_medyczna
(
  id_pacjenta   integer   not null
    constraint historia_medyczna_id_pacjenta_fkey
    references pacjenci,
  id_wydarzenia integer   not null
    constraint historia_medyczna_id_wydarzenia_fkey
    references wydarzenia_medyczne,
  wizyta        integer
    constraint historia_medyczna_wizyta_fkey
    references wizyty_odbyte,
  od            timestamp not null,
  "do"          timestamp,
  constraint historia_medyczna_pkey
  primary key (id_pacjenta, id_wydarzenia, od),
  constraint historia_medyczna_check
  check (od < "do")
);

create table skierowania
(
  nr_skierowania  integer not null
    constraint skierowania_pkey
    primary key,
  wizyta          integer not null
    constraint skierowania_wizyta_fkey
    references wizyty_odbyte,
  specjalizacja   integer not null
    constraint skierowania_specjalizacja_fkey
    references specjalizacje,
  cel_skierowania integer not null
    constraint skierowania_cel_skierowania_fkey
    references cele_wizyty,
  opis            varchar not null,
  constraint skierowania_wizyta_specjalizacja_cel_skierowania_key
  unique (wizyta, specjalizacja, cel_skierowania)
);


