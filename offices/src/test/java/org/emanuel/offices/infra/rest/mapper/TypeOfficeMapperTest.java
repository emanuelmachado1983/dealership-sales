package org.emanuel.offices.infra.rest.mapper;

import org.emanuel.offices.domain.TypeOffice;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TypeOfficeMapperTest {

    private final TypeOfficeMapper mapper = new TypeOfficeMapper();

    @Test
    void toDto_mapsAllFields() {
        var typeOffice = new TypeOffice(2L, "Sucursal");

        var dto = mapper.toDto(typeOffice);

        assertThat(dto.getId()).isEqualTo(2L);
        assertThat(dto.getName()).isEqualTo("Sucursal");
    }
}
