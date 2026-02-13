package org.emanuel.offices.infra.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "offices")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfficeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_country", nullable = false)
    private Long idCountry;

    @Column(name = "id_province", nullable = false)
    private Long idProvince;

    @Column(name = "id_locality", nullable = false)
    private Long idLocality;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "opening_date", nullable = false)
    private LocalDateTime openingDate;

    @ManyToOne
    @JoinColumn(name = "type_office_id", nullable = false)
    @JsonBackReference
    private TypeOfficeEntity typeOffice;

    private LocalDateTime deletedAt;


}
