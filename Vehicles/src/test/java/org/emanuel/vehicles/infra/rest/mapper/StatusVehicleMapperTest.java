package org.emanuel.vehicles.infra.rest.mapper;

import org.emanuel.vehicles.domain.StatusVehicle;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StatusVehicleMapperTest {

    private final StatusVehicleMapper mapper = new StatusVehicleMapper();

    @Test
    void toGetDto_mapsAllFields() {
        var status = new StatusVehicle(1L, "Disponible");

        var dto = mapper.toGetDto(status);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("Disponible");
    }
}
