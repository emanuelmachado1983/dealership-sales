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
public class SalePostDto {
    private Long employeeId;
    private Long customerId;
    private Long vehicleId;
    private LocalDateTime date;
    private Long officeSellerId;

}
