package org.emanuel.vehicles.application;

import org.emanuel.vehicles.domain.StatusVehicle;

import java.util.List;

public interface IStatusVehicleService {
    List<StatusVehicle> getAllStates();
}
