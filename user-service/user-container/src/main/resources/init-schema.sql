DROP SCHEMA IF EXISTS "user" CASCADE;
CREATE SCHEMA "user";

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS "user".users CASCADE;

CREATE TABLE users (
   id UUID PRIMARY KEY,
   warehouse_id UUID,
   username VARCHAR(50) UNIQUE NOT NULL,
   email VARCHAR(50) UNIQUE NOT NULL,
   password VARCHAR(250),
   is_email_verified VARCHAR(10),
   role VARCHAR(25)
);

DROP TABLE IF EXISTS "user".warehouses CASCADE;

CREATE TABLE warehouses (
    warehouse_id UUID PRIMARY KEY
);
