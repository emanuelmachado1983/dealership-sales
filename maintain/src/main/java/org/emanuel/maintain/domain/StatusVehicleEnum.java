package org.emanuel.maintain.domain;

import lombok.Getter;

@Getter
public enum StatusVehicleEnum {
    AVAILABLE(1L, "Disponible"),
    RESERVED(2L, "Reservado"),
    SOLD(3L, "Vendido"),
    DELIVERED(4L, "Entregado"),
    IN_REPAIR(5L, "En reparación");

    private final Long id;
    private final String name;

    StatusVehicleEnum(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
