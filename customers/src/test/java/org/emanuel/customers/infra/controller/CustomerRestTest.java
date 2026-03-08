package org.emanuel.customers.infra.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.emanuel.customers.application.ICustomerService;
import org.emanuel.customers.domain.Customer;
import org.emanuel.customers.domain.exceptions.CustomerNotExistDomainException;
import org.emanuel.customers.infra.controller.dto.CustomerGetDto;
import org.emanuel.customers.infra.controller.dto.CustomerModifyDto;
import org.emanuel.customers.infra.controller.mapper.CustomerMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerRest.class)
class CustomerRestTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ICustomerService customerService;

    @MockBean
    CustomerMapper customerMapper;

    private Customer customer() {
        return new Customer(1L, "Juan Perez", "12345678", "juan@mail.com", "1122334455");
    }

    private CustomerGetDto customerDto() {
        return new CustomerGetDto(1L, "Juan Perez", "12345678", "juan@mail.com", "1122334455");
    }

    private CustomerModifyDto modifyDto() {
        return new CustomerModifyDto("Juan Perez", "12345678", "juan@mail.com", "1122334455");
    }

    // --- GET /{id} ---

    @Test
    void getCustomerById_whenFound_returns200() throws Exception {
        var customer = customer();
        var dto = customerDto();
        when(customerService.getCustomerById(1L)).thenReturn(customer);
        when(customerMapper.toGetDto(customer)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Juan Perez"))
                .andExpect(jsonPath("$.dni").value("12345678"));
    }

    @Test
    void getCustomerById_whenNotFound_returns404() throws Exception {
        when(customerService.getCustomerById(99L))
                .thenThrow(new CustomerNotExistDomainException("Customer not found with id: 99"));

        mockMvc.perform(get("/api/v1/customers/99"))
                .andExpect(status().isNotFound());
    }

    // --- GET / ---

    @Test
    void getAllCustomers_whenEmpty_returns204() throws Exception {
        when(customerService.getAllCustomers()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllCustomers_whenNotEmpty_returns200WithList() throws Exception {
        var customer = customer();
        var dto = customerDto();
        when(customerService.getAllCustomers()).thenReturn(List.of(customer));
        when(customerMapper.toGetDto(customer)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Juan Perez"));
    }

    // --- POST / ---

    @Test
    void addCustomer_returns201() throws Exception {
        doNothing().when(customerService)
                .addCustomer("Juan Perez", "12345678", "juan@mail.com", "1122334455");

        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyDto())))
                .andExpect(status().isCreated());

        verify(customerService).addCustomer("Juan Perez", "12345678", "juan@mail.com", "1122334455");
    }

    // --- PUT /{id} ---

    @Test
    void updateCustomer_whenFound_returns204() throws Exception {
        doNothing().when(customerService)
                .updateCustomer(1L, "Juan Perez", "12345678", "juan@mail.com", "1122334455");

        mockMvc.perform(put("/api/v1/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyDto())))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateCustomer_whenNotFound_returns404() throws Exception {
        doThrow(new CustomerNotExistDomainException("Customer not found with id: 99"))
                .when(customerService).updateCustomer(eq(99L), any(), any(), any(), any());

        mockMvc.perform(put("/api/v1/customers/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyDto())))
                .andExpect(status().isNotFound());
    }

    // --- DELETE /{id} ---

    @Test
    void deleteCustomer_whenFound_returns204() throws Exception {
        doNothing().when(customerService).deleteCustomer(1L);

        mockMvc.perform(delete("/api/v1/customers/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteCustomer_whenNotFound_returns404() throws Exception {
        doThrow(new CustomerNotExistDomainException("Customer not found with id: 99"))
                .when(customerService).deleteCustomer(99L);

        mockMvc.perform(delete("/api/v1/customers/99"))
                .andExpect(status().isNotFound());
    }
}
