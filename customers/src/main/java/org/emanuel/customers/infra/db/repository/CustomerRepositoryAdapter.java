package org.emanuel.customers.infra.db.repository;

import lombok.RequiredArgsConstructor;
import org.emanuel.customers.domain.db.repository.ICustomerRepository;
import org.emanuel.customers.domain.Customer;
import org.emanuel.customers.infra.db.mapper.CustomerEntityMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryAdapter implements ICustomerRepository {
    private final ICustomerDao customerDao;
    private final CustomerEntityMapper customerEntityMapper;

    @Override
    public Optional<Customer> findByIdAndNotDeleted(Long id) {
        return Optional.ofNullable(customerEntityMapper.toModel(customerDao.findByIdAndNotDeleted(id).orElse(null)));
    }

    @Override
    public List<Customer> findAllAndNotDeleted() {
        return customerDao.findAllAndNotDeleted().stream().map(customerEntityMapper::toModel).toList();
    }

    @Override
    public void save(Customer customer) {
        customerDao.save(customerEntityMapper.toEntity(customer));
    }

    @Override
    public boolean existsByIdAndNotDeleted(Long id) {
        return customerDao.existsByIdAndNotDeleted(id);
    }
}

