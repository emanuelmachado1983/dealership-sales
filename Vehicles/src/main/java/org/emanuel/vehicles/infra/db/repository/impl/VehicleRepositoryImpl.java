package org.emanuel.vehicles.infra.db.repository.impl;

import lombok.RequiredArgsConstructor;
import org.emanuel.vehicles.domain.Vehicle;
import org.emanuel.vehicles.domain.db.repository.VehicleRepository;
import org.emanuel.vehicles.infra.db.mapper.VehicleEntityMapper;
import org.emanuel.vehicles.infra.db.repository.IVehicleDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VehicleRepositoryImpl implements VehicleRepository {

    private final IVehicleDao vehicleDao;
    private final VehicleEntityMapper vehicleEntityMapper;

    @Override
    public Optional<Vehicle> findById(Long id) {
        return vehicleDao.findById(id).map(vehicleEntityMapper::toDomain);
    }

    @Override
    public List<Vehicle> findAllByFilters(Long officeLocationId, Long statusId, Long modelId, Long type) {
        return vehicleDao.findAll().stream().map(vehicleEntityMapper::toDomain).toList();
    }

    @Override
    public void save(Vehicle modelVehicle) {
        vehicleDao.save(vehicleEntityMapper.toEntity(modelVehicle));
    }

    @Override
    public void updateVehicleStatus(Long id, Long statusId) {
        vehicleDao.updateVehicleStatus(id, statusId);
    }

    @Override
    public boolean existsById(Long id) {
        return vehicleDao.existsById(id);
    }
}
