package org.emanuel.sales.infra.integration.vehicles.dto;

import org.springframework.stereotype.Component;

@Component
public class StatusVehicleFeignDto {
    private Long id;
    private String name;

    public StatusVehicleFeignDto() {
    }

    public StatusVehicleFeignDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
