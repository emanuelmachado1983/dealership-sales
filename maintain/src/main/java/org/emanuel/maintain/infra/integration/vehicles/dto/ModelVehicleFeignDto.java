package org.emanuel.maintain.infra.integration.vehicles.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModelVehicleFeignDto {
    private Long id;

    String brand;

    String model;

    Long year;

    Double price;

}
