package org.emanuel.sales.domain.vehicle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModelVehicle {
    private Long id;

    String brand;

    String model;

    Long year;

    Double price;

}
