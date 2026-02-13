package org.emanuel.vehicles.application.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.emanuel.vehicles.application.IModelVehicleService;
import org.emanuel.vehicles.domain.ModelVehicle;
import org.emanuel.vehicles.domain.db.repository.ModelVehicleRepository;
import org.emanuel.vehicles.domain.exceptions.ModelVehicleNotExistException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModelVehicleServiceImpl implements IModelVehicleService {
    private final ModelVehicleRepository modelVehicleRepository;

    @Override
    public ModelVehicle getModelVehicleById(Long id) {
        return modelVehicleRepository.findById(id).orElseThrow(() -> new ModelVehicleNotExistException("ModelVehicle not found with id: " + id));
    }

    @Override
    public List<ModelVehicle> getAllModelVehicles() {
        return modelVehicleRepository.findAll();
    }

    @Override
    @Transactional
    public void addModelVehicle(ModelVehicle modelVehicle) {
        modelVehicleRepository.save(modelVehicle);
    }

    @Override
    public void updateModelVehicle(Long id, ModelVehicle modelVehicle) {
        modelVehicle.setId(id);
        if (modelVehicleRepository.existsById(id)) {
            modelVehicleRepository.save(modelVehicle);
        } else {
            throw new ModelVehicleNotExistException("Model vehicle not found with id: " + id);
        }
    }

    @Override
    public void deleteModelVehicle(Long id) {
        modelVehicleRepository.findById(id).orElseThrow(() -> new ModelVehicleNotExistException("Model vehicle not found with id: " + id));
        modelVehicleRepository.deleteById(id);
    }
}
