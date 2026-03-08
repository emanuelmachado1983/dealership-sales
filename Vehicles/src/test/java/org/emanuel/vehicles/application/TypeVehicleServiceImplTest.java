package org.emanuel.vehicles.application;

import org.emanuel.vehicles.application.impl.TypeVehicleServiceImpl;
import org.emanuel.vehicles.domain.TypeVehicle;
import org.emanuel.vehicles.domain.db.repository.TypeVehicleRepository;
import org.emanuel.vehicles.domain.exceptions.TypeVehicleNotExistException;
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
class TypeVehicleServiceImplTest {

    @Mock
    TypeVehicleRepository typeVehicleRepository;

    @InjectMocks
    TypeVehicleServiceImpl typeVehicleService;

    private TypeVehicle typeVehicle() {
        return new TypeVehicle(1L, "Sedán", 3);
    }

    @Test
    void getTypeVehicleById_whenExists_returnsTypeVehicle() {
        when(typeVehicleRepository.findById(1L)).thenReturn(Optional.of(typeVehicle()));

        var result = typeVehicleService.getTypeVehicleById(1L);

        assertThat(result).isEqualTo(typeVehicle());
    }

    @Test
    void getTypeVehicleById_whenNotFound_throwsException() {
        when(typeVehicleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> typeVehicleService.getTypeVehicleById(99L))
                .isInstanceOf(TypeVehicleNotExistException.class)
                .hasMessageContaining("99");
    }

    @Test
    void getAllTypes_returnsListFromRepository() {
        when(typeVehicleRepository.findAll()).thenReturn(List.of(typeVehicle()));

        var result = typeVehicleService.getAllTypes();

        assertThat(result).hasSize(1);
    }

    @Test
    void addTypeVehicle_delegatesToRepository() {
        var type = typeVehicle();

        typeVehicleService.addTypeVehicle(type);

        verify(typeVehicleRepository).save(type);
    }

    @Test
    void updateTypeVehicle_whenExists_setsIdAndSaves() {
        var type = new TypeVehicle(null, "SUV", 5);
        when(typeVehicleRepository.existsById(3L)).thenReturn(true);

        typeVehicleService.updateTypeVehicle(3L, type);

        assertThat(type.getId()).isEqualTo(3L);
        verify(typeVehicleRepository).save(type);
    }

    @Test
    void updateTypeVehicle_whenNotFound_throwsException() {
        var type = typeVehicle();
        when(typeVehicleRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> typeVehicleService.updateTypeVehicle(99L, type))
                .isInstanceOf(TypeVehicleNotExistException.class)
                .hasMessageContaining("99");

        verify(typeVehicleRepository, never()).save(any());
    }

    @Test
    void deleteTypeVehicle_whenExists_deletesById() {
        when(typeVehicleRepository.findById(1L)).thenReturn(Optional.of(typeVehicle()));

        typeVehicleService.deleteTypeVehicle(1L);

        verify(typeVehicleRepository).deleteById(1L);
    }

    @Test
    void deleteTypeVehicle_whenNotFound_throwsException() {
        when(typeVehicleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> typeVehicleService.deleteTypeVehicle(99L))
                .isInstanceOf(TypeVehicleNotExistException.class)
                .hasMessageContaining("99");

        verify(typeVehicleRepository, never()).deleteById(any());
    }
}
