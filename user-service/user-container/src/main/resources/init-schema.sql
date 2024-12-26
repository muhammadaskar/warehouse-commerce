-- DROP SCHEMA IF EXISTS "user" CASCADE;
-- CREATE SCHEMA "user";
--
-- CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
--
-- DROP TABLE IF EXISTS "user".users CASCADE;
--
-- CREATE TABLE users (
--    id UUID PRIMARY KEY,
--    warehouse_id UUID,
--    username VARCHAR(50) UNIQUE NOT NULL,
--    email VARCHAR(50) UNIQUE NOT NULL,
--    password VARCHAR(250),
--    is_email_verified BOOLEAN DEFAULT FALSE,
--    role VARCHAR(25)
-- );
--
-- DROP TABLE IF EXISTS "user".warehouses CASCADE;
--
-- CREATE TABLE warehouses (
--     warehouse_id UUID PRIMARY KEY
-- );

DROP TABLE IF EXISTS "user".user_addresses CASCADE;

CREATE TABLE user_addresses (
    id UUID NOT NULL PRIMARY KEY,
    user_id UUID NOT NULL,
    street VARCHAR(255) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    city VARCHAR(100) NOT NULL,
    latitude VARCHAR(50) NOT NULL,
    longitude VARCHAR(50) NOT NULL,
    is_primary BOOLEAN DEFAULT FALSE
);

