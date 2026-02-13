package org.emanuel.customers.infra.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerEntity {
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

    public CustomerEntity(Long id) {
        this.id = id;
    }

    public CustomerEntity(Long id, String name, String dni, String email, String phone) {
        this.id = id;
        this.name = name;
        this.dni = dni;
        this.email = email;
        this.phone = phone;
    }

}
