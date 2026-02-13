package org.emanuel.vehicles.infra.db.repository.impl;

import lombok.RequiredArgsConstructor;
import org.emanuel.vehicles.domain.ModelVehicle;
import org.emanuel.vehicles.domain.db.repository.ModelVehicleRepository;
import org.emanuel.vehicles.infra.db.mapper.ModelVehicleEntityMapper;
import org.emanuel.vehicles.infra.db.repository.IModelVehicleDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ModelVehicleRepositoryImpl implements ModelVehicleRepository {
    private final IModelVehicleDao modelVehicleDao;
    private final ModelVehicleEntityMapper modelVehicleEntityMapper;

    @Override
    public Optional<ModelVehicle> findById(Long id) {
        return modelVehicleDao.findById(id).map(modelVehicleEntityMapper::toDomain);
    }

    @Override
    public List<ModelVehicle> findAll() {
        return modelVehicleDao.findAll().stream().map(modelVehicleEntityMapper::toDomain).toList();
    }

    @Override
    public ModelVehicle save(ModelVehicle modelVehicle) {
        return modelVehicleEntityMapper.toDomain(modelVehicleDao.save(modelVehicleEntityMapper.toEntity(modelVehicle)));
    }

    @Override
    public boolean existsById(Long id) {
        return modelVehicleDao.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        modelVehicleDao.deleteById(id);
    }
}
