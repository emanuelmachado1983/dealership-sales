package org.emanuel.vehicles.infra.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleModifyDto {
    private Long modelId;
    private String description;
    private Long statusId;
    private Long typeId;
    private String patent;
    private Long officeLocationId;

}
