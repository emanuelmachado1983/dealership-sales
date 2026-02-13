package org.emanuel.sales.infra.db.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "sales")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeEntity employeeEntity;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "vehicle_id", nullable = false)
    private Long vehicleId;

    @Column(name = "ammount", nullable = false)
    private Double ammount;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "warranty_years", nullable = false)
    private Integer warrantyYears;

    @Column(name = "date_created", updatable = false)
    private LocalDateTime dateCreated;

    @Column(name = "date_modified", updatable = false)
    private LocalDateTime dateModified;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private SaleStatusEntity saleStatusEntity;

    @Column(name = "delivery_days", nullable = false)
    private Integer deliveryDays;

    @Column(name = "office_seller", nullable = false)
    private Long officeSeller;

    @PrePersist
    protected void onCreate() {
        this.dateCreated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dateModified = LocalDateTime.now();
    }



}
