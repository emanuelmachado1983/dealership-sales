package org.emanuel.offices.infra.rest.mapper;

import org.emanuel.offices.domain.Country;
import org.emanuel.offices.domain.Province;
import org.emanuel.offices.infra.rest.dto.ProvinceGetDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProvinceMapperTest {

    private final ProvinceMapper mapper = new ProvinceMapper();

    @Test
    void toGetDto_mapsAllFields() {
        var country = new Country(1L, "Argentina");
        var province = new Province(10L, "Buenos Aires", country);

        var dto = mapper.toGetDto(province);

        assertThat(dto.getId()).isEqualTo(10L);
        assertThat(dto.getName()).isEqualTo("Buenos Aires");
        assertThat(dto.getCountryId()).isEqualTo(1L);
    }

    @Test
    void toModel_mapsAllFields() {
        var dto = new ProvinceGetDto(10L, "Buenos Aires", 1L);

        var model = mapper.toModel(dto);

        assertThat(model.getId()).isEqualTo(10L);
        assertThat(model.getName()).isEqualTo("Buenos Aires");
        assertThat(model.getCountry().getId()).isEqualTo(1L);
    }
}
