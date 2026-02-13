CREATE TABLE sale_states (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             name VARCHAR(255) NOT NULL
);

INSERT INTO sale_states (id, name) VALUES (1, 'Pendiente');
INSERT INTO sale_states (id, name) VALUES (2, 'Completada');
INSERT INTO sale_states (id, name) VALUES (3, 'Cancelada');

CREATE TABLE employees (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           dni VARCHAR(255) NOT NULL,
                           email VARCHAR(255),
                           phone VARCHAR(255),
                           deleted_at TIMESTAMP
);

-- Insertar ejemplos de empleados
INSERT INTO employees (name, dni, email, phone) VALUES ('Pedro Martínez', '34567890', 'pedro.martinez@example.com', '555-4321');
INSERT INTO employees (name, dni, email, phone) VALUES ('Laura Fernández', '98765432', 'laura.fernandez@example.com', '555-8765');
INSERT INTO employees (name, dni, email, phone) VALUES ('Miguel Torres', '22334455', 'miguel.torres@example.com', '555-6543');

CREATE TABLE sales (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       employee_id BIGINT NOT NULL,
                       customer_id BIGINT NOT NULL,
                       vehicle_id BIGINT NOT NULL,
                       ammount DOUBLE NOT NULL,
                       date TIMESTAMP NOT NULL,
                       warranty_years INT NOT NULL,
                       date_created TIMESTAMP NOT NULL,
                       date_modified TIMESTAMP,
                       status_id BIGINT NOT NULL,
                       delivery_days INT NOT NULL,
                       office_seller INT NOT NULL CHECK (office_seller <> 1),
                       FOREIGN KEY (employee_id) REFERENCES employees(id),
                       FOREIGN KEY (status_id) REFERENCES sale_states(id)
);

-- Insertar ejemplos de ventas
INSERT INTO sales (employee_id, customer_id, vehicle_id, ammount, date, warranty_years, date_created, status_id, delivery_days, office_seller)
VALUES (1, 1, 1, 25000.00, '2023-10-01 10:00:00', 2, '2023-10-01 10:00:00', 2, 8, 2);

INSERT INTO sales (employee_id, customer_id, vehicle_id, ammount, date, warranty_years, date_created, status_id, delivery_days, office_seller)
VALUES (2, 2, 2, 30000.00, '2023-10-02 11:00:00', 3, '2023-10-02 11:00:00', 1, 10, 3);

INSERT INTO sales (employee_id, customer_id, vehicle_id, ammount, date, warranty_years, date_created, status_id, delivery_days, office_seller)
VALUES (3, 3, 3, 15000.00, '2023-10-03 12:00:00', 1, '2023-10-03 12:00:00', 3, 5, 4);