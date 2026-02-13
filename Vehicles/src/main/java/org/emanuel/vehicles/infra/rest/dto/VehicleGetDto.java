package org.emanuel.vehicles.infra.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleGetDto {
    private Long id;
    private ModelVehicleGetDto model;
    private String description;
    private StatusVehicleGetDto status;
    private TypeVehicleGetDto type;
    private String patent;
    private Long officeLocationId;

}
