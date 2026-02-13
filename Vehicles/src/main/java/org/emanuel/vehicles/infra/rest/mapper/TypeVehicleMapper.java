package org.emanuel.vehicles.infra.rest.mapper;

import org.emanuel.vehicles.infra.rest.dto.TypeVehicleGetDto;
import org.emanuel.vehicles.infra.rest.dto.TypeVehicleModifyDto;
import org.emanuel.vehicles.domain.TypeVehicle;
import org.springframework.stereotype.Component;

@Component
public class TypeVehicleMapper {
    public TypeVehicleGetDto toGetDto(TypeVehicle typeVehicle) {
        return new TypeVehicleGetDto(
                typeVehicle.getId(),
                typeVehicle.getName(),
                typeVehicle.getWarrantyYears()
                );
    }

    public TypeVehicle toModel(Long id, TypeVehicleModifyDto typeVehicleModifyDto) {
        var typeVehicle = new TypeVehicle();
        typeVehicle.setId(id);
        typeVehicle.setName(typeVehicleModifyDto.getName());
        typeVehicle.setWarrantyYears(typeVehicleModifyDto.getWarrantyYears());
        return typeVehicle;
    }
}
