package org.emanuel.offices.infra.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.emanuel.offices.domain.Office;

@Entity
@Table(name = "delivery_schedules")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "office_from_id", nullable = false)
    @JsonBackReference
    private OfficeEntity officeFrom;

    @ManyToOne
    @JoinColumn(name = "office_to_id", nullable = false)
    @JsonBackReference
    private OfficeEntity officeTo;

    @Column(name = "days", nullable = false)
    private Integer days;

}
