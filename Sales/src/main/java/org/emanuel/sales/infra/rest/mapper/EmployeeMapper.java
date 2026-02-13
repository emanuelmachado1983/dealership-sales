package org.emanuel.sales.infra.rest.mapper;

import org.emanuel.sales.infra.rest.dto.EmployeeGetDto;
import org.emanuel.sales.domain.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public EmployeeGetDto toGetDto(Employee employee) {
        return new EmployeeGetDto(employee.getId(),
                employee.getName(),
                employee.getDni(),
                employee.getEmail(),
                employee.getPhone());
    }
}
