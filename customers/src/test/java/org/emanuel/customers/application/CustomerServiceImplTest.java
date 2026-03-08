package org.emanuel.customers.application;

import org.emanuel.customers.application.impl.CustomerServiceImpl;
import org.emanuel.customers.domain.Customer;
import org.emanuel.customers.domain.db.repository.ICustomerRepository;
import org.emanuel.customers.domain.exceptions.CustomerNotExistDomainException;
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
class CustomerServiceImplTest {

    @Mock
    ICustomerRepository customerRepository;

    @InjectMocks
    CustomerServiceImpl customerService;

    private Customer customer() {
        return new Customer(1L, "Juan Perez", "12345678", "juan@mail.com", "1122334455");
    }

    // --- getCustomerById ---

    @Test
    void getCustomerById_whenCustomerExists_returnsCustomer() {
        var customer = customer();
        when(customerRepository.findByIdAndNotDeleted(1L)).thenReturn(Optional.of(customer));

        var result = customerService.getCustomerById(1L);

        assertThat(result).isEqualTo(customer);
    }

    @Test
    void getCustomerById_whenNotFound_throwsException() {
        when(customerRepository.findByIdAndNotDeleted(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.getCustomerById(99L))
                .isInstanceOf(CustomerNotExistDomainException.class)
                .hasMessageContaining("99");
    }

    // --- getAllCustomers ---

    @Test
    void getAllCustomers_returnsListFromRepository() {
        var customers = List.of(customer());
        when(customerRepository.findAllAndNotDeleted()).thenReturn(customers);

        var result = customerService.getAllCustomers();

        assertThat(result).isEqualTo(customers);
    }

    // --- addCustomer ---

    @Test
    void addCustomer_savesCustomerWithCorrectFields() {
        customerService.addCustomer("Juan Perez", "12345678", "juan@mail.com", "1122334455");

        var captor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(captor.capture());
        var saved = captor.getValue();
        assertThat(saved.getId()).isNull();
        assertThat(saved.getName()).isEqualTo("Juan Perez");
        assertThat(saved.getDni()).isEqualTo("12345678");
        assertThat(saved.getEmail()).isEqualTo("juan@mail.com");
        assertThat(saved.getPhone()).isEqualTo("1122334455");
    }

    // --- updateCustomer ---

    @Test
    void updateCustomer_whenCustomerExists_savesWithUpdatedFields() {
        when(customerRepository.existsByIdAndNotDeleted(1L)).thenReturn(true);

        customerService.updateCustomer(1L, "Nuevo Nombre", "99999999", "nuevo@mail.com", "9988776655");

        var captor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(captor.capture());
        var saved = captor.getValue();
        assertThat(saved.getId()).isEqualTo(1L);
        assertThat(saved.getName()).isEqualTo("Nuevo Nombre");
        assertThat(saved.getDni()).isEqualTo("99999999");
        assertThat(saved.getEmail()).isEqualTo("nuevo@mail.com");
        assertThat(saved.getPhone()).isEqualTo("9988776655");
    }

    @Test
    void updateCustomer_whenNotFound_throwsException() {
        when(customerRepository.existsByIdAndNotDeleted(99L)).thenReturn(false);

        assertThatThrownBy(() -> customerService.updateCustomer(99L, "Nombre", "dni", "email", "phone"))
                .isInstanceOf(CustomerNotExistDomainException.class)
                .hasMessageContaining("99");

        verify(customerRepository, never()).save(any());
    }

    // --- deleteCustomer ---

    @Test
    void deleteCustomer_whenCustomerExists_setsDeletedAt() {
        var customer = customer();
        when(customerRepository.findByIdAndNotDeleted(1L)).thenReturn(Optional.of(customer));

        customerService.deleteCustomer(1L);

        var captor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(captor.capture());
        assertThat(captor.getValue().getDeletedAt()).isNotNull();
    }

    @Test
    void deleteCustomer_whenNotFound_throwsException() {
        when(customerRepository.findByIdAndNotDeleted(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.deleteCustomer(99L))
                .isInstanceOf(CustomerNotExistDomainException.class)
                .hasMessageContaining("99");

        verify(customerRepository, never()).save(any());
    }
}
