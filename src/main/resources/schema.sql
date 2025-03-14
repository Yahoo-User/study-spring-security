-- Oracle19cR3

DROP TABLE member PURGE;

CREATE TABLE member (
    id VARCHAR2(10) PRIMARY KEY,
    password VARCHAR2(100),
    name VARCHAR2(30),
    role VARCHAR2(12),
    enabled NUMBER(1, 0) CHECK (enabled = 0 OR enabled = 1)
);

INSERT INTO member VALUES ( 'member', 'member', 'MEMBER', 'ROLE_MEMBER', 1 );
INSERT INTO member VALUES ( 'manager', 'manager', 'MANAGER', 'ROLE_MANAGER', 1);
INSERT INTO member VALUES ( 'admin', 'admin', 'ADMIN', 'ROLE_ADMIN', 1);

COMMIT;

SELECT * FROM member;


-- H2

USE jpa_schema;

DROP TABLE IF EXISTS member CASCADE;

CREATE TABLE member (
    id VARCHAR(10) PRIMARY KEY,
    password VARCHAR(100),
    name VARCHAR(30),
    role VARCHAR(12),
    enabled BOOLEAN CHECK (enabled = TRUE OR enabled = FALSE)
);

INSERT INTO member VALUES ( 'member', 'member', 'MEMBER', 'ROLE_MEMBER', TRUE );
INSERT INTO member VALUES ( 'manager', 'manager', 'MANAGER', 'ROLE_MANAGER', TRUE );
INSERT INTO member VALUES ( 'admin', 'admin', 'ADMIN', 'ROLE_ADMIN', TRUE );

COMMIT;

SELECT * FROM member;


-- MySQL8

USE scott;

DROP TABLE IF EXISTS member CASCADE;

CREATE TABLE member (
    id VARCHAR(10) PRIMARY KEY,
    password VARCHAR(100),
    name VARCHAR(30),
    role VARCHAR(12),
    enabled TINYINT(1) NOT NULL CHECK (enabled IN (0, 1))
);

INSERT INTO member VALUES ( 'member', 'member', 'MEMBER', 'ROLE_MEMBER', 1 );
INSERT INTO member VALUES ( 'manager', 'manager', 'MANAGER', 'ROLE_MANAGER', 1);
INSERT INTO member VALUES ('admin', 'admin', 'ADMIN', 'ROLE_ADMIN', 1);

COMMIT;

SELECT * FROM member;





