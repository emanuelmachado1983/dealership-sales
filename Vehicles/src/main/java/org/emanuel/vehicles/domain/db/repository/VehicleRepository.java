package org.emanuel.vehicles.domain.db.repository;

import org.emanuel.vehicles.domain.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository {
    Optional<Vehicle> findById(Long id);

    List<Vehicle> findAllByFilters(Long officeLocationId, Long statusId, Long modelId, Long type);

    void save(Vehicle modelVehicle);

    void updateVehicleStatus(Long id, Long statusId);

    boolean existsById(Long id);

}
