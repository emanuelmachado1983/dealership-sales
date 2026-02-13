package org.emanuel.sales.infra.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleGetDto {
    private Long id;
    private EmployeeGetDto employeeGetDto;
    private Long customerId;
    private Long vehicleId;
    private Double ammount;
    private LocalDateTime date;
    private Integer warrantyYears;
    private SaleStatusDto saleStatusDto;
    private LocalDateTime deliveryDate;

}
