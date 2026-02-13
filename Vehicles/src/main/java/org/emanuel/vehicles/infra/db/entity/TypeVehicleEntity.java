package org.emanuel.vehicles.infra.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vehicle_types")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TypeVehicleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "warranty_years", nullable = false)
    private Integer warrantyYears;

}
