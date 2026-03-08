package org.emanuel.vehicles.infra.rest.mapper;

import org.emanuel.vehicles.domain.ModelVehicle;
import org.emanuel.vehicles.domain.StatusVehicle;
import org.emanuel.vehicles.domain.TypeVehicle;
import org.emanuel.vehicles.domain.Vehicle;
import org.emanuel.vehicles.infra.rest.dto.VehicleModifyDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VehicleMapperTest {

    private final ModelVehicleMapper modelVehicleMapper = new ModelVehicleMapper();
    private final StatusVehicleMapper statusVehicleMapper = new StatusVehicleMapper();
    private final TypeVehicleMapper typeVehicleMapper = new TypeVehicleMapper();
    private final VehicleMapper mapper = new VehicleMapper(modelVehicleMapper, statusVehicleMapper, typeVehicleMapper);

    private Vehicle vehicle() {
        return Vehicle.builder()
                .id(10L)
                .model(new ModelVehicle(1L, "Toyota", "Corolla", 2023L, 25000.0))
                .description("Impecable")
                .status(new StatusVehicle(1L, "Disponible"))
                .type(new TypeVehicle(1L, "Sedán", 3))
                .patent("ABC123")
                .officeLocationId(5L)
                .build();
    }

    @Test
    void toGetDto_mapsAllFields() {
        var dto = mapper.toGetDto(vehicle());

        assertThat(dto.getId()).isEqualTo(10L);
        assertThat(dto.getDescription()).isEqualTo("Impecable");
        assertThat(dto.getPatent()).isEqualTo("ABC123");
        assertThat(dto.getOfficeLocationId()).isEqualTo(5L);
        assertThat(dto.getModel().getId()).isEqualTo(1L);
        assertThat(dto.getModel().getBrand()).isEqualTo("Toyota");
        assertThat(dto.getStatus().getId()).isEqualTo(1L);
        assertThat(dto.getStatus().getName()).isEqualTo("Disponible");
        assertThat(dto.getType().getId()).isEqualTo(1L);
        assertThat(dto.getType().getName()).isEqualTo("Sedán");
    }

    @Test
    void modifyToModel_withId_mapsAllFields() {
        var dto = VehicleModifyDto.builder()
                .modelId(1L).statusId(1L).typeId(1L)
                .description("Nuevo").patent("XYZ999").officeLocationId(3L)
                .build();

        var model = mapper.modifyToModel(7L, dto);

        assertThat(model.getId()).isEqualTo(7L);
        assertThat(model.getDescription()).isEqualTo("Nuevo");
        assertThat(model.getPatent()).isEqualTo("XYZ999");
        assertThat(model.getOfficeLocationId()).isEqualTo(3L);
        assertThat(model.getModel().getId()).isEqualTo(1L);
        assertThat(model.getStatus().getId()).isEqualTo(1L);
        assertThat(model.getType().getId()).isEqualTo(1L);
    }

    @Test
    void modifyToModel_withNullId_setsNullId() {
        var dto = VehicleModifyDto.builder()
                .modelId(1L).statusId(1L).typeId(1L).officeLocationId(3L).build();

        var model = mapper.modifyToModel(null, dto);

        assertThat(model.getId()).isNull();
    }
}
