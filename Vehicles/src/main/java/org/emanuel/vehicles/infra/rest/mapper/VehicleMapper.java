package org.emanuel.vehicles.infra.rest.mapper;

import org.emanuel.vehicles.infra.rest.dto.VehicleGetDto;
import org.emanuel.vehicles.infra.rest.dto.VehicleModifyDto;
import org.emanuel.vehicles.domain.ModelVehicle;
import org.emanuel.vehicles.domain.StatusVehicle;
import org.emanuel.vehicles.domain.TypeVehicle;
import org.emanuel.vehicles.domain.Vehicle;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {
    private final ModelVehicleMapper modelVehicleMapper;
    private final StatusVehicleMapper statusVehicleMapper;
    private final TypeVehicleMapper typeVehicleMapper;

    public VehicleMapper(ModelVehicleMapper modelVehicleMapper, StatusVehicleMapper statusVehicleMapper, TypeVehicleMapper typeVehicleMapper) {
        this.modelVehicleMapper = modelVehicleMapper;
        this.statusVehicleMapper = statusVehicleMapper;
        this.typeVehicleMapper = typeVehicleMapper;
    }

    public VehicleGetDto toGetDto(Vehicle vehicle) {
        return new VehicleGetDto(
                vehicle.getId(),
                modelVehicleMapper.toGetDto(vehicle.getModel()),
                vehicle.getDescription(),
                statusVehicleMapper.toGetDto(vehicle.getStatus()),
                typeVehicleMapper.toGetDto(vehicle.getType()),
                vehicle.getPatent(),
                vehicle.getOfficeLocationId()
        );
    }

    public Vehicle modifyToModel(Long id, VehicleModifyDto vehicleModifyDto) {
        ModelVehicle model = new ModelVehicle();
        model.setId(vehicleModifyDto.getModelId());
        StatusVehicle status = new StatusVehicle();
        status.setId(vehicleModifyDto.getStatusId());
        TypeVehicle type = new TypeVehicle();
        type.setId(vehicleModifyDto.getTypeId());

        return new Vehicle(
                id,
                model,
                vehicleModifyDto.getDescription(),
                status,
                type,
                vehicleModifyDto.getPatent(),
                vehicleModifyDto.getOfficeLocationId()
        );
    }

}
