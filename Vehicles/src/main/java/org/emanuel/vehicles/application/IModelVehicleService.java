package org.emanuel.vehicles.application;

import org.emanuel.vehicles.domain.ModelVehicle;

import java.util.List;

public interface IModelVehicleService {

    ModelVehicle getModelVehicleById(Long id);

    List<ModelVehicle> getAllModelVehicles();

    void addModelVehicle(ModelVehicle modelVehicle);

    void updateModelVehicle(Long id, ModelVehicle modelVehicle);

    void deleteModelVehicle(Long id);

}
