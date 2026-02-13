package org.emanuel.maintain.infra.rest.dto;

import java.time.LocalDateTime;

public class MechanicalRepairModifyDto {
    private LocalDateTime enterDate;
    private LocalDateTime deliveryDateEstimated;
    private LocalDateTime deliveryDate;
    private Long kmUnit;

    public MechanicalRepairModifyDto() {
    }

    public MechanicalRepairModifyDto(LocalDateTime enterDate, LocalDateTime deliveryDateEstimated, LocalDateTime deliveryDate, Long kmUnit) {
        this.enterDate = enterDate;
        this.deliveryDateEstimated = deliveryDateEstimated;
        this.deliveryDate = deliveryDate;
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

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Long getKmUnit() {
        return kmUnit;
    }

    public void setKmUnit(Long kmUnit) {
        this.kmUnit = kmUnit;
    }
}
