package org.emanuel.customers.infra.controller.mapper;

import org.emanuel.customers.domain.Customer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerMapperTest {

    private final CustomerMapper mapper = new CustomerMapper();

    @Test
    void toGetDto_mapsAllFields() {
        var customer = new Customer(1L, "Juan Perez", "12345678", "juan@mail.com", "1122334455");

        var dto = mapper.toGetDto(customer);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("Juan Perez");
        assertThat(dto.getDni()).isEqualTo("12345678");
        assertThat(dto.getEmail()).isEqualTo("juan@mail.com");
        assertThat(dto.getPhone()).isEqualTo("1122334455");
    }

    @Test
    void toGetDto_whenOptionalFieldsNull_mapsWithNulls() {
        var customer = new Customer(2L, "Sin Email", "99999999", null, null);

        var dto = mapper.toGetDto(customer);

        assertThat(dto.getId()).isEqualTo(2L);
        assertThat(dto.getName()).isEqualTo("Sin Email");
        assertThat(dto.getEmail()).isNull();
        assertThat(dto.getPhone()).isNull();
    }
}
