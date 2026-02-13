package org.emanuel.sales.infra.db.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sale_states")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    public SaleStatusEntity(Long id) {
        this.id = id;
    }
}
