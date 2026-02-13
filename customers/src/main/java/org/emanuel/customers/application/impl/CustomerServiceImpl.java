package org.emanuel.customers.application.impl;


import lombok.RequiredArgsConstructor;
import org.emanuel.customers.application.ICustomerService;
import org.emanuel.customers.domain.db.repository.ICustomerRepository;
import org.emanuel.customers.domain.Customer;
import org.emanuel.customers.domain.exceptions.CustomerNotExistDomainException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {
    private final ICustomerRepository customerRepository;


    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new CustomerNotExistDomainException("Customer not found with id: " + id));
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAllAndNotDeleted();
    }

    @Override
    public void addCustomer(String name, String dni, String email, String phone) {
        customerRepository.save(new Customer(null, name, dni, email, phone));
    }

    @Override
    public void updateCustomer(Long idCustomer, String name, String dni, String email, String phone) {
        if (customerRepository.existsByIdAndNotDeleted(idCustomer)) {
            customerRepository.save(new Customer(idCustomer, name, dni, email, phone));
        } else {
            throw new CustomerNotExistDomainException("Customer not found with id: " + idCustomer);
        }
    }

    @Override
    public void deleteCustomer(Long id) {
        var customer = customerRepository.findByIdAndNotDeleted(id).orElseThrow(() -> new CustomerNotExistDomainException("Customer not found with id: " + id));
        customer.setDeletedAt(LocalDateTime.now());
        customerRepository.save(customer);
    }
}
