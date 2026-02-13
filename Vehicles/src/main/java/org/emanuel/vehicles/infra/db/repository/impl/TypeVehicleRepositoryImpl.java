package org.emanuel.vehicles.infra.db.repository.impl;

import lombok.RequiredArgsConstructor;
import org.emanuel.vehicles.domain.TypeVehicle;
import org.emanuel.vehicles.domain.db.repository.TypeVehicleRepository;
import org.emanuel.vehicles.infra.db.mapper.TypeVehicleEntityMapper;
import org.emanuel.vehicles.infra.db.repository.ITypeVehicleDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TypeVehicleRepositoryImpl implements TypeVehicleRepository {

    private final ITypeVehicleDao typeVehicleDao;
    private final TypeVehicleEntityMapper typeVehicleEntityMapper;

    @Override
    public Optional<TypeVehicle> findById(Long id) {
        return typeVehicleDao.findById(id).map(typeVehicleEntityMapper::toDomain);
    }

    @Override
    public List<TypeVehicle> findAll() {
        return typeVehicleDao.findAll().stream().map(typeVehicleEntityMapper::toDomain).toList();
    }

    @Override
    public TypeVehicle save(TypeVehicle modelVehicle) {
        return typeVehicleEntityMapper.toDomain(typeVehicleDao.save(typeVehicleEntityMapper.toEntity(modelVehicle)));
    }

    @Override
    public boolean existsById(Long id) {
        return typeVehicleDao.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        typeVehicleDao.deleteById(id);
    }
}
