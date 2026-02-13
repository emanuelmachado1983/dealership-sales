package org.emanuel.sales.infra.db.mapper;

import lombok.RequiredArgsConstructor;
import org.emanuel.sales.domain.Sale;
import org.emanuel.sales.infra.db.dto.SaleEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaleEntityMapper {

    private final EmployeeEntityMapper employeeEntityMapper;
    private final SaleStatusEntityMapper saleStatusEntityMapper;

    public Sale toDomain(SaleEntity entity) {
        if (entity == null) return null;
        return Sale.builder()
                .id(entity.getId())
                .employee(employeeEntityMapper.toDomain(entity.getEmployeeEntity()))
                .customerId(entity.getCustomerId())
                .vehicleId(entity.getVehicleId())
                .ammount(entity.getAmmount())
                .date(entity.getDate())
                .warrantyYears(entity.getWarrantyYears())
                .dateCreated(entity.getDateCreated())
                .dateModified(entity.getDateModified())
                .saleStatus(saleStatusEntityMapper.toDomain(entity.getSaleStatusEntity()))
                .deliveryDays(entity.getDeliveryDays())
                .officeSeller(entity.getOfficeSeller())
                .build();
    }

    public SaleEntity toEntity(Sale sale) {
        if (sale == null) return null;
        return SaleEntity.builder()
                .id(sale.getId())
                .employeeEntity(employeeEntityMapper.toEntity(sale.getEmployee()))
                .customerId(sale.getCustomerId())
                .vehicleId(sale.getVehicleId())
                .ammount(sale.getAmmount())
                .date(sale.getDate())
                .warrantyYears(sale.getWarrantyYears())
                .dateCreated(sale.getDateCreated())
                .dateModified(sale.getDateModified())
                .saleStatusEntity(saleStatusEntityMapper.toEntity(sale.getSaleStatus()))
                .deliveryDays(sale.getDeliveryDays())
                .officeSeller(sale.getOfficeSeller())
                .build();
    }
}
