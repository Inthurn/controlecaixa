CREATE TABLE
    IF NOT EXISTS users (
        id SERIAL PRIMARY KEY,
        username VARCHAR(255) NOT NULL UNIQUE,
        email VARCHAR(255) NOT NULL UNIQUE,
        password VARCHAR(255) NOT NULL
    );

CREATE TABLE
    IF NOT EXISTS caixa (
        id SERIAL PRIMARY KEY,
        descricao varchar(255) NOT NULL UNIQUE,
        saldoinicial NUMERIC(10, 2) NOT NULL DEFAULT 0
    );

CREATE TABLE
    IF NOT EXISTS movimentacao (
        id SERIAL PRIMARY KEY,
        tipo varchar(255) NOT NULL,
        descricao varchar(255) NOT NULL,
        valor numeric(10, 2) NOT NULL DEFAULT 0,
        caixa_id INTEGER,
        CONSTRAINT fk_caixa FOREIGN key (caixa_id) REFERENCES caixa (id)
    );