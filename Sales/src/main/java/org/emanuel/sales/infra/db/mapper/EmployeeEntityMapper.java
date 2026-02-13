package org.emanuel.sales.infra.db.mapper;

import org.emanuel.sales.domain.Employee;
import org.emanuel.sales.infra.db.dto.EmployeeEntity;
import org.springframework.stereotype.Component;

@Component
public class EmployeeEntityMapper {
    public Employee toDomain(EmployeeEntity employeeEntity) {
        if (employeeEntity == null) {
            return null;
        }
        return Employee.builder()
                .id(employeeEntity.getId())
                .name(employeeEntity.getName())
                .dni(employeeEntity.getDni())
                .email(employeeEntity.getEmail())
                .phone(employeeEntity.getPhone())
                .deletedAt(employeeEntity.getDeletedAt())
                .build();
    }

    public EmployeeEntity toEntity(Employee employee) {
        if (employee == null) {
            return null;
        }
        return EmployeeEntity.builder()
                .id(employee.getId())
                .name(employee.getName())
                .dni(employee.getDni())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .deletedAt(employee.getDeletedAt())
                .build();
    }
}
