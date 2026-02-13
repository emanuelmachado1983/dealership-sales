package org.emanuel.vehicles.infra.integration.sales.dto;

import java.time.LocalDateTime;

public class SaleFeignGetDto {
    private Long id;
    private EmployeeFeignGetDto employeeFeignGetDto;
    private CustomerFeignGetDto customerFeignGetDto;
    private Long vehicleId;
    private Double ammount;
    private LocalDateTime date;
    private Integer warrantyYears;
    private SaleStatusFeignDto saleStatus;

    public SaleFeignGetDto() {
    }

    public SaleFeignGetDto(Long id, EmployeeFeignGetDto employeeFeignGetDto, CustomerFeignGetDto customerFeignGetDto, Long vehicleId, Double ammount, LocalDateTime date, Integer warrantyYears, SaleStatusFeignDto saleStatus) {
        this.id = id;
        this.employeeFeignGetDto = employeeFeignGetDto;
        this.customerFeignGetDto = customerFeignGetDto;
        this.vehicleId = vehicleId;
        this.ammount = ammount;
        this.date = date;
        this.warrantyYears = warrantyYears;
        this.saleStatus = saleStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeFeignGetDto getEmployeeFeignGetDto() {
        return employeeFeignGetDto;
    }

    public void setEmployeeFeignGetDto(EmployeeFeignGetDto employeeFeignGetDto) {
        this.employeeFeignGetDto = employeeFeignGetDto;
    }

    public CustomerFeignGetDto getCustomerFeignGetDto() {
        return customerFeignGetDto;
    }

    public void setCustomerFeignGetDto(CustomerFeignGetDto customerFeignGetDto) {
        this.customerFeignGetDto = customerFeignGetDto;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Double getAmmount() {
        return ammount;
    }

    public void setAmmount(Double ammount) {
        this.ammount = ammount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getWarrantyYears() {
        return warrantyYears;
    }

    public void setWarrantyYears(Integer warrantyYears) {
        this.warrantyYears = warrantyYears;
    }

    public SaleStatusFeignDto getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(SaleStatusFeignDto saleStatus) {
        this.saleStatus = saleStatus;
    }
}
