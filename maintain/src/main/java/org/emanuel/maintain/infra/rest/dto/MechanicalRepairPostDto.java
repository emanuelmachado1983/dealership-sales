package org.emanuel.maintain.infra.rest.dto;

import java.time.LocalDateTime;

public class MechanicalRepairPostDto {
    private LocalDateTime enterDate;
    private LocalDateTime deliveryDateEstimated;
    private Long vehicleId;
    private Long kmUnit;

    public MechanicalRepairPostDto() {
    }

    public MechanicalRepairPostDto(LocalDateTime enterDate, LocalDateTime deliveryDateEstimated, LocalDateTime deliveryDate, Long vehicleId, Long kmUnit) {
        this.enterDate = enterDate;
        this.deliveryDateEstimated = deliveryDateEstimated;
        this.vehicleId = vehicleId;
        this.kmUnit = kmUnit;
    }

    public LocalDateTime getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(LocalDateTime enterDate) {
        this.enterDate = enterDate;
    }

    public LocalDateTime getDeliveryDateEstimated() {
        return deliveryDateEstimated;
    }

    public void setDeliveryDateEstimated(LocalDateTime deliveryDateEstimated) {
        this.deliveryDateEstimated = deliveryDateEstimated;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Long getKmUnit() {
        return kmUnit;
    }

    public void setKmUnit(Long kmUnit) {
        this.kmUnit = kmUnit;
    }

}
