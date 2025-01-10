DROP SCHEMA IF EXISTS "product" CASCADE;
CREATE SCHEMA "product";

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS "product".products CASCADE;

CREATE TABLE products (
     id UUID PRIMARY KEY,
     sku VARCHAR(255) NOT NULL,
     name VARCHAR(255) NOT NULL,
     description TEXT,
     image_url VARCHAR(500),
     price DOUBLE PRECISION NOT NULL
);

DROP TABLE IF EXISTS "product".users CASCADE;

CREATE TABLE users(
    id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    is_email_verified BOOLEAN NOT NULL,
    role VARCHAR(255) NOT NULL
);

INSERT INTO product.products (id, sku, name, description, image_url, price) VALUES ('111e4567-e89b-12d3-a456-426614174000', 'SKU-001', 'Kecap Indofood', 'this desc for kecap indofood', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRp5_8kTMBT3pzPZ8saWQFeXaW4wh2WzTosNw&s', 15000);
INSERT INTO product.products (id, sku, name, description, image_url, price) VALUES ('111e4567-e89b-12d3-a456-426614174001', 'SKU-002', 'Susu Indomilk', 'this desc for susu indomilk', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS_hex_Z47zL-Um5Y-OlVTi4S_VIoxMMEhwVw&s', 20000);

INSERT INTO product.users (id, email, is_email_verified, role) VALUES ('111e4567-e89b-12d3-a456-426614174000', 'superadmin@gmail.com', TRUE, 'SUPER_ADMIN');
INSERT INTO product.users (id, email, is_email_verified, role) VALUES ('222e4567-e89b-12d3-a456-426614174001', 'warehouseadmin1@gmail.com',TRUE, 'WAREHOUSE_ADMIN');
INSERT INTO product.users (id, email, is_email_verified, role) VALUES ('333e4567-e89b-12d3-a456-426614174002', 'warehouseadmin2@gmail.com',TRUE, 'WAREHOUSE_ADMIN');
INSERT INTO product.users (id, email, is_email_verified, role) VALUES ('444e4567-e89b-12d3-a456-426614174003', 'customer@gmail.com',TRUE, 'CUSTOMER');