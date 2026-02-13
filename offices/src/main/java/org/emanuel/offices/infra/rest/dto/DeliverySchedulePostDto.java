package org.emanuel.offices.infra.rest.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliverySchedulePostDto {
    private Long officeFrom;
    private Long officeTo;
    private Integer days;

}
