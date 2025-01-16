DROP SCHEMA IF EXISTS "order" CASCADE;
CREATE SCHEMA "order";

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS "order".users CASCADE;
CREATE TABLE users (
    id UUID PRIMARY KEY,
    warehouse_id UUID,
    email VARCHAR(255) NOT NULL,
    is_email_verified BOOLEAN NOT NULL,
    role VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS "order".warehouses CASCADE;
CREATE TABLE warehouses (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    street VARCHAR(255),
    postal_code VARCHAR(20),
    city VARCHAR(100),
    latitude VARCHAR(50) NOT NULL,
    longitude VARCHAR(50) NOT NULL
);

DROP TABLE IF EXISTS "order".products CASCADE;
CREATE TABLE products (
    id UUID PRIMARY KEY,
    sku VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    image_url VARCHAR(500),
    price DOUBLE PRECISION NOT NULL
);

DROP TABLE IF EXISTS "order".stocks CASCADE;
CREATE TABLE stocks (
    id UUID PRIMARY KEY,
    warehouse_id UUID,
    product_id UUID,
    quantity INTEGER NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT fk_warehouse_id FOREIGN KEY (warehouse_id) REFERENCES warehouses (id),
    CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES products (id)
);

DROP TYPE IF EXISTS order_status;
CREATE TYPE order_status AS ENUM ('AWAITING_PAYMENT', 'PROCESSED', 'APPROVED', 'SHIPPED', 'CONFIRMED', 'CANCELLING', 'CANCELLED', 'PENDING');

DROP TABLE IF EXISTS "order".orders CASCADE;
CREATE TABLE orders (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    warehouse_id UUID NOT NULL,
    cart_id UUID,
    shipping_method VARCHAR(255) NOT NULL,
    shipping_cost DOUBLE PRECISION NOT NULL,
    total_price DOUBLE PRECISION NOT NULL,
    status order_status NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (id)
);

DROP TABLE IF EXISTS "order".order_items CASCADE;
CREATE TABLE order_items (
     id VARCHAR(200) NOT NULL,
     order_id UUID NOT NULL,
     product_id UUID NOT NULL,
     quantity INTEGER NOT NULL,
     price DOUBLE PRECISION NOT NULL,
     sub_total DOUBLE PRECISION NOT NULL,
     CONSTRAINT order_items_pkey PRIMARY KEY (id, order_id),
     CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES products (id)
);

ALTER TABLE "order".order_items
    ADD CONSTRAINT "FK_ORDER_ID" FOREIGN KEY (order_id)
        REFERENCES "order".orders (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
    NOT VALID;

DROP TABLE IF EXISTS "order".order_address CASCADE;
CREATE TABLE order_address (
    id UUID NOT NULL ,
    order_id UUID UNIQUE NOT NULL,
    street VARCHAR(255),
    postal_code VARCHAR(20),
    city VARCHAR(100),
    latitude VARCHAR(50) NOT NULL,
    longitude VARCHAR(50) NOT NULL,
    CONSTRAINT order_address_pkey PRIMARY KEY (id, order_id)
);

ALTER TABLE "order".order_address
    ADD CONSTRAINT "FK_ORDER_ID" FOREIGN KEY (order_id)
        REFERENCES "order".orders (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
    NOT VALID;

-- Insert data
INSERT INTO "order".users (id, email, is_email_verified, role) VALUES ('111e4567-e89b-12d3-a456-426614174000', 'superadmin@gmail.com', TRUE, 'SUPER_ADMIN');
INSERT INTO "order".users (id, warehouse_id, email, is_email_verified, role) VALUES ('222e4567-e89b-12d3-a456-426614174001', '8b622abe-b4c6-4540-84df-7dc0e9752529', 'warehouseadmin1@gmail.com',TRUE, 'WAREHOUSE_ADMIN');
INSERT INTO "order".users (id, warehouse_id, email, is_email_verified, role) VALUES ('333e4567-e89b-12d3-a456-426614174002', 'a04adf69-11a5-440f-a6b9-56d9a718e9a7', 'warehouseadmin2@gmail.com',TRUE, 'WAREHOUSE_ADMIN');
INSERT INTO "order".users (id, email, is_email_verified, role) VALUES ('444e4567-e89b-12d3-a456-426614174003', 'customer@gmail.com',TRUE, 'CUSTOMER');

INSERT INTO "order".warehouses (id, name, street, postal_code, city, latitude, longitude)
VALUES ('8b622abe-b4c6-4540-84df-7dc0e9752529', 'Indomarco 1', 'West Ancol Street', '14430', 'North Jakarta', '-6.1219211', '106.8147549');

INSERT INTO "order".warehouses (id, name, street, postal_code, city, latitude, longitude)
VALUES ('a04adf69-11a5-440f-a6b9-56d9a718e9a7', 'Indomarco 2', 'Ancol Street', '14430', 'North Jakarta', '-6.1213429', '106.8141887');

INSERT INTO "order".products (id, sku, name, image_url, price) VALUES ('111e4567-e89b-12d3-a456-426614174000','SKU-001', 'Kecap Indofood', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRp5_8kTMBT3pzPZ8saWQFeXaW4wh2WzTosNw&s', 15000);
INSERT INTO "order".products (id, sku, name, image_url, price) VALUES ('111e4567-e89b-12d3-a456-426614174001','SKU-002', 'Susu Indomilk', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS_hex_Z47zL-Um5Y-OlVTi4S_VIoxMMEhwVw&s', 20000);

INSERT INTO "order".stocks (id, warehouse_id, product_id, quantity) VALUES ('111e4567-e89b-12d3-a456-426614174002',  '8b622abe-b4c6-4540-84df-7dc0e9752529', '111e4567-e89b-12d3-a456-426614174000', 100);
INSERT INTO "order".stocks (id, warehouse_id, product_id, quantity) VALUES ('111e4567-e89b-12d3-a456-426614174004',  'a04adf69-11a5-440f-a6b9-56d9a718e9a7', '111e4567-e89b-12d3-a456-426614174001', 100);
INSERT INTO "order".stocks (id, warehouse_id, product_id, quantity) VALUES ('111e4567-e89b-12d3-a456-426614174003',  '8b622abe-b4c6-4540-84df-7dc0e9752529', '111e4567-e89b-12d3-a456-426614174001', 100);


