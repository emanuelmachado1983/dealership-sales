package org.emanuel.vehicles.infra.db.mapper;

import org.emanuel.vehicles.domain.TypeVehicle;
import org.emanuel.vehicles.infra.db.entity.TypeVehicleEntity;
import org.springframework.stereotype.Component;

@Component
public class TypeVehicleEntityMapper {

    public TypeVehicle toDomain(TypeVehicleEntity entity) {
        if (entity == null) return null;
        return TypeVehicle.builder()
                .id(entity.getId())
                .name(entity.getName())
                .warrantyYears(entity.getWarrantyYears())
                .build();
    }

    public TypeVehicleEntity toEntity(TypeVehicle domain) {
        if (domain == null) return null;
        return TypeVehicleEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .warrantyYears(domain.getWarrantyYears())
                .build();
    }
}

