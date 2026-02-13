package org.emanuel.vehicles.domain.db.repository;

import org.emanuel.vehicles.domain.ModelVehicle;

import java.util.List;
import java.util.Optional;

public interface ModelVehicleRepository {
    Optional<ModelVehicle> findById(Long id);
    List<ModelVehicle> findAll();
    ModelVehicle save(ModelVehicle modelVehicle);
    boolean existsById(Long id);
    void deleteById(Long id);
}
