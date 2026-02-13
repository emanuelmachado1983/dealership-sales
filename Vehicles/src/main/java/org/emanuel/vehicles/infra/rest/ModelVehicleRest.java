package org.emanuel.vehicles.infra.rest;

import lombok.RequiredArgsConstructor;
import org.emanuel.vehicles.application.IModelVehicleService;
import org.emanuel.vehicles.infra.rest.dto.ModelVehicleGetDto;
import org.emanuel.vehicles.infra.rest.dto.ModelVehicleModifyDto;
import org.emanuel.vehicles.infra.rest.mapper.ModelVehicleMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/model-vehicles")
@RequiredArgsConstructor
public class ModelVehicleRest {
    private final IModelVehicleService modelVehicleService;
    private final ModelVehicleMapper modelVehicleMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ModelVehicleGetDto> getModelVehicleById(@PathVariable Long id) {
        return ResponseEntity.ok(modelVehicleMapper.toGetDto(modelVehicleService.getModelVehicleById(id)));
    }

    @GetMapping("")
    public ResponseEntity<List<ModelVehicleGetDto>> getAllModelVehicles() {
        var modelVehicles = modelVehicleService.getAllModelVehicles();
        if (modelVehicles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(modelVehicles.stream().map(modelVehicleMapper::toGetDto).toList());
    }

    @PostMapping("")
    public ResponseEntity<ModelVehicleGetDto> addModelVehicle(@RequestBody ModelVehicleModifyDto modelVehicleModifyDto) {
        modelVehicleService.addModelVehicle(modelVehicleMapper.toModel(null, modelVehicleModifyDto));
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateModelVehicle(@PathVariable Long id, @RequestBody ModelVehicleModifyDto modelVehicleModifyDto) {
        modelVehicleService.updateModelVehicle(id, modelVehicleMapper.toModel(id, modelVehicleModifyDto));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModelVehicle(@PathVariable Long id) {
        modelVehicleService.deleteModelVehicle(id);
        return ResponseEntity.noContent().build();
    }

}
