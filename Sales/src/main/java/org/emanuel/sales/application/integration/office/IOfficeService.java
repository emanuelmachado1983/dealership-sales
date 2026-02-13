package org.emanuel.sales.application.integration.office;

import org.emanuel.sales.domain.office.Office;

import java.util.Optional;

public interface IOfficeService {
    Optional<Office> getOfficeById(Long idOffice);

    Optional<Integer> getDeliveryScheduleIdByOfficeId(Long idOfficeFrom, Long idOffice);
}
