package org.emanuel.vehicles.infra.db.mapper;

import lombok.RequiredArgsConstructor;
import org.emanuel.vehicles.domain.Vehicle;
import org.emanuel.vehicles.infra.db.entity.VehicleEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VehicleEntityMapper {
    private final ModelVehicleEntityMapper modelVehicleEntityMapper;
    private final StatusVehicleEntityMapper statusVehicleEntityMapper;
    private final TypeVehicleEntityMapper typeVehicleEntityMapper;

    public Vehicle toDomain(VehicleEntity entity) {
        if (entity == null) return null;
        return Vehicle.builder()
                .id(entity.getId())
                .model(modelVehicleEntityMapper.toDomain(entity.getModel()))
                .description(entity.getDescription())
                .status(statusVehicleEntityMapper.toDomain(entity.getStatus()))
                .type(typeVehicleEntityMapper.toDomain(entity.getType()))
                .patent(entity.getPatent())
                .officeLocationId(entity.getOfficeLocationId())
                .build();
    }

    public VehicleEntity toEntity(Vehicle domain) {
        if (domain == null) return null;
        return VehicleEntity.builder()
                .id(domain.getId())
                .model(modelVehicleEntityMapper.toEntity(domain.getModel()))
                .description(domain.getDescription())
                .status(statusVehicleEntityMapper.toEntity(domain.getStatus()))
                .type(typeVehicleEntityMapper.toEntity(domain.getType()))
                .patent(domain.getPatent())
                .officeLocationId(domain.getOfficeLocationId())
                .build();
    }
}

