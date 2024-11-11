DROP SCHEMA IF EXISTS "order" CASCADE;

DROP SCHEMA IF EXISTS "warehouse" CASCADE;
CREATE SCHEMA "warehouse";

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS "warehouse".warehouses CASCADE;

CREATE TABLE warehouses (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    street VARCHAR(255),
    postal_code VARCHAR(20),
    city VARCHAR(100)
);
