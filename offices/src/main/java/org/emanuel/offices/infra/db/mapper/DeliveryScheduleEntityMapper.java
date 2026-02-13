package org.emanuel.offices.infra.db.mapper;

import lombok.AllArgsConstructor;
import org.emanuel.offices.domain.Country;
import org.emanuel.offices.domain.DeliverySchedule;
import org.emanuel.offices.infra.db.entity.CountryEntity;
import org.emanuel.offices.infra.db.entity.DeliveryScheduleEntity;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DeliveryScheduleEntityMapper {
    private final OfficeEntityMapper officeEntityMapper;

    public DeliverySchedule toDomain(DeliveryScheduleEntity entity) {
        if (entity == null) return null;
        return DeliverySchedule.builder()
                .id(entity.getId())
                .officeFrom(officeEntityMapper.toDomain(entity.getOfficeFrom()))
                .officeTo(officeEntityMapper.toDomain(entity.getOfficeTo()))
                .days(entity.getDays())
                .build();
    }

    public DeliveryScheduleEntity toEntity(DeliverySchedule domain) {
        if (domain == null) return null;
        return DeliveryScheduleEntity.builder()
                .id(domain.getId())
                .officeFrom(officeEntityMapper.toEntity(domain.getOfficeFrom()))
                .officeTo(officeEntityMapper.toEntity(domain.getOfficeTo()))
                .days(domain.getDays())
                .build();
    }
}

