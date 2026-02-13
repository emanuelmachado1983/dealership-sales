package org.emanuel.customers.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "dni", nullable = false)
    private String dni;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Customer(Long id) {
        this.id = id;
    }

    public Customer(Long id, String name, String dni, String email, String phone) {
        this.id = id;
        this.name = name;
        this.dni = dni;
        this.email = email;
        this.phone = phone;
    }

}
