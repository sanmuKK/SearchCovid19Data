CREATE TABLE countries_covid
(
    id                  int AUTO_INCREMENT,
    country_name        varchar(20) NOT NULL default '' UNIQUE,
    confirmed           int         NOT NULL default 0,
    recovered           int         NOT NULL default 0,
    deaths              int         NOT NULL default 0,
    population          int         NOT NULL default 0,
    sq_km_area          int         NOT NULL default 0,
    life_expectancy     varchar(20) NOT NULL default 0,
    elevation_in_meters varchar(20) NOT NULL default '',
    continent           varchar(20) NOT NULL default '',
    abbreviation        varchar(20) NOT NULL default '',
    location            varchar(20) NOT NULL default '',
    iso                 int         NOT NULL default 0,
    capital_city        varchar(20) NOT NULL default '',
    PRIMARY KEY (id)
);
CREATE TABLE provinces_covid
(
    id            int AUTO_INCREMENT,
    province_name varchar(40) NOT NULL default '' UNIQUE,
    lat           varchar(20) NOT NULL default '',
    long_data     varchar(20) NOT NULL default '',
    confirmed     int         NOT NULL default 0,
    recovered     int         NOT NULL default 0,
    deaths        int         NOT NULL default 0,
    updated       varchar(30)          default '',
    country_id    int,
    PRIMARY KEY (id),
    CONSTRAINT country_province FOREIGN KEY (country_id) REFERENCES countries_covid (id)
);