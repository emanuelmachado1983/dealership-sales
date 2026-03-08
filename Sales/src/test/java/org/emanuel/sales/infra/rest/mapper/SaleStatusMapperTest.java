package org.emanuel.sales.infra.rest.mapper;

import org.emanuel.sales.domain.SaleStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SaleStatusMapperTest {

    private final SaleStatusMapper mapper = new SaleStatusMapper();

    @Test
    void toDto_mapsAllFields() {
        var status = new SaleStatus(2L, "Completada");

        var dto = mapper.toDto(status);

        assertThat(dto.getId()).isEqualTo(2L);
        assertThat(dto.getName()).isEqualTo("Completada");
    }
}
