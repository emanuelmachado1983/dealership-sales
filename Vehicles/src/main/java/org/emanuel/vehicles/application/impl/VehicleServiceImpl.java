package org.emanuel.vehicles.application.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.emanuel.vehicles.application.integration.offices.IOfficeService;
import org.emanuel.vehicles.application.IVehicleService;
import org.emanuel.vehicles.domain.Vehicle;
import org.emanuel.vehicles.domain.StatusVehicle;
import org.emanuel.vehicles.domain.db.repository.VehicleRepository;
import org.emanuel.vehicles.domain.exceptions.VehicleBadRequestException;
import org.emanuel.vehicles.domain.exceptions.VehicleNotExistException;
import org.emanuel.vehicles.infra.grpc.office.OfficeGrcpClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements IVehicleService {
    private final IOfficeService officeService;
    private final VehicleRepository vehicleRepository;
    private final OfficeGrcpClient officeGrcpClient;

    @Override
    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id).orElseThrow(() -> new VehicleNotExistException("Vehicle not found with id: " + id));
    }

    @Override
    public List<Vehicle> getAllVehicles(Long officeLocationId, Long statusId, Long modelId, Long type) {
        return vehicleRepository.findAllByFilters(officeLocationId, statusId, modelId, type);
    }

    @Override
    @Transactional
    public void addVehicle(Vehicle vehicle) {
        if (!officeGrcpClient.existsById(vehicle.getOfficeLocationId())) {
            throw new VehicleBadRequestException("The office with id " + vehicle.getOfficeLocationId() + " does not exist.");
        }
        vehicleRepository.save(vehicle);
    }

    @Override
    @Transactional
    public void updateVehicle(Long id, Vehicle vehicle) {
        if (!officeGrcpClient.existsById(vehicle.getOfficeLocationId())) {
            throw new VehicleBadRequestException("The office with id " + vehicle.getOfficeLocationId() + " does not exist.");
        }

        if (vehicleRepository.existsById(id)) {
            vehicleRepository.save(vehicle);
        } else {
            throw new VehicleNotExistException("Vehicle not found with id: " + id);
        }
    }

    @Override
    @Transactional
    public void changeState(Long id, Long idState) {
        var vehicle = vehicleRepository.findById(id).orElseThrow(() -> new VehicleNotExistException("Vehicle not found with id: " + id));
        vehicle.setStatus(new StatusVehicle(idState, null));
        vehicleRepository.save(vehicle);
    }

    @Override
    @Transactional
    public void updateStateVehicle(Long id, Long statusId) {
        if (vehicleRepository.existsById(id)) {
            vehicleRepository.updateVehicleStatus(id, statusId);
        } else {
            throw new VehicleNotExistException("Vehicle not found with id: " + id);
        }
    }


}
