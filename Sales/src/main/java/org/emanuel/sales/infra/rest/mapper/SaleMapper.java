package org.emanuel.sales.infra.rest.mapper;

import org.emanuel.sales.infra.rest.dto.SaleGetDto;
import org.emanuel.sales.domain.Sale;
import org.springframework.stereotype.Component;

@Component
public class SaleMapper {

    private final EmployeeMapper employeeMapper;
    private final SaleStatusMapper saleStatusMapper;

    public SaleMapper(EmployeeMapper employeeMapper, SaleStatusMapper saleStatusMapper) {
        this.employeeMapper = employeeMapper;
        this.saleStatusMapper = saleStatusMapper;
    }

    public SaleGetDto toGetDto(Sale sale) {
        return new SaleGetDto(sale.getId(),
                employeeMapper.toGetDto(sale.getEmployee()),
                sale.getCustomerId(),
                sale.getVehicleId(),
                sale.getAmmount(),
                sale.getDate(),
                sale.getWarrantyYears(),
                saleStatusMapper.toDto(sale.getSaleStatus()),
                sale.getDate().plusDays(sale.getDeliveryDays())
        );
    }

}
