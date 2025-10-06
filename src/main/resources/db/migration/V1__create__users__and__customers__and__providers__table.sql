CREATE TABLE customers
(
    id         UUID NOT NULL,
    email      VARCHAR(255),
    password   VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT pk_customers PRIMARY KEY (id)
);

CREATE TABLE providers
(
    id         UUID NOT NULL,
    email      VARCHAR(255),
    password   VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT pk_providers PRIMARY KEY (id)
);

CREATE TABLE users
(
    id         UUID NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    email      VARCHAR(255),
    password   VARCHAR(255),
    CONSTRAINT pk_users PRIMARY KEY (id)
);