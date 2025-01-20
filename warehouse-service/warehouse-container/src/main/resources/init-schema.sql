DROP SCHEMA IF EXISTS "warehouse" CASCADE;
CREATE SCHEMA "warehouse";

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS "warehouse".warehouses CASCADE;

CREATE TABLE warehouses (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    street VARCHAR(255),
    postal_code VARCHAR(20),
    city VARCHAR(100),
    latitude VARCHAR(50) NOT NULL,
    longitude VARCHAR(50) NOT NULL
);

DROP TABLE IF EXISTS "warehouse".users CASCADE;

CREATE TABLE users (
    id UUID PRIMARY KEY,
    warehouse_id UUID,
    is_email_verified BOOLEAN DEFAULT FALSE,
    role VARCHAR(25),
    CONSTRAINT fk_warehouse_id FOREIGN KEY (warehouse_id) REFERENCES warehouses (id)
);

DROP TABLE IF EXISTS "warehouse".products CASCADE;

CREATE TABLE products (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    image_url VARCHAR(500),
    price DOUBLE PRECISION NOT NULL
);

DROP TABLE IF EXISTS "warehouse".stocks CASCADE;

CREATE TABLE stocks (
    id UUID PRIMARY KEY,
    warehouse_id UUID,
    product_id UUID,
    quantity INTEGER NOT NULL,
    CONSTRAINT fk_warehouse_id FOREIGN KEY (warehouse_id) REFERENCES warehouses (id),
    CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES products (id)
);

DROP TYPE IF EXISTS transfer_status;
CREATE TYPE transfer_status AS ENUM ('REQUESTED', 'REJECTED', 'TRANSFERRED');

CREATE TABLE stock_transfers (
    id UUID PRIMARY KEY,
    source_warehouse_id UUID NOT NULL,
    destination_warehouse_id UUID NOT NULL,
    product_id UUID NOT NULL,
    quantity INT NOT NULL,
    reason TEXT NOT NULL,
    status transfer_status,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_source_warehouse_id FOREIGN KEY (source_warehouse_id) REFERENCES warehouses (id),
    CONSTRAINT fk_destination_warehouse_id FOREIGN KEY (destination_warehouse_id) REFERENCES warehouses (id),
    CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES products (id)
);

DROP TYPE IF EXISTS change_type;
CREATE TYPE change_type AS ENUM ('ADDITION', 'REDUCTION');

CREATE TABLE stock_journals (
    id UUID PRIMARY KEY,
    warehouse_id UUID NOT NULL,
    product_id UUID NOT NULL,
    change_type change_type NOT NULL,
    quantity INT NOT NULL,
    reason TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_warehouse_id FOREIGN KEY (warehouse_id) REFERENCES warehouses (id),
    CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES products (id)
);

INSERT INTO "warehouse".warehouses (id, name, street, postal_code, city, latitude, longitude)
    VALUES ('8b622abe-b4c6-4540-84df-7dc0e9752529', 'Indomarco 1', 'West Ancol Street', '14430', 'North Jakarta', '-6.1219211', '106.8147549');
INSERT INTO "warehouse".warehouses (id, name, street, postal_code, city, latitude, longitude)
    VALUES ('a04adf69-11a5-440f-a6b9-56d9a718e9a7', 'Indomarco 2', 'Ancol Street', '14430', 'North Jakarta', '-6.1213429', '106.8141887');

INSERT INTO "warehouse".users (id, warehouse_id, is_email_verified, role) VALUES ('111e4567-e89b-12d3-a456-426614174000', NULL, TRUE, 'SUPER_ADMIN');
INSERT INTO "warehouse".users (id, warehouse_id, is_email_verified, role) VALUES ('222e4567-e89b-12d3-a456-426614174001', '8b622abe-b4c6-4540-84df-7dc0e9752529', TRUE, 'WAREHOUSE_ADMIN');
INSERT INTO "warehouse".users (id, warehouse_id, is_email_verified, role) VALUES ('333e4567-e89b-12d3-a456-426614174002', 'a04adf69-11a5-440f-a6b9-56d9a718e9a7', TRUE, 'WAREHOUSE_ADMIN');

INSERT INTO "warehouse".products (id, name, image_url, price) VALUES ('111e4567-e89b-12d3-a456-426614174000', 'Kecap Indofood', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRp5_8kTMBT3pzPZ8saWQFeXaW4wh2WzTosNw&s', 15000);
INSERT INTO "warehouse".products (id, name, image_url, price) VALUES ('111e4567-e89b-12d3-a456-426614174001', 'Susu Indomilk', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS_hex_Z47zL-Um5Y-OlVTi4S_VIoxMMEhwVw&s', 20000);

INSERT INTO "warehouse".stocks (id, warehouse_id, product_id, quantity) VALUES ('111e4567-e89b-12d3-a456-426614174002',  '8b622abe-b4c6-4540-84df-7dc0e9752529', '111e4567-e89b-12d3-a456-426614174000', 100);
INSERT INTO "warehouse".stocks (id, warehouse_id, product_id, quantity) VALUES ('111e4567-e89b-12d3-a456-426614174004',  'a04adf69-11a5-440f-a6b9-56d9a718e9a7', '111e4567-e89b-12d3-a456-426614174001', 100);
INSERT INTO "warehouse".stocks (id, warehouse_id, product_id, quantity) VALUES ('111e4567-e89b-12d3-a456-426614174003',  '8b622abe-b4c6-4540-84df-7dc0e9752529', '111e4567-e89b-12d3-a456-426614174001', 100);