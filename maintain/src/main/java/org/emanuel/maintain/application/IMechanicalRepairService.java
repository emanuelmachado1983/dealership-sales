package org.emanuel.maintain.application;

import org.emanuel.maintain.domain.MechanicalRepair;
import org.emanuel.maintain.infra.rest.dto.MechanicalRepairGetDto;
import org.emanuel.maintain.infra.rest.dto.MechanicalRepairPostDto;
import org.emanuel.maintain.domain.exceptions.MechanicalRepairBadRequestException;
import org.emanuel.maintain.domain.exceptions.MechanicalRepairNotExistException;
import org.emanuel.maintain.domain.exceptions.VehicleBadRequestException;
import org.emanuel.maintain.domain.exceptions.VehicleNotExistException;

import java.time.LocalDateTime;
import java.util.List;

public interface IMechanicalRepairService {
    MechanicalRepair getMechanicalRepairById(Long id);

    List<MechanicalRepair> getAllMechanicalRepairs(Long customerId);

    void addMechanicalRepair(LocalDateTime enterDate, LocalDateTime deliveryDateEstimated, Long vehicleId, Long kmUnit );

    void updateMechanicalRepair(Long id, LocalDateTime enterDate, LocalDateTime deliveryDateEstimated, LocalDateTime deliveryDate, Long kmUnit);

    void deleteMechanicalRepair(Long id);
}
