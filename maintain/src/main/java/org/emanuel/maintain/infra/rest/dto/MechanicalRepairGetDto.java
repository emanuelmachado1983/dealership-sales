package org.emanuel.maintain.infra.rest.dto;

import java.time.LocalDateTime;

public class MechanicalRepairGetDto {
    private Long id;
    private LocalDateTime enterDate;
    private LocalDateTime deliveryDateEstimated;
    private LocalDateTime deliveryDate;
    private Long vehicleId;
    private Long kmUnit;
    private Boolean usingWarranty;

    public MechanicalRepairGetDto() {
    }

    public MechanicalRepairGetDto(Long id, LocalDateTime enterDate, LocalDateTime deliveryDateEstimated, LocalDateTime deliveryDate, Long vehicleId, Long kmUnit, Boolean usingWarranty) {
        this.id = id;
        this.enterDate = enterDate;
        this.deliveryDateEstimated = deliveryDateEstimated;
        this.deliveryDate = deliveryDate;
        this.vehicleId = vehicleId;
        this.kmUnit = kmUnit;
        this.usingWarranty = usingWarranty;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
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

    public Boolean getUsingWarranty() {
        return usingWarranty;
    }

    public void setUsingWarranty(Boolean usingWarranty) {
        this.usingWarranty = usingWarranty;
    }
}
