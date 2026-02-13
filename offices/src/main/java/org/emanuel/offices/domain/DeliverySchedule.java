package org.emanuel.offices.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliverySchedule {

    private Long id;

    private Office officeFrom;

    private Office officeTo;

    private Integer days;

}
