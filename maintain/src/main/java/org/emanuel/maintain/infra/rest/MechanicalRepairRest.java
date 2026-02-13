package org.emanuel.maintain.infra.rest;

import lombok.RequiredArgsConstructor;
import org.emanuel.maintain.application.IMechanicalRepairService;
import org.emanuel.maintain.infra.rest.dto.MechanicalRepairGetDto;
import org.emanuel.maintain.infra.rest.dto.MechanicalRepairModifyDto;
import org.emanuel.maintain.infra.rest.dto.MechanicalRepairPostDto;
import org.emanuel.maintain.infra.rest.mapper.MechanicalRepairMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/mechanical-repairs")
public class MechanicalRepairRest {
    private final IMechanicalRepairService mechanicalRepairService;
    private final MechanicalRepairMapper mechanicalRepairMapper;

    @GetMapping("/{id}")
    public ResponseEntity<MechanicalRepairGetDto> getMechanicalRepairById(@PathVariable Long id) {
        return ResponseEntity.ok(mechanicalRepairMapper.toGetDto(mechanicalRepairService.getMechanicalRepairById(id)));
    }

    @GetMapping("")
    public ResponseEntity<List<MechanicalRepairGetDto>> getAllCountries(@RequestParam(required = false) Long customerId) {
        var mechanicalRepairs = mechanicalRepairService.getAllMechanicalRepairs(customerId);
        if (mechanicalRepairs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mechanicalRepairs.stream().map(mechanicalRepairMapper::toGetDto).toList());
    }

    @PostMapping("")
    public ResponseEntity<Void> addMechanicalRepair(@RequestBody MechanicalRepairPostDto mechanicalRepairPostDto) {
        mechanicalRepairService.addMechanicalRepair(
                mechanicalRepairPostDto.getEnterDate(),
                mechanicalRepairPostDto.getDeliveryDateEstimated(),
                mechanicalRepairPostDto.getVehicleId(),
                mechanicalRepairPostDto.getKmUnit()
        );
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMechanicalRepair(@PathVariable Long id, @RequestBody MechanicalRepairModifyDto mechanicalRepairModifyDto) {
        mechanicalRepairService.updateMechanicalRepair(
                id,
                mechanicalRepairModifyDto.getEnterDate(),
                mechanicalRepairModifyDto.getDeliveryDateEstimated(),
                mechanicalRepairModifyDto.getDeliveryDate(),
                mechanicalRepairModifyDto.getKmUnit()
        );
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMechanicalRepair(@PathVariable Long id) {
        mechanicalRepairService.deleteMechanicalRepair(id);
        return ResponseEntity.noContent().build();
    }
}
