package org.emanuel.sales.infra.integration.vehicles.dto;

public class ModelVehicleFeignDto {
    private Long id;

    String brand;

    String model;

    Long year;

    Double price;

    public ModelVehicleFeignDto() {
    }

    public ModelVehicleFeignDto(Long id, String brand, String model, Long year, Double price) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
