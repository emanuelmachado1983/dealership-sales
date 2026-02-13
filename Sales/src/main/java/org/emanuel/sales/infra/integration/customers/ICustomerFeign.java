package org.emanuel.sales.infra.integration.customers;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "customers-service", url = "${customers.service.url}")
public interface ICustomerFeign {
    @GetMapping("/api/v1/customers/{customerId}")
    Map<String, Object> findById(@PathVariable Long customerId);
}
