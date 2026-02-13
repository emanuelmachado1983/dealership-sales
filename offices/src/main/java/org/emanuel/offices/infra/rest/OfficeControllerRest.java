package org.emanuel.offices.infra.rest;

import org.emanuel.offices.application.IOfficeService;
import org.emanuel.offices.infra.rest.dto.OfficeGetDto;
import org.emanuel.offices.infra.rest.dto.OfficeModifyDto;
import org.emanuel.offices.infra.rest.mapper.OfficeMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/offices")
public class OfficeControllerRest {
    private final IOfficeService officeService;
    private final OfficeMapper officeMapper;

    public OfficeControllerRest(IOfficeService officeService, OfficeMapper officeMapper) {
        this.officeService = officeService;
        this.officeMapper = officeMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfficeGetDto> getOfficeById(@PathVariable Long id) {
        return ResponseEntity.ok(officeMapper.toDto(officeService.getOfficeById(id)));
    }

    @GetMapping
    public ResponseEntity<List<OfficeGetDto>> getAllOffices() {
        var offices = officeService.getAllOffices();
        if (offices.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(offices.stream().map(officeMapper::toDto).toList());
    }

    @PostMapping
    public ResponseEntity<OfficeGetDto> addOffice(@RequestBody OfficeModifyDto officeModifyDto) {
        var newOffice = officeService.addOffice(officeMapper.toModel(null, officeModifyDto));
        return ResponseEntity.status(201).body(officeMapper.toDto(newOffice));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfficeGetDto> updateOffice(@PathVariable Long id, @RequestBody OfficeModifyDto officeModifyDto) {
        var updatedOffice = officeService.updateOffice(id, officeMapper.toModel(id, officeModifyDto));
        return ResponseEntity.ok(officeMapper.toDto(updatedOffice));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffice(@PathVariable Long id) {
        officeService.deleteOffice(id);
        return ResponseEntity.noContent().build();
    }

}
