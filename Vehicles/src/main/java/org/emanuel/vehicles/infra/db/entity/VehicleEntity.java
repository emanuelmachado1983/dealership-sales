package org.emanuel.vehicles.infra.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emanuel.vehicles.domain.ModelVehicle;
import org.emanuel.vehicles.domain.StatusVehicle;
import org.emanuel.vehicles.domain.TypeVehicle;

@Entity
@Table(name = "vehicles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private ModelVehicleEntity model;

    @Column(name = "description" , nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private StatusVehicleEntity status;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private TypeVehicleEntity type;

    @Column(name = "patent" , nullable = false)
    private String patent;

    @Column(name = "office_location_id" , nullable = false)
    private Long officeLocationId;

}
