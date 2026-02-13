package org.emanuel.customers.infra.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerGetDto {
    private Long id;

    private String name;

    private String dni;

    private String email;

    private String phone;

}
