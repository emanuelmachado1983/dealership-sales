package org.emanuel.maintain.infra.db.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "mechanical_repairs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MechanicalRepairEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "enter_date" , nullable = false)
    private LocalDateTime enterDate;

    @Column(name = "delivery_date_estimated" , nullable = false)
    private LocalDateTime deliveryDateEstimated;

    @Column(name = "delivery_date" , nullable = true)
    private LocalDateTime deliveryDate;

    @Column(name = "vehicle_id")
    private Long vehicleId;

    @Column(name = "km_unit" , nullable = false)
    private Long kmUnit;

    @Column(name = "using_warranty" , nullable = false)
    private Boolean usingWarranty;


}
