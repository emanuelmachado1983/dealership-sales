package org.emanuel.maintain.infra.rest.mapper;

import org.emanuel.maintain.domain.MechanicalRepair;
import org.emanuel.maintain.infra.rest.dto.MechanicalRepairPostDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class MechanicalRepairMapperTest {

    private final MechanicalRepairMapper mapper = new MechanicalRepairMapper();

    private static final LocalDateTime ENTER = LocalDateTime.of(2024, 1, 1, 9, 0);
    private static final LocalDateTime ESTIMATED = LocalDateTime.of(2024, 1, 15, 9, 0);
    private static final LocalDateTime DELIVERED = LocalDateTime.of(2024, 1, 10, 9, 0);

    private MechanicalRepair repair() {
        return MechanicalRepair.builder()
                .id(1L)
                .enterDate(ENTER)
                .deliveryDateEstimated(ESTIMATED)
                .deliveryDate(DELIVERED)
                .vehicleId(10L)
                .kmUnit(50000L)
                .usingWarranty(true)
                .build();
    }

    @Test
    void toGetDto_mapsAllFields() {
        var dto = mapper.toGetDto(repair());

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getEnterDate()).isEqualTo(ENTER);
        assertThat(dto.getDeliveryDateEstimated()).isEqualTo(ESTIMATED);
        assertThat(dto.getDeliveryDate()).isEqualTo(DELIVERED);
        assertThat(dto.getVehicleId()).isEqualTo(10L);
        assertThat(dto.getKmUnit()).isEqualTo(50000L);
        assertThat(dto.getUsingWarranty()).isTrue();
    }

    @Test
    void toGetDto_whenDeliveryDateAndUsingWarrantyNull_mapsWithNulls() {
        var repair = MechanicalRepair.builder()
                .id(2L).enterDate(ENTER).deliveryDateEstimated(ESTIMATED)
                .deliveryDate(null).vehicleId(10L).kmUnit(30000L).usingWarranty(null)
                .build();

        var dto = mapper.toGetDto(repair);

        assertThat(dto.getDeliveryDate()).isNull();
        assertThat(dto.getUsingWarranty()).isNull();
    }

    @Test
    void toBodyPostDto_mapsId() {
        var repair = MechanicalRepair.builder().id(5L).build();

        var dto = mapper.toBodyPostDto(repair);

        assertThat(dto.getId()).isEqualTo(5L);
    }

    @Test
    void postToModel_withId_mapsAllFields() {
        var postDto = new MechanicalRepairPostDto();
        postDto.setEnterDate(ENTER);
        postDto.setDeliveryDateEstimated(ESTIMATED);
        postDto.setVehicleId(10L);
        postDto.setKmUnit(50000L);

        var model = mapper.postToModel(1L, postDto);

        assertThat(model.getId()).isEqualTo(1L);
        assertThat(model.getEnterDate()).isEqualTo(ENTER);
        assertThat(model.getDeliveryDateEstimated()).isEqualTo(ESTIMATED);
        assertThat(model.getDeliveryDate()).isNull();
        assertThat(model.getVehicleId()).isEqualTo(10L);
        assertThat(model.getKmUnit()).isEqualTo(50000L);
        assertThat(model.getUsingWarranty()).isNull();
    }

    @Test
    void postToModel_withNullId_setsIdNull() {
        var postDto = new MechanicalRepairPostDto();
        postDto.setVehicleId(10L);
        postDto.setKmUnit(10000L);

        var model = mapper.postToModel(null, postDto);

        assertThat(model.getId()).isNull();
    }
}
