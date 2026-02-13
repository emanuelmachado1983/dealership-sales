package org.emanuel.customers.infra.controller.mapper;

import org.emanuel.customers.infra.controller.dto.CustomerGetDto;
import org.emanuel.customers.domain.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public CustomerGetDto toGetDto(Customer customer) {
        return new CustomerGetDto(customer.getId(),
                customer.getName(),
                customer.getDni(),
                customer.getEmail(),
                customer.getPhone());
    }
}
