package org.emanuel.maintain.application.impl;

import lombok.RequiredArgsConstructor;
import org.emanuel.maintain.application.IMechanicalRepairService;
import org.emanuel.maintain.application.integration.ISalesService;
import org.emanuel.maintain.application.integration.IVehicleService;
import org.emanuel.maintain.domain.MechanicalRepair;
import org.emanuel.maintain.domain.StatusVehicleEnum;
import org.emanuel.maintain.domain.db.repository.MechanicalRepairRepository;
import org.emanuel.maintain.domain.exceptions.MechanicalRepairBadRequestException;
import org.emanuel.maintain.domain.exceptions.MechanicalRepairNotExistException;
import org.emanuel.maintain.domain.exceptions.VehicleBadRequestException;
import org.emanuel.maintain.domain.exceptions.VehicleNotExistException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MechanicalRepairServiceImpl implements IMechanicalRepairService {

    private final MechanicalRepairRepository mechanicalRepairRepository;
    private final IVehicleService vehicleService;
    private final ISalesService salesService;

    @Override
    public MechanicalRepair getMechanicalRepairById(Long id) {
        return mechanicalRepairRepository.findById(id).orElseThrow(() -> new MechanicalRepairNotExistException("MechanicalRepair not found with id: " + id));
    }

    @Override
    public List<MechanicalRepair> getAllMechanicalRepairs(Long idVehicle) {
        return mechanicalRepairRepository.findAllByFilters(idVehicle);
    }

    @Override
    @Transactional
    public void addMechanicalRepair(LocalDateTime enterDate, LocalDateTime deliveryDateEstimated, Long vehicleId, Long kmUnit) {
        //TODO: validar que el id de vehiculo exista
        if (enterDate.isAfter(deliveryDateEstimated)) {
            throw new MechanicalRepairBadRequestException("Enter date cannot be after the estimated delivery date.");
        }
        var vehicle = vehicleService.getVehicleById(vehicleId).orElseThrow(() -> new VehicleNotExistException("Vehicle not exists. Please, check the vehicle id."));
        if (!Objects.equals(vehicle.getStatus().getId(), StatusVehicleEnum.DELIVERED.getId())) {
            throw new MechanicalRepairBadRequestException("Cannot register a mechanical repair for a vehicle that is not delivered.");
        }
        //I go to the sale to see if the vehicle has a warranty
        var sale = salesService.findSales(vehicle.getId());
        if (sale == null) {
            throw new VehicleBadRequestException("The vehicle does not have a sale associated with it or the sale is not completed. So I can`t see if it has a warranty.");
        }
        var dateWithWarranty = sale.getDate().plusYears(sale.getWarrantyYears());
        var mechanicalRepair = new MechanicalRepair(
                null,
                enterDate,
                deliveryDateEstimated,
                null,
                vehicleId,
                kmUnit,
                null
        );

        mechanicalRepair.setUsingWarranty(dateWithWarranty.isAfter(mechanicalRepair.getEnterDate()));

        mechanicalRepairRepository.save(mechanicalRepair);

        try {
            vehicleService.putVehicle(mechanicalRepair.getVehicleId(), StatusVehicleEnum.IN_REPAIR.getId());
        } catch (Exception e) {
            throw new RuntimeException("Unexpected Error. Couldn`t modify state of vehicle ", e);
        }
    }

    @Override
    @Transactional
    public void updateMechanicalRepair(Long id, LocalDateTime enterDate, LocalDateTime deliveryDateEstimated, LocalDateTime deliveryDate, Long kmUnit) {
        var mechanicalRepair = mechanicalRepairRepository.findById(id).orElseThrow(() -> new MechanicalRepairNotExistException("MechanicalRepair not found with id: " + id));
        mechanicalRepair.setEnterDate(enterDate);
        mechanicalRepair.setDeliveryDateEstimated(deliveryDateEstimated);
        mechanicalRepair.setKmUnit(kmUnit);
        mechanicalRepair.setDeliveryDate(deliveryDate);

        if (!mechanicalRepairRepository.existsById(mechanicalRepair.getId())) {
            throw new MechanicalRepairNotExistException("Customer not found with id: " + mechanicalRepair.getId());
        }
        if (mechanicalRepair.getEnterDate().isAfter(mechanicalRepair.getDeliveryDateEstimated())) {
            throw new MechanicalRepairBadRequestException("Enter date cannot be after the estimated delivery date.");
        }
        if (deliveryDate != null && deliveryDate.isBefore(mechanicalRepair.getEnterDate())) {
            throw new MechanicalRepairBadRequestException("Delivery date cannot be before the enter date.");
        }
        var vehicle = vehicleService.getVehicleById(mechanicalRepair.getVehicleId()).orElseThrow(() -> new VehicleNotExistException("Vehicle not exists. Please, check the vehicle id."));
        if (!Objects.equals(vehicle.getStatus().getId(), StatusVehicleEnum.IN_REPAIR.getId())) {
            throw new MechanicalRepairBadRequestException("The vehicle is already repaired... cannot update the mechanical repair.");
        }

        var saved = mechanicalRepairRepository.save(mechanicalRepair);

        if (deliveryDate != null) {
            try {
                vehicleService.putVehicle(mechanicalRepair.getVehicleId(), StatusVehicleEnum.DELIVERED.getId());
            } catch (Exception e) {
                throw new RuntimeException("Unexpected Error. Couldn`t modify state of vehicle ", e);
            }
        }
    }

    @Override
    @Transactional
    public void deleteMechanicalRepair(Long id) {
        var mechanicalRepair = mechanicalRepairRepository.findById(id).orElseThrow(() -> new MechanicalRepairNotExistException("MechanicalRepair not found with id: " + id));
        if (mechanicalRepair.getDeliveryDate() != null) {
            throw new MechanicalRepairBadRequestException("Cannot delete a mechanical repair that has already been delivered.");
        }
        var vehicle = vehicleService.getVehicleById(mechanicalRepair.getVehicleId()).orElseThrow(() -> new VehicleNotExistException("Vehicle not exists. Please, check the vehicle id."));
        if (!Objects.equals(vehicle.getStatus().getId(), StatusVehicleEnum.IN_REPAIR.getId())) {
            throw new MechanicalRepairBadRequestException("The vehicle is already repaired... cannot update the mechanical repair.");
        }
        mechanicalRepairRepository.deleteById(id);
        try {
            vehicleService.putVehicle(mechanicalRepair.getVehicleId(), StatusVehicleEnum.DELIVERED.getId());
        } catch (Exception e) {
            throw new RuntimeException("Unexpected Error. Couldn`t modify state of vehicle ", e);
        }
    }

}
