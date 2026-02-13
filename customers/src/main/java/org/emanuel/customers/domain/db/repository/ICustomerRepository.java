package org.emanuel.customers.domain.db.repository;

import org.emanuel.customers.domain.Customer;
import java.util.List;
import java.util.Optional;

public interface ICustomerRepository {
    Optional<Customer> findByIdAndNotDeleted(Long id);
    List<Customer> findAllAndNotDeleted();
    void save(Customer customer);
    boolean existsByIdAndNotDeleted(Long id);
}

