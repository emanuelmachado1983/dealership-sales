package org.emanuel.sales.application.integration.vehicle;

import org.emanuel.sales.domain.vehicle.Vehicle;

import java.util.Optional;

public interface IVehicleService {
    Optional<Vehicle> getVehicleById(Long vehicleId);

    void putVehicle(Long vehicleId, Long idStatus);
}
