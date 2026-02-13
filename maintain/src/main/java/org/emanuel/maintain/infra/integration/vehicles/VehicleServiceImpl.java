package org.emanuel.maintain.infra.integration.vehicles;

import feign.FeignException;
import org.emanuel.maintain.application.integration.IVehicleService;
import org.emanuel.maintain.infra.integration.vehicles.dto.VehicleFeignDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleServiceImpl implements IVehicleService {
    private final IVehicleFeign vehicleFeign;

    public VehicleServiceImpl(IVehicleFeign vehicleFeign) {
        this.vehicleFeign = vehicleFeign;
    }

    @Override
    public Optional<VehicleFeignDto> getVehicleById(Long vehicleId) {
    try {
            return Optional.of(vehicleFeign.findById(vehicleId));
        } catch (FeignException.NotFound e) {
            return Optional.empty();
        }
    }

    @Override
    public void putVehicle(Long vehicleId, Long idStatus) {
        vehicleFeign.putState(vehicleId, idStatus);
    }
}
