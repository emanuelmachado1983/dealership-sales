
CREATE TABLE vehicle_models (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                brand VARCHAR(255) NOT NULL,
                                model VARCHAR(255) NOT NULL,
                                year_model BIGINT NOT NULL,
                                price DOUBLE NOT NULL
);

INSERT INTO vehicle_models (brand, model, year_model, price) VALUES ('Toyota', 'Corolla', 2020, 20000.50);
INSERT INTO vehicle_models (brand, model, year_model, price) VALUES ('Ford', 'Focus', 2019, 18000.75);
INSERT INTO vehicle_models (brand, model, year_model, price) VALUES ('Honda', 'Civic', 2021, 22000.00);
INSERT INTO vehicle_models (brand, model, year_model, price) VALUES ('Chevrolet', 'Cruze', 2018, 17000.00);
INSERT INTO vehicle_models (brand, model, year_model, price) VALUES ('Volkswagen', 'Golf', 2022, 25000.00);
INSERT INTO vehicle_models (brand, model, year_model, price) VALUES ('Nissan', 'Altima', 2021, 23000.50);
INSERT INTO vehicle_models (brand, model, year_model, price) VALUES ('BMW', 'Series 3', 2020, 35000.75);
INSERT INTO vehicle_models (brand, model, year_model, price) VALUES ('Audi', 'A4', 2019, 33000.00);

CREATE TABLE vehicle_states (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                name VARCHAR(255) NOT NULL
);

INSERT INTO vehicle_states (id, name) VALUES (1, 'Disponible');
INSERT INTO vehicle_states (id, name) VALUES (2, 'Reservado');
INSERT INTO vehicle_states (id, name) VALUES (3, 'Vendido');
INSERT INTO vehicle_states (id, name) VALUES (4, 'Entregado');
INSERT INTO vehicle_states (id, name) VALUES (5, 'En reparación');


CREATE TABLE vehicle_types (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               name VARCHAR(255) NOT NULL,
                               warranty_years INT NOT NULL
);

INSERT INTO vehicle_types (name, warranty_years) VALUES ('Sedán', 5);
INSERT INTO vehicle_types (name, warranty_years) VALUES ('SUV', 7);
INSERT INTO vehicle_types (name, warranty_years) VALUES ('Camioneta', 6);
INSERT INTO vehicle_types (name, warranty_years) VALUES ('Convertible', 4);
INSERT INTO vehicle_types (name, warranty_years) VALUES ('Hatchback', 3);

CREATE TABLE vehicles (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          model_id BIGINT NOT NULL,
                          description VARCHAR(255) NOT NULL,
                          status_id BIGINT NOT NULL,
                          type_id BIGINT NOT NULL,
                          patent VARCHAR(255) NOT NULL,
                          office_location_id BIGINT NOT NULL,
                          FOREIGN KEY (model_id) REFERENCES vehicle_models(id),
                          FOREIGN KEY (status_id) REFERENCES vehicle_states(id),
                          FOREIGN KEY (type_id) REFERENCES vehicle_types(id)
);

INSERT INTO vehicles (model_id, description, status_id, type_id, patent, office_location_id)
VALUES (1, 'Vehículo compacto y eficiente', 4, 1, 'ABC123', 1);

INSERT INTO vehicles (model_id, description, status_id, type_id, patent, office_location_id)
VALUES (2, 'Vehículo familiar con gran espacio', 1, 2, 'DEF456', 1);

INSERT INTO vehicles (model_id, description, status_id, type_id, patent, office_location_id)
VALUES (3, 'Vehículo deportivo de alta gama', 1, 4, 'GHI789', 1);

INSERT INTO vehicles (model_id, description, status_id, type_id, patent, office_location_id)
VALUES (4, 'Vehículo todoterreno robusto', 1, 2, 'JKL012', 2);

INSERT INTO vehicles (model_id, description, status_id, type_id, patent, office_location_id)
VALUES (5, 'Vehículo económico y confiable', 1, 1, 'MNO345', 2);