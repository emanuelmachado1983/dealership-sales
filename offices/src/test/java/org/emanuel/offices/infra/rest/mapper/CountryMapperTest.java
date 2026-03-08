package org.emanuel.offices.infra.rest.mapper;

import org.emanuel.offices.domain.Country;
import org.emanuel.offices.infra.rest.dto.CountryGetDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CountryMapperTest {

    private final CountryMapper mapper = new CountryMapper();

    @Test
    void toGetDto_mapsAllFields() {
        var country = new Country(1L, "Argentina");

        var dto = mapper.toGetDto(country);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("Argentina");
    }

    @Test
    void toModel_mapsAllFields() {
        var dto = new CountryGetDto(2L, "Brasil");

        var model = mapper.toModel(dto);

        assertThat(model.getId()).isEqualTo(2L);
        assertThat(model.getName()).isEqualTo("Brasil");
    }
}
