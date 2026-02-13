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
public class MechanicalRepairGetDto {
    private Long id;
    private LocalDateTime enterDate;
    private LocalDateTime deliveryDateEstimated;
    private LocalDateTime deliveryDate;
    private VehicleGetDto vehicle;
    private Long kmUnit;
    private Boolean usingWarranty;

}
