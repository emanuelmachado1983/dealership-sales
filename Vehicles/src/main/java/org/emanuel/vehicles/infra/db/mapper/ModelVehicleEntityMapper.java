package org.emanuel.vehicles.infra.db.mapper;

import org.emanuel.vehicles.domain.ModelVehicle;
import org.emanuel.vehicles.infra.db.entity.ModelVehicleEntity;
import org.springframework.stereotype.Component;

@Component
public class ModelVehicleEntityMapper {

    public ModelVehicle toDomain(ModelVehicleEntity entity) {
        if (entity == null) return null;
        return ModelVehicle.builder()
                .id(entity.getId())
                .brand(entity.getBrand())
                .model(entity.getModel())
                .year(entity.getYear())
                .price(entity.getPrice())
                .build();
    }

    public ModelVehicleEntity toEntity(ModelVehicle domain) {
        if (domain == null) return null;
        return ModelVehicleEntity.builder()
                .id(domain.getId())
                .brand(domain.getBrand())
                .model(domain.getModel())
                .year(domain.getYear())
                .price(domain.getPrice())
                .build();
    }
}

