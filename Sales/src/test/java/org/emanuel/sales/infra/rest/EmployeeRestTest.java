package org.emanuel.sales.infra.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.emanuel.sales.application.IEmployeeService;
import org.emanuel.sales.domain.Employee;
import org.emanuel.sales.domain.exceptions.EmployeeNotExistException;
import org.emanuel.sales.infra.rest.dto.EmployeeGetDto;
import org.emanuel.sales.infra.rest.dto.EmployeeModifyDto;
import org.emanuel.sales.infra.rest.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeRest.class)
class EmployeeRestTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    IEmployeeService employeeService;

    @MockBean
    EmployeeMapper employeeMapper;

    private Employee employee() {
        return new Employee(1L, "Juan Perez", "12345678", "juan@mail.com", "1122334455");
    }

    private EmployeeGetDto employeeDto() {
        return new EmployeeGetDto(1L, "Juan Perez", "12345678", "juan@mail.com", "1122334455");
    }

    private EmployeeModifyDto modifyDto() {
        return new EmployeeModifyDto("Juan Perez", "12345678", "juan@mail.com", "1122334455");
    }

    // --- GET /{id} ---

    @Test
    void getEmployeeById_whenFound_returns200() throws Exception {
        var employee = employee();
        when(employeeService.getEmployeeById(1L)).thenReturn(employee);
        when(employeeMapper.toGetDto(employee)).thenReturn(employeeDto());

        mockMvc.perform(get("/api/v1/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Juan Perez"))
                .andExpect(jsonPath("$.dni").value("12345678"));
    }

    @Test
    void getEmployeeById_whenNotFound_returns404() throws Exception {
        when(employeeService.getEmployeeById(99L))
                .thenThrow(new EmployeeNotExistException("Employee not found with id: 99"));

        mockMvc.perform(get("/api/v1/employees/99"))
                .andExpect(status().isNotFound());
    }

    // --- GET / ---

    @Test
    void getAllEmployees_whenEmpty_returns204() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/employees"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllEmployees_whenNotEmpty_returns200WithList() throws Exception {
        var employee = employee();
        when(employeeService.getAllEmployees()).thenReturn(List.of(employee));
        when(employeeMapper.toGetDto(employee)).thenReturn(employeeDto());

        mockMvc.perform(get("/api/v1/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Juan Perez"));
    }

    // --- POST / ---

    @Test
    void addEmployee_returns201WithBody() throws Exception {
        var employee = employee();
        when(employeeService.addEmployee("Juan Perez", "12345678", "juan@mail.com", "1122334455"))
                .thenReturn(employee);
        when(employeeMapper.toGetDto(employee)).thenReturn(employeeDto());

        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyDto())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    // --- PUT /{id} ---

    @Test
    void updateEmployee_whenFound_returns204() throws Exception {
        doNothing().when(employeeService).updateEmployee(1L, "Juan Perez", "12345678", "juan@mail.com", "1122334455");

        mockMvc.perform(put("/api/v1/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyDto())))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateEmployee_whenNotFound_returns404() throws Exception {
        doThrow(new EmployeeNotExistException("Employee not found with id: 99"))
                .when(employeeService).updateEmployee(eq(99L), any(), any(), any(), any());

        mockMvc.perform(put("/api/v1/employees/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyDto())))
                .andExpect(status().isNotFound());
    }

    // --- DELETE /{id} ---

    @Test
    void deleteEmployee_whenFound_returns204() throws Exception {
        doNothing().when(employeeService).deleteEmployee(1L);

        mockMvc.perform(delete("/api/v1/employees/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteEmployee_whenNotFound_returns404() throws Exception {
        doThrow(new EmployeeNotExistException("Employee not found with id: 99"))
                .when(employeeService).deleteEmployee(99L);

        mockMvc.perform(delete("/api/v1/employees/99"))
                .andExpect(status().isNotFound());
    }
}
