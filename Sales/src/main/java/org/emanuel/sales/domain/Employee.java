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
public class Employee {
    private Long id;

    private String name;

    private String dni;

    private String email;

    private String phone;

    private LocalDateTime deletedAt;

    public Employee(Long id) {
        this.id = id;
    }

    public Employee(Long id, String name, String dni, String email, String phone) {
        this.id = id;
        this.name = name;
        this.dni = dni;
        this.email = email;
        this.phone = phone;
    }
}
