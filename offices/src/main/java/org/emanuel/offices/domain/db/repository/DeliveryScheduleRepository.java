package org.emanuel.offices.domain.db.repository;

import org.emanuel.offices.domain.DeliverySchedule;

import java.util.List;
import java.util.Optional;

public interface DeliveryScheduleRepository {

    List<DeliverySchedule> findAll();

    List<DeliverySchedule> findDeliveryScheduleByOfficeTo(Long officeTo);

    boolean existsByOfficeFromAndOfficeTo(Long officeFrom, Long officeTo);

    Optional<DeliverySchedule> findById(Long id);

    DeliverySchedule save(DeliverySchedule deliverySchedule);

    void deleteById(Long id);
}

