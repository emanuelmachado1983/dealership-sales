package org.emanuel.sales.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sale {
    private Long id;

    private Employee employee;

    private Long customerId;

    private Long vehicleId;

    private Double ammount;

    private LocalDateTime date;

    private Integer warrantyYears;

    private LocalDateTime dateCreated;

    private LocalDateTime dateModified;

    private SaleStatus saleStatus;

    private Integer deliveryDays;

    private Long officeSeller;


}
