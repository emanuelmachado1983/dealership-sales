package org.emanuel.vehicles.domain.db.repository;

import org.emanuel.vehicles.domain.TypeVehicle;

import java.util.List;
import java.util.Optional;

public interface TypeVehicleRepository {
    Optional<TypeVehicle> findById(Long id);

    List<TypeVehicle> findAll();

    TypeVehicle save(TypeVehicle modelVehicle);

    boolean existsById(Long id);

    void deleteById(Long id);
}
