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
   is_email_verified BOOLEAN DEFAULT FALSE,
   role VARCHAR(25)
);

DROP TABLE IF EXISTS "user".warehouses CASCADE;

CREATE TABLE warehouses (
    warehouse_id UUID PRIMARY KEY
);

DROP TABLE IF EXISTS "user".user_addresses CASCADE;

CREATE TABLE user_addresses (
    id UUID NOT NULL PRIMARY KEY,
    user_id UUID NOT NULL,
    street VARCHAR(255) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    city VARCHAR(100) NOT NULL,
    latitude VARCHAR(50) NOT NULL,
    longitude VARCHAR(50) NOT NULL,
    is_primary BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (id)
);

INSERT INTO "user".users (id, username, email, password, is_email_verified, role) VALUES ('111e4567-e89b-12d3-a456-426614174000', 'superadmin', 'superadmin@gmail.com', '$2a$10$B4iIWo/gfc.mkKwR3GbWne0pt4B07LHFER7hHysugWh4CNxXowbeS', TRUE, 'SUPER_ADMIN');
INSERT INTO "user".users (id, warehouse_id, username, email, password, is_email_verified, role) VALUES ('222e4567-e89b-12d3-a456-426614174001', '8b622abe-b4c6-4540-84df-7dc0e9752529', 'warehouseadmin1', 'warehouseadmin1@gmail.com', '$2a$10$B4iIWo/gfc.mkKwR3GbWne0pt4B07LHFER7hHysugWh4CNxXowbeS', TRUE, 'WAREHOUSE_ADMIN');
INSERT INTO "user".users (id, warehouse_id, username, email, password, is_email_verified, role) VALUES ('333e4567-e89b-12d3-a456-426614174002', 'a04adf69-11a5-440f-a6b9-56d9a718e9a7', 'warehouseadmin2', 'warehouseadmin2@gmail.com', '$2a$10$B4iIWo/gfc.mkKwR3GbWne0pt4B07LHFER7hHysugWh4CNxXowbeS', TRUE, 'WAREHOUSE_ADMIN');
INSERT INTO "user".users (id, username, email, password, is_email_verified, role) VALUES ('444e4567-e89b-12d3-a456-426614174003', 'customer', 'customer@gmail.com', '$2a$10$B4iIWo/gfc.mkKwR3GbWne0pt4B07LHFER7hHysugWh4CNxXowbeS', TRUE, 'CUSTOMER');

INSERT INTO "user".user_addresses (id, user_id, street, postal_code, city, latitude, longitude, is_primary) VALUES ('111e4567-e89b-12d3-a456-426614174001', '444e4567-e89b-12d3-a456-426614174003', 'Swadaya Street II', '11410', 'West Jakarta', '-6.1941932', '-106.7989543', TRUE);
INSERT INTO "user".user_addresses (id, user_id, street, postal_code, city, latitude, longitude, is_primary) VALUES ('111e4567-e89b-12d3-a456-426614174002', '444e4567-e89b-12d3-a456-426614174003', 'M.H Thamrin', '10350', 'Central Jakarta', '-6.1936508', '106.8221703', FALSE);
