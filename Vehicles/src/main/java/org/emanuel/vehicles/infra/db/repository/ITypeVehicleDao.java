package org.emanuel.vehicles.infra.db.repository;

import org.emanuel.vehicles.infra.db.entity.TypeVehicleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ITypeVehicleDao extends CrudRepository<TypeVehicleEntity, Long> {
    @Override
    List<TypeVehicleEntity> findAll();
}
