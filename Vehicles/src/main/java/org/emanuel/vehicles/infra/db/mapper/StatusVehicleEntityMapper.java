package org.emanuel.vehicles.infra.db.mapper;

import org.emanuel.vehicles.domain.StatusVehicle;
import org.emanuel.vehicles.infra.db.entity.StatusVehicleEntity;
import org.springframework.stereotype.Component;

@Component
public class StatusVehicleEntityMapper {

    public StatusVehicle toDomain(StatusVehicleEntity entity) {
        if (entity == null) return null;
        return StatusVehicle.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public StatusVehicleEntity toEntity(StatusVehicle domain) {
        if (domain == null) return null;
        return StatusVehicleEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .build();
    }
}

