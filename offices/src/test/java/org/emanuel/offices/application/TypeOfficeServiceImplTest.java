package org.emanuel.offices.application;

import org.emanuel.offices.application.impl.TypeOfficeServiceImpl;
import org.emanuel.offices.domain.TypeOffice;
import org.emanuel.offices.domain.db.repository.TypeOfficeRepository;
import org.emanuel.offices.domain.exceptions.TypeOfficeNotExistsDomainException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TypeOfficeServiceImplTest {

    @Mock
    TypeOfficeRepository typeOfficeRepository;

    @InjectMocks
    TypeOfficeServiceImpl typeOfficeService;

    @Test
    void getTypeOffices_returnsAllFromRepository() {
        var types = List.of(new TypeOffice(1L, "Central"), new TypeOffice(2L, "Sucursal"));
        when(typeOfficeRepository.findAll()).thenReturn(types);

        var result = typeOfficeService.getTypeOffices();

        assertThat(result).isEqualTo(types);
    }

    @Test
    void getTypeOfficeById_whenExists_returnsTypeOffice() {
        var type = new TypeOffice(2L, "Sucursal");
        when(typeOfficeRepository.findById(2L)).thenReturn(Optional.of(type));

        var result = typeOfficeService.getTypeOfficeById(2L);

        assertThat(result).isEqualTo(type);
    }

    @Test
    void getTypeOfficeById_whenNotFound_throwsException() {
        when(typeOfficeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> typeOfficeService.getTypeOfficeById(99L))
                .isInstanceOf(TypeOfficeNotExistsDomainException.class)
                .hasMessageContaining("99");
    }

    @Test
    void existsById_delegatesToRepository() {
        when(typeOfficeRepository.existsById(1L)).thenReturn(true);
        when(typeOfficeRepository.existsById(99L)).thenReturn(false);

        assertThat(typeOfficeService.existsById(1L)).isTrue();
        assertThat(typeOfficeService.existsById(99L)).isFalse();
    }
}
