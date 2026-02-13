package org.emanuel.sales.infra.integration.customers;

import feign.FeignException;
import org.emanuel.sales.application.integration.customer.ICustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements ICustomerService {
    private final ICustomerFeign customerFeign;

    public CustomerServiceImpl(ICustomerFeign customerFeign) {
        this.customerFeign = customerFeign;
    }

    @Override
    public Boolean existsById(Long customerId) {
        try {
            customerFeign.findById(customerId);
            return true;
        } catch (FeignException.NotFound e) {
            return false;
        }
    }
}
