package org.emanuel.maintain.infra.rest.dto;

public class MechanicalRepairBodyPostDto {
    private Long id;

    public MechanicalRepairBodyPostDto() {
    }

    public MechanicalRepairBodyPostDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
