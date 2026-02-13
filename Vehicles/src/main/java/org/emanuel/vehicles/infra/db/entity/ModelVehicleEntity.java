package org.emanuel.vehicles.infra.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vehicle_models")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModelVehicleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand" , nullable = false)
    private String brand;

    @Column(name = "model" , nullable = false)
    private String model;

    @Column(name = "year_model" , nullable = false)
    private Long year;

    @Column(name = "price" , nullable = false)
    private Double price;
}
