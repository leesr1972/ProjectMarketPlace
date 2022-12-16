-- liquibase formatted sql

-- changeset safarov:1

CREATE TABLE IF NOT EXISTS users
(
    id   SERIAL PRIMARY KEY,
    first_name VARCHAR (30) NOT NULL,
    last_name VARCHAR (30) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR (30) UNIQUE NOT NULL,
    password VARCHAR (150) NOT NULL,
    role VARCHAR (10) NOT NULL
    );

CREATE TABLE IF NOT EXISTS ads
(
    id  SERIAL PRIMARY KEY,
    author_id BIGINT REFERENCES users (id),
    image VARCHAR (250),
    title VARCHAR (100) NOT NULL,
    description TEXT,
    price INTEGER NOT NULL
    );

CREATE TABLE IF NOT EXISTS comment
(
    id SERIAL PRIMARY KEY,
    author_id BIGINT REFERENCES users (user_id),
    ads_id BIGINT REFERENCES ads (ads_id),
    created_at TIMESTAMP NOT NULL,
    text TEXT NOT NULL
    );