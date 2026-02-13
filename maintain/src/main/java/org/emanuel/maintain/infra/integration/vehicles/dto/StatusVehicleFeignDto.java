package org.emanuel.maintain.infra.integration.vehicles.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusVehicleFeignDto {
    private Long id;
    private String name;

}
