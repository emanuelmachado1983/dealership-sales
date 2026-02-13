package org.emanuel.vehicles.application;

import org.emanuel.vehicles.domain.TypeVehicle;

import java.util.List;

public interface ITypeVehicleService {
    TypeVehicle getTypeVehicleById(Long id);

    List<TypeVehicle> getAllTypes();

    void addTypeVehicle(TypeVehicle typeVehicle);

    void updateTypeVehicle(Long id, TypeVehicle typeVehicle);

    void deleteTypeVehicle(Long id);
}
