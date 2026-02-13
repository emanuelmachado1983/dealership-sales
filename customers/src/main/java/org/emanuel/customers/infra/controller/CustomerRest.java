package org.emanuel.customers.infra.controller;

import lombok.RequiredArgsConstructor;
import org.emanuel.customers.application.ICustomerService;
import org.emanuel.customers.domain.exceptions.CustomerNotExistDomainException;
import org.emanuel.customers.infra.controller.dto.CustomerGetDto;
import org.emanuel.customers.infra.controller.dto.CustomerModifyDto;
import org.emanuel.customers.infra.controller.mapper.CustomerMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/customers")
public class CustomerRest {
    private final ICustomerService customerService;
    private final CustomerMapper customerMapper;

    @GetMapping("/{id}")
    public ResponseEntity<CustomerGetDto> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerMapper.toGetDto(customerService.getCustomerById(id)));
    }

    @GetMapping("")
    public ResponseEntity<List<CustomerGetDto>> getAllCustomers() {
        var customers = customerService.getAllCustomers();
        if (customers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(customers.stream().map(customerMapper::toGetDto).toList());
    }

    @PostMapping("")
    public ResponseEntity<Void> addCustomer(@RequestBody CustomerModifyDto customer) {
        customerService.addCustomer(
                customer.getName(), customer.getDni(), customer.getEmail(), customer.getPhone());
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCustomer(@PathVariable Long id, @RequestBody CustomerModifyDto customerModifyDto) {
        customerService.updateCustomer(id,
                customerModifyDto.getName(),
                customerModifyDto.getDni(),
                customerModifyDto.getEmail(),
                customerModifyDto.getPhone());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }


}
