package org.emanuel.sales.infra.rest;

import lombok.RequiredArgsConstructor;
import org.emanuel.sales.application.IEmployeeService;
import org.emanuel.sales.infra.rest.dto.EmployeeGetDto;
import org.emanuel.sales.infra.rest.dto.EmployeeModifyDto;
import org.emanuel.sales.domain.exceptions.EmployeeException;
import org.emanuel.sales.domain.exceptions.EmployeeNotExistException;
import org.emanuel.sales.infra.rest.mapper.EmployeeMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/employees")
public class EmployeeRest {
    private final IEmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeGetDto> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeMapper.toGetDto(employeeService.getEmployeeById(id)));
    }

    @GetMapping("")
    public ResponseEntity<List<EmployeeGetDto>> getAllEmployees() {
        var employees = employeeService.getAllEmployees();
        if (employees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(employees.stream().map(employeeMapper::toGetDto).toList());
    }

    @PostMapping("")
    public ResponseEntity<EmployeeGetDto> addEmployee(@RequestBody EmployeeModifyDto employee){
        var saved = employeeService.addEmployee(
                employee.getName(), employee.getDni(), employee.getEmail(), employee.getPhone());
        return ResponseEntity.status(201).body(employeeMapper.toGetDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEmployee(@PathVariable Long id, @RequestBody EmployeeModifyDto employeeModifyDto){
        employeeService.updateEmployee(id,
                employeeModifyDto.getName(),
                employeeModifyDto.getDni(),
                employeeModifyDto.getEmail(),
                employeeModifyDto.getPhone());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

}
