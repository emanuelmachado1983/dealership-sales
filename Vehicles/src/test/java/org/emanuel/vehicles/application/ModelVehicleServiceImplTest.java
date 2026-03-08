package org.emanuel.vehicles.application;

import org.emanuel.vehicles.application.impl.ModelVehicleServiceImpl;
import org.emanuel.vehicles.domain.ModelVehicle;
import org.emanuel.vehicles.domain.db.repository.ModelVehicleRepository;
import org.emanuel.vehicles.domain.exceptions.ModelVehicleNotExistException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModelVehicleServiceImplTest {

    @Mock
    ModelVehicleRepository modelVehicleRepository;

    @InjectMocks
    ModelVehicleServiceImpl modelVehicleService;

    private ModelVehicle model() {
        return new ModelVehicle(1L, "Toyota", "Corolla", 2023L, 25000.0);
    }

    @Test
    void getModelVehicleById_whenExists_returnsModel() {
        when(modelVehicleRepository.findById(1L)).thenReturn(Optional.of(model()));

        var result = modelVehicleService.getModelVehicleById(1L);

        assertThat(result).isEqualTo(model());
    }

    @Test
    void getModelVehicleById_whenNotFound_throwsException() {
        when(modelVehicleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> modelVehicleService.getModelVehicleById(99L))
                .isInstanceOf(ModelVehicleNotExistException.class)
                .hasMessageContaining("99");
    }

    @Test
    void getAllModelVehicles_returnsListFromRepository() {
        when(modelVehicleRepository.findAll()).thenReturn(List.of(model()));

        var result = modelVehicleService.getAllModelVehicles();

        assertThat(result).hasSize(1);
    }

    @Test
    void addModelVehicle_delegatesToRepository() {
        var model = model();

        modelVehicleService.addModelVehicle(model);

        verify(modelVehicleRepository).save(model);
    }

    @Test
    void updateModelVehicle_whenExists_setsIdAndSaves() {
        var model = new ModelVehicle(null, "Honda", "Civic", 2022L, 20000.0);
        when(modelVehicleRepository.existsById(5L)).thenReturn(true);

        modelVehicleService.updateModelVehicle(5L, model);

        assertThat(model.getId()).isEqualTo(5L);
        verify(modelVehicleRepository).save(model);
    }

    @Test
    void updateModelVehicle_whenNotFound_throwsException() {
        var model = model();
        when(modelVehicleRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> modelVehicleService.updateModelVehicle(99L, model))
                .isInstanceOf(ModelVehicleNotExistException.class)
                .hasMessageContaining("99");

        verify(modelVehicleRepository, never()).save(any());
    }

    @Test
    void deleteModelVehicle_whenExists_deletesById() {
        when(modelVehicleRepository.findById(1L)).thenReturn(Optional.of(model()));

        modelVehicleService.deleteModelVehicle(1L);

        verify(modelVehicleRepository).deleteById(1L);
    }

    @Test
    void deleteModelVehicle_whenNotFound_throwsException() {
        when(modelVehicleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> modelVehicleService.deleteModelVehicle(99L))
                .isInstanceOf(ModelVehicleNotExistException.class)
                .hasMessageContaining("99");

        verify(modelVehicleRepository, never()).deleteById(any());
    }
}
