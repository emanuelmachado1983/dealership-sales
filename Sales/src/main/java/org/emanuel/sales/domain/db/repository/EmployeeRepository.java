package org.emanuel.sales.domain.db.repository;

import org.emanuel.sales.domain.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {
    Optional<Employee> findByIdAndNotDeleted(Long id);
    List<Employee> findAllAndNotDeleted();
    Employee save(Employee employee);
    boolean existsByIdAndNotDeleted(Long id);
}
