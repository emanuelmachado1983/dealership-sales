package org.emanuel.vehicles.infra.rest.mapper;

import org.emanuel.vehicles.infra.rest.dto.ModelVehicleGetDto;
import org.emanuel.vehicles.infra.rest.dto.ModelVehicleModifyDto;
import org.emanuel.vehicles.domain.ModelVehicle;
import org.springframework.stereotype.Component;

@Component
public class ModelVehicleMapper {
    public ModelVehicleGetDto toGetDto(ModelVehicle modelVehicle) {
        return new ModelVehicleGetDto(
                modelVehicle.getId(),
                modelVehicle.getBrand(),
                modelVehicle.getModel(),
                modelVehicle.getYear(),
                modelVehicle.getPrice()
        );
    }

    public ModelVehicle toModel(Long id, ModelVehicleModifyDto modelVehicleModifyDto) {
        return new ModelVehicle(
                id,
                modelVehicleModifyDto.getBrand(),
                modelVehicleModifyDto.getModel(),
                modelVehicleModifyDto.getYear(),
                modelVehicleModifyDto.getPrice()
        );
    }
}
