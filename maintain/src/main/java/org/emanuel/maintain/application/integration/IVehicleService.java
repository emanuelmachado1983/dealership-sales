package org.emanuel.maintain.application.integration;

import org.emanuel.maintain.infra.integration.vehicles.dto.VehicleFeignDto;

import java.util.Optional;

public interface IVehicleService {
    Optional<VehicleFeignDto> getVehicleById(Long vehicleId);

    void putVehicle(Long vehicleId, Long idStatus);
}
