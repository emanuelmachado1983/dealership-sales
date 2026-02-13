package org.emanuel.sales.infra.integration.offices.dto;

import java.time.LocalDateTime;

public class OfficeFeignDto {
    private Long id;
    private Long idCountry;
    private Long idProvince;
    private Long idLocality;
    private String address;
    private String name;
    private LocalDateTime openingDate;
    private TypeOfficeFeignDto typeOffice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(Long idCountry) {
        this.idCountry = idCountry;
    }

    public Long getIdProvince() {
        return idProvince;
    }

    public void setIdProvince(Long idProvince) {
        this.idProvince = idProvince;
    }

    public Long getIdLocality() {
        return idLocality;
    }

    public void setIdLocality(Long idLocality) {
        this.idLocality = idLocality;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDateTime openingDate) {
        this.openingDate = openingDate;
    }

    public TypeOfficeFeignDto getTypeOffice() {
        return typeOffice;
    }

    public void setTypeOffice(TypeOfficeFeignDto typeOffice) {
        this.typeOffice = typeOffice;
    }
}
