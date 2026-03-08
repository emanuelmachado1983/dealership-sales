package org.emanuel.customers.infra.grpc.customer;

import io.grpc.stub.StreamObserver;
import org.emanuel.customers.application.ICustomerService;
import org.emanuel.customers.domain.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerGrpcServiceTest {

    @Mock
    ICustomerService customerService;

    @InjectMocks
    CustomerGrpcService customerGrpcService;

    @Test
    void getById_whenCustomerExists_returnsCustomerMinimalAndCompletes() {
        var customer = new Customer(1L, "Juan Perez", "12345678", "juan@mail.com", "1122334455");
        when(customerService.getCustomerById(1L)).thenReturn(customer);

        var request = IdRequest.newBuilder().setId(1L).build();

        @SuppressWarnings("unchecked")
        StreamObserver<CustomerMinimal> observer = mock(StreamObserver.class);

        customerGrpcService.getById(request, observer);

        var captor = ArgumentCaptor.forClass(CustomerMinimal.class);
        verify(observer).onNext(captor.capture());
        verify(observer).onCompleted();
        verifyNoMoreInteractions(observer);

        var response = captor.getValue();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Juan Perez");
        assertThat(response.getDni()).isEqualTo("12345678");
        assertThat(response.getEmail()).isEqualTo("juan@mail.com");
        assertThat(response.getPhone()).isEqualTo("1122334455");
    }

    @Test
    void getById_propagatesExceptionFromService() {
        when(customerService.getCustomerById(99L))
                .thenThrow(new RuntimeException("Customer not found"));

        var request = IdRequest.newBuilder().setId(99L).build();

        @SuppressWarnings("unchecked")
        StreamObserver<CustomerMinimal> observer = mock(StreamObserver.class);

        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> customerGrpcService.getById(request, observer))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Customer not found");

        verify(observer, never()).onNext(any());
        verify(observer, never()).onCompleted();
    }
}
