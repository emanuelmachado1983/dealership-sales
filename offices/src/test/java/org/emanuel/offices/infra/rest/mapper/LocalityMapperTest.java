package org.emanuel.offices.infra.rest.mapper;

import org.emanuel.offices.domain.Locality;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LocalityMapperTest {

    private final LocalityMapper mapper = new LocalityMapper();

    @Test
    void toGetDto_mapsAllFields() {
        var locality = Locality.builder().id(100L).name("La Plata").build();

        var dto = mapper.toGetDto(locality);

        assertThat(dto.getId()).isEqualTo(100L);
        assertThat(dto.getName()).isEqualTo("La Plata");
    }
}
