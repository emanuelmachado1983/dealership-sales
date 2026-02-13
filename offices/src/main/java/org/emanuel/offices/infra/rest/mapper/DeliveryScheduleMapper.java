package org.emanuel.offices.infra.rest.mapper;

import org.emanuel.offices.infra.rest.dto.DeliveryScheduleGetDto;
import org.emanuel.offices.domain.DeliverySchedule;
import org.springframework.stereotype.Component;

@Component
public class DeliveryScheduleMapper {
    private final OfficeMapper officeMapper;

    public DeliveryScheduleMapper(OfficeMapper officeMapper) {
        this.officeMapper = officeMapper;
    }

    public DeliveryScheduleGetDto toDto(DeliverySchedule deliverySchedule) {
        return new DeliveryScheduleGetDto(
                deliverySchedule.getId(),
                officeMapper.toDto(deliverySchedule.getOfficeFrom()),
                officeMapper.toDto(deliverySchedule.getOfficeTo()),
                deliverySchedule.getDays()
        );
    }
}
