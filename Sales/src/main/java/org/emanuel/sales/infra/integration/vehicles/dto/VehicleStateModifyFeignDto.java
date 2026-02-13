package org.emanuel.sales.infra.integration.vehicles.dto;

public class VehicleStateModifyFeignDto {
    private Long statusId;

    public VehicleStateModifyFeignDto() {
    }

    public VehicleStateModifyFeignDto(Long statusId) {
        this.statusId = statusId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }
}
