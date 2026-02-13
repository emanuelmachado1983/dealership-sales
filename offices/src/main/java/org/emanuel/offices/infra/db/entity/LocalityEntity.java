package org.emanuel.offices.infra.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "localities")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocalityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "province_id", nullable = false)
    @JsonBackReference
    private ProvinceEntity province;
}

