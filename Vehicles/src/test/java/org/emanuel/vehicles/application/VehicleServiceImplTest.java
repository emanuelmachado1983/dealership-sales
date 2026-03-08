package org.emanuel.vehicles.application;

import org.emanuel.vehicles.application.impl.VehicleServiceImpl;
import org.emanuel.vehicles.application.integration.offices.IOfficeService;
import org.emanuel.vehicles.domain.ModelVehicle;
import org.emanuel.vehicles.domain.StatusVehicle;
import org.emanuel.vehicles.domain.TypeVehicle;
import org.emanuel.vehicles.domain.Vehicle;
import org.emanuel.vehicles.domain.db.repository.VehicleRepository;
import org.emanuel.vehicles.domain.exceptions.VehicleBadRequestException;
import org.emanuel.vehicles.domain.exceptions.VehicleNotExistException;
import org.emanuel.vehicles.infra.grpc.office.OfficeGrcpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceImplTest {

    @Mock
    IOfficeService officeService;

    @Mock
    VehicleRepository vehicleRepository;

    @Mock
    OfficeGrcpClient officeGrcpClient;

    // Se instancia manualmente para evitar ambigüedad entre IOfficeService y OfficeGrcpClient
    VehicleServiceImpl vehicleService;

    @BeforeEach
    void setUp() {
        vehicleService = new VehicleServiceImpl(officeService, vehicleRepository, officeGrcpClient);
    }

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

    // --- getVehicleById ---

    @Test
    void getVehicleById_whenExists_returnsVehicle() {
        when(vehicleRepository.findById(10L)).thenReturn(Optional.of(vehicle()));

        var result = vehicleService.getVehicleById(10L);

        assertThat(result).isEqualTo(vehicle());
    }

    @Test
    void getVehicleById_whenNotFound_throwsException() {
        when(vehicleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> vehicleService.getVehicleById(99L))
                .isInstanceOf(VehicleNotExistException.class)
                .hasMessageContaining("99");
    }

    // --- getAllVehicles ---

    @Test
    void getAllVehicles_delegatesFiltersToRepository() {
        var vehicles = List.of(vehicle());
        when(vehicleRepository.findAllByFilters(5L, 1L, 1L, 1L)).thenReturn(vehicles);

        var result = vehicleService.getAllVehicles(5L, 1L, 1L, 1L);

        assertThat(result).isEqualTo(vehicles);
    }

    @Test
    void getAllVehicles_withNullFilters_delegatesToRepository() {
        when(vehicleRepository.findAllByFilters(null, null, null, null)).thenReturn(List.of());

        var result = vehicleService.getAllVehicles(null, null, null, null);

        assertThat(result).isEmpty();
    }

    // --- addVehicle ---

    @Test
    void addVehicle_whenOfficeExists_saves() {
        var vehicle = vehicle();
        when(officeGrcpClient.existsById(5L)).thenReturn(true);

        vehicleService.addVehicle(vehicle);

        verify(vehicleRepository).save(vehicle);
    }

    @Test
    void addVehicle_whenOfficeNotExist_throwsException() {
        var vehicle = vehicle();
        when(officeGrcpClient.existsById(5L)).thenReturn(false);

        assertThatThrownBy(() -> vehicleService.addVehicle(vehicle))
                .isInstanceOf(VehicleBadRequestException.class)
                .hasMessageContaining("5");

        verify(vehicleRepository, never()).save(any());
    }

    // --- updateVehicle ---

    @Test
    void updateVehicle_whenOfficeNotExist_throwsException() {
        var vehicle = vehicle();
        when(officeGrcpClient.existsById(5L)).thenReturn(false);

        assertThatThrownBy(() -> vehicleService.updateVehicle(10L, vehicle))
                .isInstanceOf(VehicleBadRequestException.class)
                .hasMessageContaining("5");

        verify(vehicleRepository, never()).save(any());
    }

    @Test
    void updateVehicle_whenOfficeExistsButVehicleNotFound_throwsException() {
        var vehicle = vehicle();
        when(officeGrcpClient.existsById(5L)).thenReturn(true);
        when(vehicleRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> vehicleService.updateVehicle(99L, vehicle))
                .isInstanceOf(VehicleNotExistException.class)
                .hasMessageContaining("99");

        verify(vehicleRepository, never()).save(any());
    }

    @Test
    void updateVehicle_whenValid_saves() {
        var vehicle = vehicle();
        when(officeGrcpClient.existsById(5L)).thenReturn(true);
        when(vehicleRepository.existsById(10L)).thenReturn(true);

        vehicleService.updateVehicle(10L, vehicle);

        verify(vehicleRepository).save(vehicle);
    }

    // --- changeState ---

    @Test
    void changeState_whenVehicleExists_updatesStatusAndSaves() {
        var vehicle = vehicle();
        when(vehicleRepository.findById(10L)).thenReturn(Optional.of(vehicle));

        vehicleService.changeState(10L, 2L);

        var captor = ArgumentCaptor.forClass(Vehicle.class);
        verify(vehicleRepository).save(captor.capture());
        assertThat(captor.getValue().getStatus().getId()).isEqualTo(2L);
    }

    @Test
    void changeState_whenVehicleNotFound_throwsException() {
        when(vehicleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> vehicleService.changeState(99L, 2L))
                .isInstanceOf(VehicleNotExistException.class)
                .hasMessageContaining("99");

        verify(vehicleRepository, never()).save(any());
    }

    // --- updateStateVehicle ---

    @Test
    void updateStateVehicle_whenVehicleExists_callsUpdateStatus() {
        when(vehicleRepository.existsById(10L)).thenReturn(true);

        vehicleService.updateStateVehicle(10L, 3L);

        verify(vehicleRepository).updateVehicleStatus(10L, 3L);
    }

    @Test
    void updateStateVehicle_whenVehicleNotFound_throwsException() {
        when(vehicleRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> vehicleService.updateStateVehicle(99L, 3L))
                .isInstanceOf(VehicleNotExistException.class)
                .hasMessageContaining("99");

        verify(vehicleRepository, never()).updateVehicleStatus(any(), any());
    }
}
