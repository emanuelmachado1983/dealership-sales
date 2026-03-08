package org.emanuel.vehicles.application;

import org.emanuel.vehicles.application.impl.StatusVehicleServiceImpl;
import org.emanuel.vehicles.domain.StatusVehicle;
import org.emanuel.vehicles.domain.db.repository.StatusVehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatusVehicleServiceImplTest {

    @Mock
    StatusVehicleRepository statusVehicleRepository;

    @InjectMocks
    StatusVehicleServiceImpl statusVehicleService;

    @Test
    void getAllStates_returnsListFromRepository() {
        var states = List.of(
                new StatusVehicle(1L, "Disponible"),
                new StatusVehicle(2L, "Reservado"),
                new StatusVehicle(3L, "Vendido")
        );
        when(statusVehicleRepository.findAll()).thenReturn(states);

        var result = statusVehicleService.getAllStates();

        assertThat(result).isEqualTo(states);
    }

    @Test
    void getAllStates_whenEmpty_returnsEmptyList() {
        when(statusVehicleRepository.findAll()).thenReturn(List.of());

        var result = statusVehicleService.getAllStates();

        assertThat(result).isEmpty();
    }
}
