package org.emanuel.sales.infra.db.repository.impl;

import lombok.RequiredArgsConstructor;
import org.emanuel.sales.domain.Employee;
import org.emanuel.sales.domain.db.repository.EmployeeRepository;
import org.emanuel.sales.infra.db.mapper.EmployeeEntityMapper;
import org.emanuel.sales.infra.db.repository.IEmployeeDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepository {
    private final IEmployeeDao employeeDao;
    private final EmployeeEntityMapper employeeEntityMapper;

    @Override
    public Optional<Employee> findByIdAndNotDeleted(Long id) {
        return employeeDao.findByIdAndNotDeleted(id).map(employeeEntityMapper::toDomain);
    }

    @Override
    public List<Employee> findAllAndNotDeleted() {
        return employeeDao.findAllAndNotDeleted().stream().map(employeeEntityMapper::toDomain).toList();
    }

    @Override
    public Employee save(Employee employee) {
        return employeeEntityMapper.toDomain(employeeDao.save(employeeEntityMapper.toEntity(employee)));
    }

    @Override
    public boolean existsByIdAndNotDeleted(Long id) {
        return employeeDao.existsByIdAndNotDeleted(id);
    }
}
