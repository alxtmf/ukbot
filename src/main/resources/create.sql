/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altmf
 * Created: 27.11.2017
 */

CREATE USER IF NOT EXISTS LOCAL_ADMIN PASSWORD 123 ADMIN;

DROP SCHEMA IF EXISTS UK;

CREATE SCHEMA IF NOT EXISTS UK;

CREATE ROLE IF NOT EXISTS ALL_RIGHT;

GRANT ALL_RIGHT TO LOCAL_ADMIN;

GRANT ALTER ANY SCHEMA TO LOCAL_ADMIN;

SET SCHEMA UK;

-------------------------------РОЛИ-------------------------------------------------
CREATE TABLE UK.CLS_USER(
    ID              BIGINT IDENTITY,
    ID_employee     BIGINT,
    IS_DELETED      INT DEFAULT 0,
    LOGIN           VARCHAR(255),
    PASSWORD        BLOB
);

CREATE TABLE UK.CLS_ROLE(
    ID              BIGINT IDENTITY,
    IS_DELETED      INT DEFAULT 0,
    NAME            VARCHAR(255),
    CODE            VARCHAR(255)
);

CREATE TABLE UK.CLS_RESOURCE(
    ID              BIGINT IDENTITY,
    IS_DELETED      INT DEFAULT 0,
    NAME            VARCHAR(255),
    PATH            VARCHAR(255),
    CODE            VARCHAR(255)
);

CREATE TABLE UK.CLS_CVITANTION(
    ID              BIGINT IDENTITY,
    ID_RESOURCE     BIGINT NOT NULL,
    ID_ROLE         BIGINT NOT NULL,
    IS_DELETED      INT DEFAULT 0,
    OPERATION       VARCHAR(4)
);

CREATE TABLE UK.REG_USE_ROLE(
    ID              BIGINT IDENTITY,
    ID_ROLE         BIGINT NOT NULL,
    IS_DELETED      INT DEFAULT 0,
    NAME            VARCHAR(255)
);
--------------------------------------------------------------------------------
CREATE TABLE UK.CLS_PERIOD(
    ID              BIGINT IDENTITY,
    IS_DELETED      INT DEFAULT 0,
    DATE_BEGIN      TIMESTAMP,
    DATE_END        TIMESTAMP
);
CREATE TABLE UK.CLS_METERING_DEVICE_TYPE(
    ID              BIGINT IDENTITY,
    IS_DELETED      INT DEFAULT 0,
    NAME            VARCHAR(255)
);
--------------------------------------------------------------------------------
CREATE TABLE UK.CLS_CUSTOMER(
    ID              BIGINT IDENTITY,
    ID_TELEGRAM     BIGINT,
    ID_CHAT         BIGINT,
    IS_DELETED      INT DEFAULT 0,
    FAM             VARCHAR(255),
    IM              VARCHAR(255),
    OTC             VARCHAR(255)
);

CREATE TABLE UK.CLS_HOUSE(
    ID              BIGINT IDENTITY,
    IS_DELETED      INT DEFAULT 0,
    STREET          VARCHAR(255),
    NUMBER          VARCHAR(15)
);

CREATE TABLE UK.CLS_APARTMENT(
    ID              BIGINT IDENTITY,
    ID_HOUSE        BIGINT,
    IS_DELETED      INT DEFAULT 0,
    NUMBER          VARCHAR(255),
    FOREIGN KEY(ID_HOUSE) REFERENCES UK.CLS_HOUSE(ID)
);
--------------------------------------------------------------------------------
-- приборы учета по квартирам
CREATE TABLE UK.REG_APARTMENT_METERING_DEVICE(
    ID                         BIGINT IDENTITY,
    ID_APARTMENT               BIGINT,
    ID_METERING_DEVICE_TYPE    BIGINT,
    IS_DELETED                 INT DEFAULT 0,
    SERIAL                     VARCHAR(63),
    FOREIGN KEY(ID_APARTMENT) REFERENCES UK.CLS_APARTMENT(ID),
    FOREIGN KEY(ID_METERING_DEVICE_TYPE) REFERENCES UK.CLS_METERING_DEVICE_TYPE(ID)
);

--показания приборов учета
CREATE TABLE UK.REG_METERING_DEVICE_RECORDS(
    ID                              BIGINT IDENTITY,
    ID_APARTMENT_METERING_DEVICE    BIGINT,
    ID_PERIOD                       BIGINT,
    ID_CUSTOMER                     BIGINT,
    IS_DELETED                      INT DEFAULT 0,
    DATE_REG                        TIMESTAMP,
    INT_PART                        INT,    
    FRACT_PART                      INT,
    RAW_RECORD                      NUMERIC(15,4),
    FOREIGN KEY(ID_APARTMENT_METERING_DEVICE) REFERENCES UK.REG_APARTMENT_METERING_DEVICE(ID),
    FOREIGN KEY(ID_PERIOD) REFERENCES UK.CLS_PERIOD(ID),
    FOREIGN KEY(ID_CUSTOMER) REFERENCES UK.CLS_CUSTOMER(ID)
); 

--кто за какие квартиры может отправлять и просматривать показания
CREATE TABLE UK.REG_APARTMENT_METERING_SENDER(
    ID              BIGINT IDENTITY,
    ID_APARTMENT    BIGINT,
    ID_CUSTOMER     BIGINT,
    IS_DELETED      INT DEFAULT 0,
    DATE_BEGIN      TIMESTAMP,
    DATE_END        TIMESTAMP,
    FOREIGN KEY(ID_APARTMENT) REFERENCES UK.CLS_APARTMENT(ID),
    FOREIGN KEY(ID_CUSTOMER) REFERENCES UK.CLS_CUSTOMER(ID)
);