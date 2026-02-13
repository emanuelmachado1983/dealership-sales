package org.emanuel.sales.infra.integration.offices.dto;

public class DeliveryScheduleFeignDto {
    private Long id;
    private OfficeFeignDto officeFrom;
    private OfficeFeignDto officeTo;
    private Integer days;

    public DeliveryScheduleFeignDto() {
    }

    public DeliveryScheduleFeignDto(Long id, OfficeFeignDto officeFrom, OfficeFeignDto officeTo, Integer days) {
        this.id = id;
        this.officeFrom = officeFrom;
        this.officeTo = officeTo;
        this.days = days;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OfficeFeignDto getOfficeFrom() {
        return officeFrom;
    }

    public void setOfficeFrom(OfficeFeignDto officeFrom) {
        this.officeFrom = officeFrom;
    }

    public OfficeFeignDto getOfficeTo() {
        return officeTo;
    }

    public void setOfficeTo(OfficeFeignDto officeTo) {
        this.officeTo = officeTo;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}
