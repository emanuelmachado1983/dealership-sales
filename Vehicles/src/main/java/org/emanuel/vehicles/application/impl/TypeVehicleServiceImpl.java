package org.emanuel.vehicles.application.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.emanuel.vehicles.application.ITypeVehicleService;
import org.emanuel.vehicles.domain.TypeVehicle;
import org.emanuel.vehicles.domain.db.repository.TypeVehicleRepository;
import org.emanuel.vehicles.domain.exceptions.TypeVehicleNotExistException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TypeVehicleServiceImpl implements ITypeVehicleService {

    private final TypeVehicleRepository typeVehicleRepository;

    @Override
    public TypeVehicle getTypeVehicleById(Long id) {
        return typeVehicleRepository.findById(id).orElseThrow(() -> new TypeVehicleNotExistException("Type vehicle not found with id: " + id));
    }

    @Override
    public List<TypeVehicle> getAllTypes() {
        return typeVehicleRepository.findAll();
    }

    @Override
    @Transactional
    public void addTypeVehicle(TypeVehicle typeVehicle) {
        typeVehicleRepository.save(typeVehicle);
    }

    @Override
    @Transactional
    public void updateTypeVehicle(Long id, TypeVehicle typeVehicle) {
        typeVehicle.setId(id);
        if (typeVehicleRepository.existsById(id)) {
            typeVehicleRepository.save(typeVehicle);
        } else {
            throw new TypeVehicleNotExistException("TypeVehicle not found with id: " + id);
        }
    }

    @Override
    @Transactional
    public void deleteTypeVehicle(Long id) {
        typeVehicleRepository.findById(id).orElseThrow(() -> new TypeVehicleNotExistException("TypeVehicle not found with id: " + id));
        typeVehicleRepository.deleteById(id);
    }


}
