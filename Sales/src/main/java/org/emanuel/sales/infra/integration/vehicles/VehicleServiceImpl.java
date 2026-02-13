package org.emanuel.sales.infra.integration.vehicles;

import feign.FeignException;
import org.emanuel.sales.application.integration.vehicle.IVehicleService;
import org.emanuel.sales.infra.integration.vehicles.mapper.VehicleMapper;
import org.emanuel.sales.domain.vehicle.Vehicle;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleServiceImpl implements IVehicleService {
    private final IVehicleFeign vehicleFeign;
    private final VehicleMapper vehicleMapper;

    public VehicleServiceImpl(IVehicleFeign vehicleFeign,
                              VehicleMapper vehicleMapper) {
        this.vehicleFeign = vehicleFeign;
        this.vehicleMapper = vehicleMapper;
    }

    @Override
    public Optional<Vehicle> getVehicleById(Long vehicleId) {
        try {
            return Optional.of(vehicleMapper.toModel(vehicleFeign.findById(vehicleId)));
        } catch (FeignException.NotFound e) {
            return Optional.empty();
        }
    }

    @Override
    public void putVehicle(Long vehicleId, Long idStatus) {
        vehicleFeign.putState(vehicleId, idStatus);
    }
}
