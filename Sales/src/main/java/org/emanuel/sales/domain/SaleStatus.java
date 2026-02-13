package org.emanuel.sales.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleStatus {
    private Long id;

    private String name;

    public SaleStatus(Long id) {
        this.id = id;
    }
}
