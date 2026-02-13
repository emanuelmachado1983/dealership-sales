package org.emanuel.vehicles.domain;

import jakarta.persistence.*;
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

    private String brand;

    private String model;

    private Long year;

    private Double price;
}
