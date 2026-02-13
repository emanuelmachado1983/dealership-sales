package org.emanuel.sales.application.impl;

import lombok.RequiredArgsConstructor;
import org.emanuel.sales.application.IEmployeeService;
import org.emanuel.sales.domain.Employee;
import org.emanuel.sales.domain.db.repository.EmployeeRepository;
import org.emanuel.sales.domain.exceptions.EmployeeException;
import org.emanuel.sales.domain.exceptions.EmployeeNotExistException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements IEmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findByIdAndNotDeleted(id).orElseThrow(() -> new EmployeeNotExistException("Employee not found with id: " + id));
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAllAndNotDeleted();
    }

    @Override
    public Employee addEmployee(String name, String dni, String email, String phone) {
        var saved = employeeRepository.save(new Employee(null, name, dni, email, phone));
        return employeeRepository.findByIdAndNotDeleted(saved.getId()).orElseThrow(() -> new EmployeeException("Error finding employee after save"));
    }

    @Override
    public void updateEmployee(Long idEmployee, String name, String dni, String email, String phone) {
        if (employeeRepository.existsByIdAndNotDeleted(idEmployee)) {
            employeeRepository.save(new Employee(idEmployee, name, dni, email, phone));
        } else {
            throw new EmployeeNotExistException("Employee not found with id: " + idEmployee);
        }
    }

    @Override
    public void deleteEmployee(Long id) {
        var Employee = employeeRepository.findByIdAndNotDeleted(id).orElseThrow(() -> new EmployeeNotExistException("Employee not found with id: " + id));
        Employee.setDeletedAt(LocalDateTime.now());
        employeeRepository.save(Employee);
    }
}
