package org.emanuel.sales.infra.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.emanuel.sales.application.ISaleService;
import org.emanuel.sales.domain.Employee;
import org.emanuel.sales.domain.Sale;
import org.emanuel.sales.domain.SaleStatus;
import org.emanuel.sales.domain.exceptions.SaleBadRequestException;
import org.emanuel.sales.domain.exceptions.SaleNotExistException;
import org.emanuel.sales.infra.rest.dto.EmployeeGetDto;
import org.emanuel.sales.infra.rest.dto.SaleGetDto;
import org.emanuel.sales.infra.rest.dto.SaleModifyDto;
import org.emanuel.sales.infra.rest.dto.SalePostDto;
import org.emanuel.sales.infra.rest.dto.SaleStatusDto;
import org.emanuel.sales.infra.rest.mapper.SaleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SaleRest.class)
class SaleRestTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ISaleService saleService;

    @MockBean
    SaleMapper saleMapper;

    private static final LocalDateTime SALE_DATE = LocalDateTime.of(2024, 6, 15, 12, 0);

    private Sale sale() {
        return Sale.builder()
                .id(1L)
                .employee(new Employee(1L, "Juan Perez", "12345678", "juan@mail.com", "1122334455"))
                .customerId(2L)
                .vehicleId(10L)
                .ammount(25000.0)
                .date(SALE_DATE)
                .warrantyYears(3)
                .saleStatus(new SaleStatus(1L, "Pendiente"))
                .deliveryDays(5)
                .officeSeller(5L)
                .build();
    }

    private SaleGetDto saleDto() {
        return SaleGetDto.builder()
                .id(1L)
                .employeeGetDto(new EmployeeGetDto(1L, "Juan Perez", "12345678", "juan@mail.com", "1122334455"))
                .customerId(2L)
                .vehicleId(10L)
                .ammount(25000.0)
                .date(SALE_DATE)
                .warrantyYears(3)
                .saleStatusDto(new SaleStatusDto(1L, "Pendiente"))
                .deliveryDate(SALE_DATE.plusDays(5))
                .build();
    }

    private SalePostDto postDto() {
        return SalePostDto.builder()
                .employeeId(1L).customerId(2L).vehicleId(10L)
                .date(null).officeSellerId(5L)
                .build();
    }

    // --- GET /{id} ---

    @Test
    void getSaleById_whenFound_returns200() throws Exception {
        var sale = sale();
        when(saleService.getSaleById(1L)).thenReturn(sale);
        when(saleMapper.toGetDto(sale)).thenReturn(saleDto());

        mockMvc.perform(get("/api/v1/sales/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customerId").value(2))
                .andExpect(jsonPath("$.vehicleId").value(10));
    }

    @Test
    void getSaleById_whenNotFound_returns404() throws Exception {
        when(saleService.getSaleById(99L))
                .thenThrow(new SaleNotExistException("Sale not found with id: 99"));

        mockMvc.perform(get("/api/v1/sales/99"))
                .andExpect(status().isNotFound());
    }

    // --- GET / ---

    @Test
    void getAllSales_whenEmpty_returns204() throws Exception {
        when(saleService.getAllSales(any(), any(), any(), any(), any())).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/sales"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllSales_whenNotEmpty_returns200WithList() throws Exception {
        var sale = sale();
        when(saleService.getAllSales(any(), any(), any(), any(), any())).thenReturn(List.of(sale));
        when(saleMapper.toGetDto(sale)).thenReturn(saleDto());

        mockMvc.perform(get("/api/v1/sales"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void getAllSales_withQueryParams_passesFiltersToService() throws Exception {
        when(saleService.getAllSales(2L, 1L, null, null, 10L)).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/sales?customerId=2&employeeId=1&vehicleId=10"))
                .andExpect(status().isNoContent());

        verify(saleService).getAllSales(2L, 1L, null, null, 10L);
    }

    // --- POST / ---

    @Test
    void addSale_whenValid_returns201WithBody() throws Exception {
        var sale = sale();
        when(saleService.addSale(any(), any(), any(), any(), any())).thenReturn(sale);
        when(saleMapper.toGetDto(sale)).thenReturn(saleDto());

        mockMvc.perform(post("/api/v1/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void addSale_whenServiceThrows_returns404() throws Exception {
        when(saleService.addSale(any(), any(), any(), any(), any()))
                .thenThrow(new SaleBadRequestException("Vehicle not available"));

        mockMvc.perform(post("/api/v1/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto())))
                .andExpect(status().isNotFound());
    }

    // --- PATCH /{id} ---

    @Test
    void patchSale_whenValid_returns204() throws Exception {
        var body = SaleModifyDto.builder().idStatus(2L).build();
        doNothing().when(saleService).patchSale(any(), any(), any(), any());

        mockMvc.perform(patch("/api/v1/sales/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isNoContent());
    }

    @Test
    void patchSale_whenSaleNotFound_returns404() throws Exception {
        var body = SaleModifyDto.builder().idStatus(2L).build();
        doThrow(new SaleNotExistException("Sale not found with id: 99"))
                .when(saleService).patchSale(any(), any(), any(), any());

        mockMvc.perform(patch("/api/v1/sales/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isNotFound());
    }
}
