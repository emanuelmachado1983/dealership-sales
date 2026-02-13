package org.emanuel.maintain.infra.integration.vehicles.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleFeignDto {
    private Long id;
    private ModelVehicleFeignDto model;
    private String description;
    private StatusVehicleFeignDto status;
    private TypeVehicleFeignDto type;
    private String patent;
    private Long officeLocationId;

}
