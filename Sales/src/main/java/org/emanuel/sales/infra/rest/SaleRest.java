package org.emanuel.sales.infra.rest;

import lombok.RequiredArgsConstructor;
import org.emanuel.sales.application.ISaleService;
import org.emanuel.sales.infra.rest.dto.SaleGetDto;
import org.emanuel.sales.infra.rest.dto.SaleModifyDto;
import org.emanuel.sales.infra.rest.dto.SalePostDto;
import org.emanuel.sales.domain.exceptions.SaleBadRequestException;
import org.emanuel.sales.domain.exceptions.SaleNotExistException;
import org.emanuel.sales.infra.rest.mapper.SaleMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/sales")
@RequiredArgsConstructor
public class SaleRest {
    private final ISaleService saleService;
    private final SaleMapper saleMapper;

    @GetMapping("/{id}")
    public ResponseEntity<SaleGetDto> getSaleById(@PathVariable Long id) {
        return ResponseEntity.ok(saleMapper.toGetDto(saleService.getSaleById(id)));
    }

    @GetMapping("")
    public ResponseEntity<List<SaleGetDto>> getAllSales(
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) Long employeeId,
            @RequestParam(required = false) LocalDateTime dateFrom,
            @RequestParam(required = false) LocalDateTime dateTo,
            @RequestParam(required = false) Long vehicleId
    ) {
        var sales = saleService.getAllSales(
                customerId,
                employeeId,
                dateFrom,
                dateTo,
                vehicleId);
        if (sales.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sales.stream().map(saleMapper::toGetDto).toList());
    }

    @PostMapping("")
    public ResponseEntity<SaleGetDto> addSale(@RequestBody SalePostDto salePostDto) {

        return ResponseEntity.status(201).body( saleMapper.toGetDto(saleService.addSale(
                salePostDto.getEmployeeId(),
                salePostDto.getCustomerId(),
                salePostDto.getVehicleId(),
                salePostDto.getDate(),
                salePostDto.getOfficeSellerId())
        ));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SaleGetDto> updateSale(@PathVariable Long id, @RequestBody SaleModifyDto saleModifyDto) {
        saleService.patchSale(
                id,
                saleModifyDto.getEmployeeId(),
                saleModifyDto.getCustomerId(),
                saleModifyDto.getIdStatus()
        );
        return ResponseEntity.noContent().build();
    }

}
