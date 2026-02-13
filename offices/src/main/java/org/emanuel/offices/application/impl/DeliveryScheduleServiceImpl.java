package org.emanuel.offices.application.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.emanuel.offices.application.IDeliveryScheduleService;
import org.emanuel.offices.application.IOfficeService;
import org.emanuel.offices.domain.DeliverySchedule;
import org.emanuel.offices.domain.Office;
import org.emanuel.offices.domain.exceptions.DeliveryScheduleBadRequestDomainException;
import org.emanuel.offices.domain.exceptions.DeliveryScheduleNotExistsDomainException;
import org.emanuel.offices.domain.db.repository.DeliveryScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DeliveryScheduleServiceImpl implements IDeliveryScheduleService {
    private final IOfficeService officeService;
    private final DeliveryScheduleRepository deliveryScheduleRepository;

    @Override
    public List<DeliverySchedule> getAllDeliverySchedules() {
        return deliveryScheduleRepository.findAll();
    }

    @Override
    public List<DeliverySchedule> getAllDeliverySchedules(Long officeTo) {
        if (!officeService.officeExists(officeTo)) {
            throw new DeliveryScheduleBadRequestDomainException("Office with id " + officeTo + " does not exist.");
        }
        return deliveryScheduleRepository.findDeliveryScheduleByOfficeTo(officeTo);
    }

    @Override
    @Transactional
    public void addDeliverySchedule(Long officeFrom, Long officeTo, Integer days) {
        if (!officeService.officeExists(officeFrom) || !officeService.officeExists(officeTo)) {
            throw new DeliveryScheduleBadRequestDomainException("One or both offices do not exist.");
        }
        if (days <= 0) {
            throw new DeliveryScheduleBadRequestDomainException("Days must be greater than zero.");
        }
        if (deliveryScheduleRepository.existsByOfficeFromAndOfficeTo(officeFrom, officeTo)) {
            throw new DeliveryScheduleBadRequestDomainException("Delivery schedule already exists for the given offices.");
        }
        var officeFromObj = new Office();
        officeFromObj.setId(officeFrom);
        var officeToObj = new Office();
        officeToObj.setId(officeTo);
        DeliverySchedule deliverySchedule = new DeliverySchedule();
        deliverySchedule.setOfficeFrom(officeFromObj);
        deliverySchedule.setOfficeTo(officeToObj);
        deliverySchedule.setDays(days);
        deliveryScheduleRepository.save(deliverySchedule);
    }

    @Override
    public void deleteDeliverySchedule(Long id) {
        deliveryScheduleRepository.findById(id).orElseThrow(() -> new DeliveryScheduleNotExistsDomainException("Delivery schedule with id " + id + " does not exist."));
        deliveryScheduleRepository.deleteById(id);
    }
}
