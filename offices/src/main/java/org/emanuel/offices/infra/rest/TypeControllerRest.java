package org.emanuel.offices.infra.rest;

import org.emanuel.offices.application.ITypeOfficeService;
import org.emanuel.offices.infra.rest.dto.TypeOfficeDto;
import org.emanuel.offices.infra.rest.mapper.TypeOfficeMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/types")
public class TypeControllerRest {
    private final ITypeOfficeService typeOfficeService;
    private final TypeOfficeMapper typeOfficeMapper;

    public TypeControllerRest(ITypeOfficeService typeOfficeService, TypeOfficeMapper typeOfficeMapper) {
        this.typeOfficeService = typeOfficeService;
        this.typeOfficeMapper = typeOfficeMapper;
    }

    @GetMapping
    public ResponseEntity<List<TypeOfficeDto>> getAllTypeOffices() {
        var typeOffices = typeOfficeService.getTypeOffices();
        if (typeOffices.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(typeOffices.stream().map(typeOfficeMapper::toDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeOfficeDto> getTypeOfficeById(@PathVariable Long id) {
        return ResponseEntity.ok(typeOfficeMapper.toDto(typeOfficeService.getTypeOfficeById(id)));
    }
}
