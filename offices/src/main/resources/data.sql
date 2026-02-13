

CREATE TABLE type_offices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE offices (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     id_country BIGINT NOT NULL,
     id_province BIGINT NOT NULL,
     id_locality BIGINT NOT NULL,
     address VARCHAR(255) NOT NULL,
     name VARCHAR(255) NOT NULL,
     opening_date TIMESTAMP NOT NULL,
     type_office_id BIGINT NOT NULL,
     deleted_at TIMESTAMP,
     CONSTRAINT fk_type_office FOREIGN KEY (type_office_id) REFERENCES type_offices(id)
);

CREATE TABLE delivery_schedules (
                                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                    office_from_id BIGINT NOT NULL,
                                    office_to_id BIGINT NOT NULL,
                                    days INT NOT NULL,
                                    CONSTRAINT fk_office_from FOREIGN KEY (office_from_id) REFERENCES offices(id),
                                    CONSTRAINT fk_office_to FOREIGN KEY (office_to_id) REFERENCES offices(id)
);

INSERT INTO type_offices (name) VALUES ('Central');
INSERT INTO type_offices (name) VALUES ('Concesionaria');

INSERT INTO offices (id_country, id_province, id_locality, address, name, opening_date, type_office_id)
VALUES (1, 1, 1, 'Rivadavia 6000', 'sucursal de venta Rivadavia', '2025-06-15T15:15', 1);

INSERT INTO offices (id_country, id_province, id_locality, address, name, opening_date, type_office_id)
VALUES (1, 1, 2, 'Yerbal 2500', 'sucursal de venta ejemplo1', '2025-06-15T16:15', 2);

INSERT INTO offices (id_country, id_province, id_locality, address, name, opening_date, type_office_id)
VALUES (1, 2, 1, 'Avellaneda 543', 'sucursal de venta en Avellaneda', '2025-06-15T16:15', 2);

INSERT INTO delivery_schedules (office_from_id, office_to_id, days) VALUES (1, 2, 5);
INSERT INTO delivery_schedules (office_from_id, office_to_id, days) VALUES (2, 2, 3);


CREATE TABLE countries (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           name VARCHAR(255) NOT NULL,
                           deleted_at DATETIME NULL
);

INSERT INTO countries (name, deleted_at) VALUES ('Argentina', NULL);

CREATE TABLE provinces (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           name VARCHAR(255) NOT NULL,
                           deleted_at DATETIME NULL,
                           country_id BIGINT NOT NULL,
                           CONSTRAINT fk_country
                               FOREIGN KEY (country_id)
                                   REFERENCES countries(id)
);

INSERT INTO provinces (name, deleted_at, country_id) VALUES ('Capital Federal', NULL, 1);
INSERT INTO provinces (name, deleted_at, country_id) VALUES ('Buenos Aires', NULL, 1);
INSERT INTO provinces (name, deleted_at, country_id) VALUES ('Neuquen', NULL, 1);

CREATE TABLE localities (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            name VARCHAR(255) NOT NULL,
                            deleted_at DATETIME NULL,
                            province_id BIGINT NOT NULL,
                            CONSTRAINT fk_province
                                FOREIGN KEY (province_id)
                                    REFERENCES provinces(id)
);

INSERT INTO localities (name, deleted_at, province_id) VALUES ('Flores', NULL, 1);
INSERT INTO localities (name, deleted_at, province_id) VALUES ('Caballito', NULL, 1);
INSERT INTO localities (name, deleted_at, province_id) VALUES ('Avellaneda', NULL, 2);
INSERT INTO localities (name, deleted_at, province_id) VALUES ('Lanus', NULL, 2);
INSERT INTO localities (name, deleted_at, province_id) VALUES ('Tigre', NULL, 2);
