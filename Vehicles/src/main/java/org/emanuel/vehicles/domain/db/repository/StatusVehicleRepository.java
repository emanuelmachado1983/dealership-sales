package org.emanuel.vehicles.domain.db.repository;

import org.emanuel.vehicles.domain.StatusVehicle;

import java.util.List;

public interface StatusVehicleRepository {
    List<StatusVehicle> findAll();
}
