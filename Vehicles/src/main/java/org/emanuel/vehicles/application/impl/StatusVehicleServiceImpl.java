package org.emanuel.vehicles.application.impl;

import lombok.RequiredArgsConstructor;
import org.emanuel.vehicles.application.IStatusVehicleService;
import org.emanuel.vehicles.domain.StatusVehicle;
import org.emanuel.vehicles.domain.db.repository.StatusVehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusVehicleServiceImpl implements IStatusVehicleService {
    private final StatusVehicleRepository statusVehicleRepository;


    @Override
    public List<StatusVehicle> getAllStates() {
        return statusVehicleRepository.findAll();
    }
}
