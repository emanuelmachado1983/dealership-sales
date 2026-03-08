package org.emanuel.offices.infra.rest.mapper;

import org.emanuel.offices.domain.Office;
import org.emanuel.offices.domain.TypeOffice;
import org.emanuel.offices.infra.rest.dto.OfficeModifyDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class OfficeMapperTest {

    private final OfficeMapper mapper = new OfficeMapper();

    private Office officeWithType() {
        return Office.builder()
                .id(10L)
                .idCountry(1L)
                .idProvince(10L)
                .idLocality(100L)
                .address("Av. Siempreviva 742")
                .name("Sucursal Springfield")
                .openingDate(LocalDateTime.of(2020, 1, 1, 0, 0))
                .typeOffice(new TypeOffice(2L, "Sucursal"))
                .build();
    }

    @Test
    void toDto_mapsAllFields() {
        var office = officeWithType();

        var dto = mapper.toDto(office);

        assertThat(dto.getId()).isEqualTo(10L);
        assertThat(dto.getIdCountry()).isEqualTo(1L);
        assertThat(dto.getIdProvince()).isEqualTo(10L);
        assertThat(dto.getIdLocality()).isEqualTo(100L);
        assertThat(dto.getAddress()).isEqualTo("Av. Siempreviva 742");
        assertThat(dto.getName()).isEqualTo("Sucursal Springfield");
        assertThat(dto.getOpeningDate()).isEqualTo(LocalDateTime.of(2020, 1, 1, 0, 0));
        assertThat(dto.getTypeOffice().getId()).isEqualTo(2L);
        assertThat(dto.getTypeOffice().getName()).isEqualTo("Sucursal");
    }

    @Test
    void toModel_mapsAllFields() {
        var dto = OfficeModifyDto.builder()
                .idCountry(1L)
                .idProvince(10L)
                .idLocality(100L)
                .address("Av. Siempreviva 742")
                .name("Sucursal Springfield")
                .openingDate(LocalDateTime.of(2020, 1, 1, 0, 0))
                .typeOfficeId(2L)
                .build();

        var model = mapper.toModel(5L, dto);

        assertThat(model.getId()).isEqualTo(5L);
        assertThat(model.getIdCountry()).isEqualTo(1L);
        assertThat(model.getIdProvince()).isEqualTo(10L);
        assertThat(model.getIdLocality()).isEqualTo(100L);
        assertThat(model.getAddress()).isEqualTo("Av. Siempreviva 742");
        assertThat(model.getName()).isEqualTo("Sucursal Springfield");
        assertThat(model.getTypeOffice().getId()).isEqualTo(2L);
    }

    @Test
    void toModel_withNullId_setsNullId() {
        var dto = OfficeModifyDto.builder()
                .idCountry(1L).idProvince(10L).idLocality(100L)
                .typeOfficeId(2L)
                .openingDate(LocalDateTime.now())
                .build();

        var model = mapper.toModel(null, dto);

        assertThat(model.getId()).isNull();
    }
}
