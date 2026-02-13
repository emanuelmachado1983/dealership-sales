package org.emanuel.sales.infra.integration.vehicles;

import org.emanuel.sales.infra.integration.vehicles.dto.VehicleFeignDto;
import org.emanuel.sales.infra.integration.vehicles.dto.VehicleStateModifyFeignDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "vehicles-service", url = "${vehicles.service.url}")
public interface IVehicleFeign {
    @GetMapping("/api/v1/vehicles/{idVehicle}")
    VehicleFeignDto findById(@PathVariable Long idVehicle);

    @PutMapping("/api/v1/vehicles/{idVehicle}")
    VehicleFeignDto put(@PathVariable Long idVehicle, @RequestBody Map<String, Object> body);

    @PutMapping("/api/v1/vehicles/{idVehicle}/state")
    VehicleStateModifyFeignDto putState(@PathVariable Long idVehicle, Long idStatus);

}