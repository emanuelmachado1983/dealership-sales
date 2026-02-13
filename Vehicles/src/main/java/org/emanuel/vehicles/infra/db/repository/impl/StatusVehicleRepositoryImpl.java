package org.emanuel.vehicles.infra.db.repository.impl;

import lombok.RequiredArgsConstructor;
import org.emanuel.vehicles.domain.StatusVehicle;
import org.emanuel.vehicles.domain.db.repository.StatusVehicleRepository;
import org.emanuel.vehicles.infra.db.mapper.StatusVehicleEntityMapper;
import org.emanuel.vehicles.infra.db.repository.IStatusVehicleDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatusVehicleRepositoryImpl implements StatusVehicleRepository {

    private final IStatusVehicleDao statusVehicleDao;
    private final StatusVehicleEntityMapper statusVehicleEntityMapper;

    @Override
    public List<StatusVehicle> findAll() {
        return statusVehicleDao.findAll().stream().map(statusVehicleEntityMapper::toDomain).toList();
    }
}
