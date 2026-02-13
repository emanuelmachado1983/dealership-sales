package org.emanuel.sales.infra.integration.offices;

import feign.FeignException;
import org.emanuel.sales.application.integration.office.IOfficeService;
import org.emanuel.sales.infra.integration.offices.mapper.OfficeMapper;
import org.emanuel.sales.domain.office.Office;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfficeServiceImpl implements IOfficeService {
    private final IOfficeFeign officeFeign;
    private final OfficeMapper officeMapper;

    public OfficeServiceImpl(IOfficeFeign officeFeign,
                             OfficeMapper officeMapper) {
        this.officeFeign = officeFeign;
        this.officeMapper = officeMapper;
    }

    @Override
    public Optional<Office> getOfficeById(Long idOffice) {
        try {
            var office = officeFeign.findById(idOffice);
            return Optional.of(
                    officeMapper.toModel(office)
            );
        } catch (FeignException.NotFound e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Integer> getDeliveryScheduleIdByOfficeId(Long idOfficeFrom, Long idOfficeTo) {
        try {
            var deliveries = Optional.ofNullable(officeFeign.findDeliverySchedule(idOfficeTo))
                    .orElse(List.of())
                    .stream().filter(x->x.getOfficeFrom().getId().equals(idOfficeFrom)).toList();
            if (deliveries.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(deliveries.get(0).getDays());
        } catch (FeignException.NotFound e) {
            return Optional.empty();
        }
    }
}
