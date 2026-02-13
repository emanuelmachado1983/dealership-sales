package org.emanuel.maintain.infra.integration.sales.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleFeignGetDto {
    private Long id;

    @JsonAlias("employeeGetDto")
    private EmployeeFeignGetDto employeeFeignGetDto;

    @JsonAlias("customerId")
    private Long customerFeignGetDto;
    private Long vehicleId;
    private Double ammount;
    private LocalDateTime date;
    private Integer warrantyYears;

    @JsonAlias("saleStatusDto")
    private SaleStatusFeignDto saleStatus;

}
