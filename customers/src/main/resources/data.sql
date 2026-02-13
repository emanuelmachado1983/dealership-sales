CREATE TABLE customers (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           dni VARCHAR(255) NOT NULL,
                           email VARCHAR(255),
                           phone VARCHAR(255),
                           deleted_at TIMESTAMP
);

-- Insertar ejemplos de clientes
INSERT INTO customers (name, dni, email, phone) VALUES ('Juan Pérez', '12345678', 'juan.perez@example.com', '555-1234');
INSERT INTO customers (name, dni, email, phone) VALUES ('Ana Gómez', '87654321', 'ana.gomez@example.com', '555-5678');
INSERT INTO customers (name, dni, email, phone) VALUES ('Carlos López', '11223344', 'carlos.lopez@example.com', '555-9876');
