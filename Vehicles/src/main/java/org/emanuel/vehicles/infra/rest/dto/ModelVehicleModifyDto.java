package org.emanuel.vehicles.infra.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModelVehicleModifyDto {
    private String brand;

    private String model;

    private Long year;

    private Double price;

}
