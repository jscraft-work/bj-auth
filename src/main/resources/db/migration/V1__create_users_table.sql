CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       display_name VARCHAR(50) NOT NULL,
                       created_at TIMESTAMP NOT NULL
);
