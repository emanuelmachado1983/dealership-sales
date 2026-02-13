package org.emanuel.vehicles.infra.rest.mapper;

import org.emanuel.vehicles.infra.rest.dto.StatusVehicleGetDto;
import org.emanuel.vehicles.domain.StatusVehicle;
import org.springframework.stereotype.Component;

@Component
public class StatusVehicleMapper {
    public StatusVehicleGetDto toGetDto(StatusVehicle statusVehicle) {
        return new StatusVehicleGetDto(
                statusVehicle.getId(),
                statusVehicle.getName()
        );
    }
}
