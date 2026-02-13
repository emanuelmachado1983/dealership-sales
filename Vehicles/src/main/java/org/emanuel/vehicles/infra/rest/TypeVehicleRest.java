package org.emanuel.vehicles.infra.rest;

import lombok.RequiredArgsConstructor;
import org.emanuel.vehicles.application.ITypeVehicleService;
import org.emanuel.vehicles.infra.rest.dto.TypeVehicleGetDto;
import org.emanuel.vehicles.infra.rest.dto.TypeVehicleModifyDto;
import org.emanuel.vehicles.infra.rest.mapper.TypeVehicleMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/type-vehicles")
@RequiredArgsConstructor
public class TypeVehicleRest {
    private final ITypeVehicleService typeVehicleService;
    private final TypeVehicleMapper typeVehicleMapper;

    @GetMapping("/{id}")
    public ResponseEntity<TypeVehicleGetDto> getTypeVehicleById(@PathVariable Long id) {
        return ResponseEntity.ok(typeVehicleMapper.toGetDto(typeVehicleService.getTypeVehicleById(id)));
    }

    @GetMapping("")
    public ResponseEntity<List<TypeVehicleGetDto>> getAllTypeVehicles() {
        var typeVehicles = typeVehicleService.getAllTypes().stream().map(typeVehicleMapper::toGetDto).toList();
        if (typeVehicles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(typeVehicles);
    }

    @PostMapping("")
    public ResponseEntity<Void> addTypeVehicle(@RequestBody TypeVehicleModifyDto typeVehicleModifyDto) {
        typeVehicleService.addTypeVehicle(typeVehicleMapper.toModel(null, typeVehicleModifyDto));
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTypeVehicle(@PathVariable Long id, @RequestBody TypeVehicleModifyDto typeVehicleModifyDto) {
        typeVehicleService.updateTypeVehicle(id, typeVehicleMapper.toModel(id, typeVehicleModifyDto));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTypeVehicle(@PathVariable Long id) {
        typeVehicleService.deleteTypeVehicle(id);
        return ResponseEntity.noContent().build();
    }

}
