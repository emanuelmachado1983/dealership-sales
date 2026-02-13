package org.emanuel.sales.infra.integration.vehicles.dto;

public class VehicleFeignDto {
    private Long id;
    private ModelVehicleFeignDto model;
    private String description;
    private StatusVehicleFeignDto status;
    private TypeVehicleFeignDto type;
    private String patent;
    private Long officeLocationId;

    public VehicleFeignDto() {
    }

    public VehicleFeignDto(Long id, ModelVehicleFeignDto model, String description, StatusVehicleFeignDto status, TypeVehicleFeignDto type, String patent, Long officeLocationId) {
        this.id = id;
        this.model = model;
        this.description = description;
        this.status = status;
        this.type = type;
        this.patent = patent;
        this.officeLocationId = officeLocationId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ModelVehicleFeignDto getModel() {
        return model;
    }

    public void setModel(ModelVehicleFeignDto model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatusVehicleFeignDto getStatus() {
        return status;
    }

    public void setStatus(StatusVehicleFeignDto status) {
        this.status = status;
    }

    public TypeVehicleFeignDto getType() {
        return type;
    }

    public void setType(TypeVehicleFeignDto type) {
        this.type = type;
    }

    public String getPatent() {
        return patent;
    }

    public void setPatent(String patent) {
        this.patent = patent;
    }

    public Long getOfficeLocationId() {
        return officeLocationId;
    }

    public void setOfficeLocationId(Long officeLocationId) {
        this.officeLocationId = officeLocationId;
    }
}
