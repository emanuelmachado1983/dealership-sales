package org.emanuel.vehicles.infra.rest.mapper;

import org.emanuel.vehicles.domain.TypeVehicle;
import org.emanuel.vehicles.infra.rest.dto.TypeVehicleModifyDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TypeVehicleMapperTest {

    private final TypeVehicleMapper mapper = new TypeVehicleMapper();

    @Test
    void toGetDto_mapsAllFields() {
        var type = new TypeVehicle(1L, "Sedán", 3);

        var dto = mapper.toGetDto(type);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("Sedán");
        assertThat(dto.getWarrantyYears()).isEqualTo(3);
    }

    @Test
    void toModel_withId_mapsAllFields() {
        var dto = TypeVehicleModifyDto.builder().name("SUV").warrantyYears(5).build();

        var model = mapper.toModel(2L, dto);

        assertThat(model.getId()).isEqualTo(2L);
        assertThat(model.getName()).isEqualTo("SUV");
        assertThat(model.getWarrantyYears()).isEqualTo(5);
    }

    @Test
    void toModel_withNullId_setsNullId() {
        var dto = TypeVehicleModifyDto.builder().name("Pick-up").warrantyYears(2).build();

        var model = mapper.toModel(null, dto);

        assertThat(model.getId()).isNull();
    }
}
