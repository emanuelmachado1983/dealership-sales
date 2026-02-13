package org.emanuel.vehicles.infra.rest;

import lombok.RequiredArgsConstructor;
import org.emanuel.vehicles.application.IVehicleService;
import org.emanuel.vehicles.infra.rest.dto.VehicleGetDto;
import org.emanuel.vehicles.infra.rest.dto.VehicleModifyDto;
import org.emanuel.vehicles.infra.rest.dto.VehicleStateModifyDto;
import org.emanuel.vehicles.infra.rest.mapper.VehicleMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/vehicles")
public class VehicleRest {
    private final IVehicleService vehicleService;
    private final VehicleMapper vehicleMapper;

    @GetMapping("/{id}")
    public ResponseEntity<VehicleGetDto> getVehicleById(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleMapper.toGetDto(vehicleService.getVehicleById(id)));
    }

    @GetMapping("")
    public ResponseEntity<List<VehicleGetDto>> getAllVehicles(
            @RequestParam(required = false) Long officeLocationId,
            @RequestParam(required = false) Long stateId,
            @RequestParam(required = false) Long modelId,
            @RequestParam(required = false) Long typeId) {
        var vehicles = vehicleService.getAllVehicles(officeLocationId, stateId, modelId, typeId);
        if (vehicles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vehicles.stream().map(vehicleMapper::toGetDto).toList());
    }

    @PostMapping("")
    public ResponseEntity<Void> addVehicle(@RequestBody VehicleModifyDto vehicleModifyDto) {
        vehicleService.addVehicle(vehicleMapper.modifyToModel(null, vehicleModifyDto));
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateVehicle(@PathVariable Long id, @RequestBody VehicleModifyDto vehicleModifyDto) {
        vehicleService.updateVehicle(id, vehicleMapper.modifyToModel(id, vehicleModifyDto));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> patchVehicle(@PathVariable Long id, @RequestBody VehicleModifyDto vehicleModifyDto) {
        var before = vehicleService.getVehicleById(id);
        vehicleModifyDto.setDescription(vehicleModifyDto.getDescription() == null ? before.getDescription() : vehicleModifyDto.getDescription());
        vehicleModifyDto.setPatent(vehicleModifyDto.getPatent() == null ? before.getPatent() : vehicleModifyDto.getPatent());
        vehicleModifyDto.setModelId(vehicleModifyDto.getModelId() == null ? before.getModel().getId() : vehicleModifyDto.getModelId());
        vehicleModifyDto.setOfficeLocationId(vehicleModifyDto.getOfficeLocationId() == null ? before.getOfficeLocationId() : vehicleModifyDto.getOfficeLocationId());
        vehicleModifyDto.setTypeId(vehicleModifyDto.getTypeId() == null ? before.getType().getId() : vehicleModifyDto.getTypeId());
        vehicleModifyDto.setStatusId(vehicleModifyDto.getStatusId() == null ? before.getStatus().getId() : vehicleModifyDto.getStatusId());

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/state")
    public ResponseEntity<Void> updateVehicle(@PathVariable Long id, @RequestBody VehicleStateModifyDto vehicleStateModifyDto) {
        vehicleService.updateStateVehicle(id, vehicleStateModifyDto.getStatusId());
        return ResponseEntity.noContent().build();
    }
}
