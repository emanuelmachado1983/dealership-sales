package org.emanuel.vehicles.infra.rest;

import lombok.RequiredArgsConstructor;
import org.emanuel.vehicles.application.IStatusVehicleService;
import org.emanuel.vehicles.infra.rest.dto.StatusVehicleGetDto;
import org.emanuel.vehicles.infra.rest.mapper.StatusVehicleMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/status-vehicles")
public class StatusVehicleRest {
    private final IStatusVehicleService statusVehicleService;
    private final StatusVehicleMapper statusVehicleMapper;

    @GetMapping("")
    public ResponseEntity<List<StatusVehicleGetDto>> getAllStates() {
        var states = statusVehicleService.getAllStates();
        if (states.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(states.stream().map(statusVehicleMapper::toGetDto).toList());
    }
}
