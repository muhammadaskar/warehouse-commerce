DROP SCHEMA IF EXISTS "user" CASCADE;
CREATE SCHEMA "user";

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS "user".warehouses CASCADE;

CREATE TABLE warehouses (
    warehouse_id UUID PRIMARY KEY
);
