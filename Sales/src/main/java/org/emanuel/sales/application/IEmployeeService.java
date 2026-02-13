package org.emanuel.sales.application;

import org.emanuel.sales.domain.Employee;
import org.emanuel.sales.infra.rest.dto.EmployeeGetDto;
import org.emanuel.sales.domain.exceptions.EmployeeException;
import org.emanuel.sales.domain.exceptions.EmployeeNotExistException;

import java.util.List;

public interface IEmployeeService {
    Employee getEmployeeById(Long id);

    List<Employee> getAllEmployees();

    Employee addEmployee(String name, String dni, String email, String phone);

    void updateEmployee(Long idEmployee, String name, String dni, String email, String phone);

    void deleteEmployee(Long id);
}
