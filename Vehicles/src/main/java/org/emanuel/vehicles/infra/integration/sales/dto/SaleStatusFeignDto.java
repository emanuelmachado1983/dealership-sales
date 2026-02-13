package org.emanuel.vehicles.infra.integration.sales.dto;

public class SaleStatusFeignDto {
    private Long id;
    private String name;

    public SaleStatusFeignDto() {
    }

    public SaleStatusFeignDto(Long id, String name) {
        this.id = id;
        this.name = name;
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
}
