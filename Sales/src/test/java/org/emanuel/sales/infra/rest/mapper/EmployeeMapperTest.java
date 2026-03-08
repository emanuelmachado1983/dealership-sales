package org.emanuel.sales.infra.rest.mapper;

import org.emanuel.sales.domain.Employee;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmployeeMapperTest {

    private final EmployeeMapper mapper = new EmployeeMapper();

    @Test
    void toGetDto_mapsAllFields() {
        var employee = new Employee(1L, "Juan Perez", "12345678", "juan@mail.com", "1122334455");

        var dto = mapper.toGetDto(employee);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("Juan Perez");
        assertThat(dto.getDni()).isEqualTo("12345678");
        assertThat(dto.getEmail()).isEqualTo("juan@mail.com");
        assertThat(dto.getPhone()).isEqualTo("1122334455");
    }

    @Test
    void toGetDto_whenOptionalFieldsNull_mapsWithNulls() {
        var employee = new Employee(2L, "Solo Nombre", "99999999", null, null);

        var dto = mapper.toGetDto(employee);

        assertThat(dto.getId()).isEqualTo(2L);
        assertThat(dto.getName()).isEqualTo("Solo Nombre");
        assertThat(dto.getEmail()).isNull();
        assertThat(dto.getPhone()).isNull();
    }
}
