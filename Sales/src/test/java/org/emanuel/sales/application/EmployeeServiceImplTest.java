package org.emanuel.sales.application;

import org.emanuel.sales.application.impl.EmployeeServiceImpl;
import org.emanuel.sales.domain.Employee;
import org.emanuel.sales.domain.db.repository.EmployeeRepository;
import org.emanuel.sales.domain.exceptions.EmployeeNotExistException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeServiceImpl employeeService;

    private Employee employee() {
        return new Employee(1L, "Juan Perez", "12345678", "juan@mail.com", "1122334455");
    }

    // --- getEmployeeById ---

    @Test
    void getEmployeeById_whenExists_returnsEmployee() {
        var employee = employee();
        when(employeeRepository.findByIdAndNotDeleted(1L)).thenReturn(Optional.of(employee));

        var result = employeeService.getEmployeeById(1L);

        assertThat(result).isEqualTo(employee);
    }

    @Test
    void getEmployeeById_whenNotFound_throwsException() {
        when(employeeRepository.findByIdAndNotDeleted(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.getEmployeeById(99L))
                .isInstanceOf(EmployeeNotExistException.class)
                .hasMessageContaining("99");
    }

    // --- getAllEmployees ---

    @Test
    void getAllEmployees_returnsListFromRepository() {
        var employees = List.of(employee());
        when(employeeRepository.findAllAndNotDeleted()).thenReturn(employees);

        var result = employeeService.getAllEmployees();

        assertThat(result).isEqualTo(employees);
    }

    // --- addEmployee ---

    @Test
    void addEmployee_savesAndReturnsRefetchedEmployee() {
        var savedEmployee = employee();
        when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee);
        when(employeeRepository.findByIdAndNotDeleted(1L)).thenReturn(Optional.of(savedEmployee));

        var result = employeeService.addEmployee("Juan Perez", "12345678", "juan@mail.com", "1122334455");

        var captor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(captor.capture());
        assertThat(captor.getValue().getId()).isNull();
        assertThat(captor.getValue().getName()).isEqualTo("Juan Perez");
        assertThat(result).isEqualTo(savedEmployee);
    }

    // --- updateEmployee ---

    @Test
    void updateEmployee_whenExists_savesWithUpdatedFields() {
        when(employeeRepository.existsByIdAndNotDeleted(1L)).thenReturn(true);

        employeeService.updateEmployee(1L, "Nuevo Nombre", "99999999", "nuevo@mail.com", "9988776655");

        var captor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(captor.capture());
        var saved = captor.getValue();
        assertThat(saved.getId()).isEqualTo(1L);
        assertThat(saved.getName()).isEqualTo("Nuevo Nombre");
        assertThat(saved.getDni()).isEqualTo("99999999");
    }

    @Test
    void updateEmployee_whenNotFound_throwsException() {
        when(employeeRepository.existsByIdAndNotDeleted(99L)).thenReturn(false);

        assertThatThrownBy(() -> employeeService.updateEmployee(99L, "X", "X", "X", "X"))
                .isInstanceOf(EmployeeNotExistException.class)
                .hasMessageContaining("99");

        verify(employeeRepository, never()).save(any());
    }

    // --- deleteEmployee ---

    @Test
    void deleteEmployee_whenExists_setsDeletedAt() {
        var employee = employee();
        when(employeeRepository.findByIdAndNotDeleted(1L)).thenReturn(Optional.of(employee));

        employeeService.deleteEmployee(1L);

        var captor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(captor.capture());
        assertThat(captor.getValue().getDeletedAt()).isNotNull();
    }

    @Test
    void deleteEmployee_whenNotFound_throwsException() {
        when(employeeRepository.findByIdAndNotDeleted(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.deleteEmployee(99L))
                .isInstanceOf(EmployeeNotExistException.class)
                .hasMessageContaining("99");

        verify(employeeRepository, never()).save(any());
    }
}
