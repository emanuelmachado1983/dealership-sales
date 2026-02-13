package org.emanuel.sales.infra.integration.vehicles.dto;

public class TypeVehicleFeignDto {
    private Long id;
    private String name;
    private Integer warrantyYears;

    public TypeVehicleFeignDto() {
    }

    public TypeVehicleFeignDto(Long id, String name, Integer warrantyYears) {
        this.id = id;
        this.name = name;
        this.warrantyYears = warrantyYears;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWarrantyYears() {
        return warrantyYears;
    }

    public void setWarrantyYears(Integer warrantyYears) {
        this.warrantyYears = warrantyYears;
    }
}
