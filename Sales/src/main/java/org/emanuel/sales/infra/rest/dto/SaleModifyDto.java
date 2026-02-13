package org.emanuel.sales.infra.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleModifyDto {
    private Long employeeId;
    private Long customerId;
    private Long idStatus;

}
