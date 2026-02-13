package org.emanuel.vehicles.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vehicle {

    private Long id;

    private ModelVehicle model;

    private String description;

    private StatusVehicle status;

    private TypeVehicle type;

    private String patent;

    private Long officeLocationId;


}
