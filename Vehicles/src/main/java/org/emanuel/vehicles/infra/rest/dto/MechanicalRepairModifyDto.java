package org.emanuel.vehicles.infra.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MechanicalRepairModifyDto {
    private LocalDateTime enterDate;
    private LocalDateTime deliveryDateEstimated;
    private LocalDateTime deliveryDate;
    private Long kmUnit;

}
