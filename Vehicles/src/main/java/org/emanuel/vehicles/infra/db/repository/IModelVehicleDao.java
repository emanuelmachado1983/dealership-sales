package org.emanuel.vehicles.infra.db.repository;

import org.emanuel.vehicles.infra.db.entity.ModelVehicleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IModelVehicleDao extends CrudRepository<ModelVehicleEntity, Long> {
    @Override
    List<ModelVehicleEntity> findAll();
}
