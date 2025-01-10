DROP SCHEMA IF EXISTS "payment" CASCADE;
CREATE SCHEMA "payment";

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS "payment".user CASCADE;
CREATE TABLE users(
    id UUID PRIMARY KEY,
    warehouse_id UUID,
    email VARCHAR(255) NOT NULL,
    is_email_verified BOOLEAN NOT NULL,
    role VARCHAR(255) NOT NULL
);

DROP TYPE IF EXISTS order_status;
CREATE TYPE order_status AS ENUM ('AWAITING_PAYMENT', 'PROCESSED', 'APPROVED', 'SHIPPED', 'CONFIRMED', 'CANCELLING', 'CANCELLED', 'PENDING');

DROP TABLE IF EXISTS "payment".orders CASCADE;
CREATE TABLE orders (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    warehouse_id UUID NOT NULL,
    status order_status NOT NULL,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (id)
);

DROP TABLE IF EXISTS "payment".payments CASCADE;
CREATE TABLE payments (
    id UUID PRIMARY KEY,
    order_id UUID NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    payment_proof TEXT,
    status VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_order_id FOREIGN KEY (order_id) REFERENCES orders (id)
);

-- Insert data
INSERT INTO "payment".users (id, email, is_email_verified, role) VALUES ('111e4567-e89b-12d3-a456-426614174000', 'superadmin@gmail.com', TRUE, 'SUPER_ADMIN');
INSERT INTO "payment".users (id, warehouse_id, email, is_email_verified, role) VALUES ('222e4567-e89b-12d3-a456-426614174001', '8b622abe-b4c6-4540-84df-7dc0e9752529', 'warehouseadmin1@gmail.com',TRUE, 'WAREHOUSE_ADMIN');
INSERT INTO "payment".users (id, warehouse_id, email, is_email_verified, role) VALUES ('333e4567-e89b-12d3-a456-426614174002', 'a04adf69-11a5-440f-a6b9-56d9a718e9a7', 'warehouseadmin2@gmail.com',TRUE, 'WAREHOUSE_ADMIN');
INSERT INTO "payment".users (id, email, is_email_verified, role) VALUES ('444e4567-e89b-12d3-a456-426614174003', 'customer@gmail.com',TRUE, 'CUSTOMER');
