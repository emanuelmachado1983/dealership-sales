package org.emanuel.sales.infra.integration.vehicles.mapper;

import org.emanuel.sales.domain.vehicle.ModelVehicle;
import org.emanuel.sales.domain.vehicle.StatusVehicle;
import org.emanuel.sales.domain.vehicle.TypeVehicle;
import org.emanuel.sales.domain.vehicle.Vehicle;
import org.emanuel.sales.infra.integration.vehicles.dto.VehicleFeignDto;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {

    public Vehicle toModel(VehicleFeignDto vehicleFeignDto) {

        var vehicle = new Vehicle();
        vehicle.setId(vehicleFeignDto.getId());
        vehicle.setModel(
                new ModelVehicle(
                        vehicleFeignDto.getModel().getId(),
                        vehicleFeignDto.getModel().getBrand(),
                        vehicleFeignDto.getModel().getModel(),
                        vehicleFeignDto.getModel().getYear(),
                        vehicleFeignDto.getModel().getPrice()
                )
        );
        vehicle.setDescription(vehicleFeignDto.getDescription());
        vehicle.setStatus(new StatusVehicle(
                        vehicleFeignDto.getStatus().getId(),
                        vehicleFeignDto.getStatus().getName()
                )
        );
        vehicle.setType(new TypeVehicle(
                        vehicleFeignDto.getType().getId(),
                        vehicleFeignDto.getType().getName(),
                        vehicleFeignDto.getType().getWarrantyYears()
                )
        );
        vehicle.setPatent(vehicleFeignDto.getPatent());
        vehicle.setOfficeLocationId(vehicleFeignDto.getOfficeLocationId());
        return vehicle;
    }
}
