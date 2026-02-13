package org.emanuel.offices.infra.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "provinces")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProvinceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    @JsonBackReference
    private CountryEntity country;

    @OneToMany(mappedBy = "province")
    @JsonManagedReference
    private List<LocalityEntity> localities;
}
