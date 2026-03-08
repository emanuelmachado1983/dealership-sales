package org.emanuel.vehicles.infra.rest.mapper;

import org.emanuel.vehicles.domain.ModelVehicle;
import org.emanuel.vehicles.infra.rest.dto.ModelVehicleModifyDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ModelVehicleMapperTest {

    private final ModelVehicleMapper mapper = new ModelVehicleMapper();

    @Test
    void toGetDto_mapsAllFields() {
        var model = new ModelVehicle(1L, "Toyota", "Corolla", 2023L, 25000.0);

        var dto = mapper.toGetDto(model);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getBrand()).isEqualTo("Toyota");
        assertThat(dto.getModel()).isEqualTo("Corolla");
        assertThat(dto.getYear()).isEqualTo(2023L);
        assertThat(dto.getPrice()).isEqualTo(25000.0);
    }

    @Test
    void toModel_withId_mapsAllFields() {
        var dto = ModelVehicleModifyDto.builder()
                .brand("Honda").model("Civic").year(2022L).price(20000.0).build();

        var model = mapper.toModel(5L, dto);

        assertThat(model.getId()).isEqualTo(5L);
        assertThat(model.getBrand()).isEqualTo("Honda");
        assertThat(model.getModel()).isEqualTo("Civic");
        assertThat(model.getYear()).isEqualTo(2022L);
        assertThat(model.getPrice()).isEqualTo(20000.0);
    }

    @Test
    void toModel_withNullId_setsNullId() {
        var dto = ModelVehicleModifyDto.builder()
                .brand("Ford").model("Focus").year(2021L).price(18000.0).build();

        var model = mapper.toModel(null, dto);

        assertThat(model.getId()).isNull();
    }
}
