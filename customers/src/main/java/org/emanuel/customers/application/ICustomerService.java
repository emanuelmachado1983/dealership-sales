package org.emanuel.customers.application;


import org.emanuel.customers.domain.Customer;

import java.util.List;

public interface ICustomerService {
    Customer getCustomerById(Long id);

    List<Customer> getAllCustomers();

    void addCustomer(String name, String dni, String email, String phone);

    void updateCustomer(Long idCustomer, String name, String dni, String email, String phone);

    void deleteCustomer(Long id);
}
