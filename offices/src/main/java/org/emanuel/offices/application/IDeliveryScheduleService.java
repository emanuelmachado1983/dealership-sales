package org.emanuel.offices.application;

import org.emanuel.offices.domain.DeliverySchedule;

import java.util.List;

public interface IDeliveryScheduleService {
    List<DeliverySchedule> getAllDeliverySchedules();

    List<DeliverySchedule> getAllDeliverySchedules(Long officeTo);

    void addDeliverySchedule(Long officeFrom, Long officeTo, Integer days);

    void deleteDeliverySchedule(Long id);
}
