package org.emanuel.vehicles.infra.db.repository;

import org.emanuel.vehicles.infra.db.entity.StatusVehicleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IStatusVehicleDao extends CrudRepository<StatusVehicleEntity, Long> {
    @Override
    List<StatusVehicleEntity> findAll();
}
