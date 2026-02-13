package org.emanuel.vehicles.application;

import org.emanuel.vehicles.domain.Vehicle;

import java.util.List;

public interface IVehicleService {
    Vehicle getVehicleById(Long id);

    List<Vehicle> getAllVehicles(Long officeLocationId, Long statusId, Long modelId, Long type);

    void addVehicle(Vehicle vehicle);

    void updateVehicle(Long id, Vehicle vehicle);

    void changeState(Long id, Long idState);

    void updateStateVehicle(Long id, Long statusId);

}
