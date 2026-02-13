package org.emanuel.maintain.infra.rest.mapper;

import org.emanuel.maintain.infra.rest.dto.MechanicalRepairBodyPostDto;
import org.emanuel.maintain.infra.rest.dto.MechanicalRepairGetDto;
import org.emanuel.maintain.infra.rest.dto.MechanicalRepairPostDto;
import org.emanuel.maintain.domain.MechanicalRepair;
import org.springframework.stereotype.Component;

@Component
public class MechanicalRepairMapper {

    public MechanicalRepairBodyPostDto toBodyPostDto(MechanicalRepair mechanicalRepair) {
        return new MechanicalRepairBodyPostDto(
                mechanicalRepair.getId()
        );
    }

    public MechanicalRepairGetDto toGetDto(MechanicalRepair mechanicalRepair) {
        return new MechanicalRepairGetDto(
                mechanicalRepair.getId(),
                mechanicalRepair.getEnterDate(),
                mechanicalRepair.getDeliveryDateEstimated(),
                mechanicalRepair.getDeliveryDate(),
                mechanicalRepair.getVehicleId(),
                mechanicalRepair.getKmUnit(),
                mechanicalRepair.getUsingWarranty()
        );
    }

    public MechanicalRepair postToModel(Long id, MechanicalRepairPostDto mechanicalRepairPostDto) {

        return new MechanicalRepair(
                id,
                mechanicalRepairPostDto.getEnterDate(),
                mechanicalRepairPostDto.getDeliveryDateEstimated(),
                null,
                mechanicalRepairPostDto.getVehicleId(),
                mechanicalRepairPostDto.getKmUnit(),
                null
        );
    }

}
