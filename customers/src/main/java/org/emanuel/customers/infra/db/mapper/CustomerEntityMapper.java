package org.emanuel.customers.infra.db.mapper;

import org.emanuel.customers.domain.Customer;
import org.emanuel.customers.infra.db.entity.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerEntityMapper {
    public Customer toModel(CustomerEntity customerEntity) {
        if (customerEntity == null) {
            return null;
        }
        return new Customer(
                customerEntity.getId(),
                customerEntity.getName(),
                customerEntity.getDni(),
                customerEntity.getEmail(),
                customerEntity.getPhone(),
                customerEntity.getDeletedAt()
        );
    }

    public CustomerEntity toEntity(Customer customer) {
        if (customer == null) {
            return null;
        }
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(customer.getId());
        customerEntity.setName(customer.getName());
        customerEntity.setDni(customer.getDni());
        customerEntity.setEmail(customer.getEmail());
        customerEntity.setPhone(customer.getPhone());
        customerEntity.setDeletedAt(customer.getDeletedAt());
        return customerEntity;
    }
}
