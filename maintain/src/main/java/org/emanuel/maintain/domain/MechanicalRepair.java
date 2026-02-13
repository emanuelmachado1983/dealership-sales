package org.emanuel.maintain.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MechanicalRepair {
    private Long id;

    private LocalDateTime enterDate;

    private LocalDateTime deliveryDateEstimated;

    private LocalDateTime deliveryDate;

    private Long vehicleId;

    private Long kmUnit;

    private Boolean usingWarranty;

}
