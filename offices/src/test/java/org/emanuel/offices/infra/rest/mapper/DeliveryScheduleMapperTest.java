package org.emanuel.offices.infra.rest.mapper;

import org.emanuel.offices.domain.DeliverySchedule;
import org.emanuel.offices.domain.Office;
import org.emanuel.offices.domain.TypeOffice;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class DeliveryScheduleMapperTest {

    private final OfficeMapper officeMapper = new OfficeMapper();
    private final DeliveryScheduleMapper mapper = new DeliveryScheduleMapper(officeMapper);

    private Office office(Long id) {
        return Office.builder()
                .id(id)
                .idCountry(1L).idProvince(10L).idLocality(100L)
                .address("Calle Falsa 123")
                .name("Oficina " + id)
                .openingDate(LocalDateTime.of(2020, 1, 1, 0, 0))
                .typeOffice(new TypeOffice(2L, "Sucursal"))
                .build();
    }

    @Test
    void toDto_mapsAllFields() {
        var schedule = DeliverySchedule.builder()
                .id(1L)
                .officeFrom(office(1L))
                .officeTo(office(2L))
                .days(3)
                .build();

        var dto = mapper.toDto(schedule);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getDays()).isEqualTo(3);
        assertThat(dto.getOfficeFrom().getId()).isEqualTo(1L);
        assertThat(dto.getOfficeTo().getId()).isEqualTo(2L);
    }
}
