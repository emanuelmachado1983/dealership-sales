package org.emanuel.maintain.infra.db.mapper;

import org.emanuel.maintain.domain.MechanicalRepair;
import org.emanuel.maintain.infra.db.dto.MechanicalRepairEntity;
import org.springframework.stereotype.Component;

@Component
public class MechanicalRepairEntityMapper {
    public MechanicalRepair toDomain(MechanicalRepairEntity entity) {
        if (entity == null) return null;
        return MechanicalRepair.builder()
                .id(entity.getId())
                .enterDate(entity.getEnterDate())
                .deliveryDateEstimated(entity.getDeliveryDateEstimated())
                .deliveryDate(entity.getDeliveryDate())
                .vehicleId(entity.getVehicleId())
                .kmUnit(entity.getKmUnit())
                .usingWarranty(entity.getUsingWarranty())
                .build();
    }

    public MechanicalRepairEntity toEntity(MechanicalRepair mechanicalRepair) {
        if (mechanicalRepair == null) return null;
        return MechanicalRepairEntity.builder()
                .id(mechanicalRepair.getId())
                .enterDate(mechanicalRepair.getEnterDate())
                .deliveryDateEstimated(mechanicalRepair.getDeliveryDateEstimated())
                .deliveryDate(mechanicalRepair.getDeliveryDate())
                .vehicleId(mechanicalRepair.getVehicleId())
                .kmUnit(mechanicalRepair.getKmUnit())
                .usingWarranty(mechanicalRepair.getUsingWarranty())
                .build();
    }
}
