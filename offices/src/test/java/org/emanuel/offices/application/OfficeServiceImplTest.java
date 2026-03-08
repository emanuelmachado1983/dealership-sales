package org.emanuel.offices.application;

import org.emanuel.offices.application.impl.OfficeServiceImpl;
import org.emanuel.offices.domain.Office;
import org.emanuel.offices.domain.TypeOffice;
import org.emanuel.offices.domain.db.repository.OfficeRepository;
import org.emanuel.offices.domain.exceptions.OfficeBadRequestDomainException;
import org.emanuel.offices.domain.exceptions.OfficeLocalityNotExistsDomainException;
import org.emanuel.offices.domain.exceptions.OfficeNotExistsDomainException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OfficeServiceImplTest {

    @Mock
    ITypeOfficeService typeOfficeService;

    @Mock
    ILocalityService localityService;

    @Mock
    OfficeRepository officeRepository;

    @InjectMocks
    OfficeServiceImpl officeService;

    private Office validOffice() {
        return Office.builder()
                .id(10L)
                .idCountry(1L)
                .idProvince(10L)
                .idLocality(100L)
                .address("Av. Siempreviva 742")
                .name("Sucursal Springfield")
                .openingDate(LocalDateTime.now().minusDays(1))
                .typeOffice(new TypeOffice(2L, "Sucursal"))
                .build();
    }

    // --- getOfficeById ---

    @Test
    void getOfficeById_whenOfficeExists_returnsOffice() {
        var office = validOffice();
        when(officeRepository.findByIdAndNotDeleted(10L)).thenReturn(Optional.of(office));

        var result = officeService.getOfficeById(10L);

        assertThat(result).isEqualTo(office);
    }

    @Test
    void getOfficeById_whenNotFound_throwsException() {
        when(officeRepository.findByIdAndNotDeleted(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> officeService.getOfficeById(99L))
                .isInstanceOf(OfficeNotExistsDomainException.class)
                .hasMessageContaining("99");
    }

    // --- officeExists ---

    @Test
    void officeExists_delegatesToRepository() {
        when(officeRepository.existsByIdAndNotDeleted(10L)).thenReturn(true);
        when(officeRepository.existsByIdAndNotDeleted(99L)).thenReturn(false);

        assertThat(officeService.officeExists(10L)).isTrue();
        assertThat(officeService.officeExists(99L)).isFalse();
    }

    // --- getAllOffices ---

    @Test
    void getAllOffices_returnsListFromRepository() {
        var offices = List.of(validOffice());
        when(officeRepository.findAllAndNotDeleted()).thenReturn(offices);

        var result = officeService.getAllOffices();

        assertThat(result).isEqualTo(offices);
    }

    // --- addOffice ---

    @Test
    void addOffice_whenTypeCentral_throwsException() {
        var office = validOffice();
        office.setTypeOffice(new TypeOffice(1L, "Central"));

        assertThatThrownBy(() -> officeService.addOffice(office))
                .isInstanceOf(OfficeBadRequestDomainException.class)
                .hasMessageContaining("Central");

        verify(officeRepository, never()).save(any());
    }

    @Test
    void addOffice_whenTypeOfficeNotExist_throwsException() {
        var office = validOffice();
        when(typeOfficeService.existsById(2L)).thenReturn(false);

        assertThatThrownBy(() -> officeService.addOffice(office))
                .isInstanceOf(OfficeBadRequestDomainException.class)
                .hasMessageContaining("2");
    }

    @Test
    void addOffice_whenIdCountryNull_throwsException() {
        var office = validOffice();
        office.setIdCountry(null);
        when(typeOfficeService.existsById(2L)).thenReturn(true);

        assertThatThrownBy(() -> officeService.addOffice(office))
                .isInstanceOf(OfficeBadRequestDomainException.class)
                .hasMessageContaining("country");
    }

    @Test
    void addOffice_whenIdProvinceNull_throwsException() {
        var office = validOffice();
        office.setIdProvince(null);
        when(typeOfficeService.existsById(2L)).thenReturn(true);

        assertThatThrownBy(() -> officeService.addOffice(office))
                .isInstanceOf(OfficeBadRequestDomainException.class)
                .hasMessageContaining("province");
    }

    @Test
    void addOffice_whenIdLocalityNull_throwsException() {
        var office = validOffice();
        office.setIdLocality(null);
        when(typeOfficeService.existsById(2L)).thenReturn(true);

        assertThatThrownBy(() -> officeService.addOffice(office))
                .isInstanceOf(OfficeBadRequestDomainException.class)
                .hasMessageContaining("locality");
    }

    @Test
    void addOffice_whenLocalityNotExist_throwsException() {
        var office = validOffice();
        when(typeOfficeService.existsById(2L)).thenReturn(true);
        when(localityService.existsLocalityById(1L, 10L, 100L)).thenReturn(false);

        assertThatThrownBy(() -> officeService.addOffice(office))
                .isInstanceOf(OfficeLocalityNotExistsDomainException.class)
                .hasMessageContaining("100");
    }

    @Test
    void addOffice_whenLocalityAlreadyOccupied_throwsException() {
        var office = validOffice();
        when(typeOfficeService.existsById(2L)).thenReturn(true);
        when(localityService.existsLocalityById(1L, 10L, 100L)).thenReturn(true);
        when(officeRepository.findAllOfficesWithLocalityAndNotDeleted(100L))
                .thenReturn(List.of(validOffice()));

        assertThatThrownBy(() -> officeService.addOffice(office))
                .isInstanceOf(OfficeBadRequestDomainException.class)
                .hasMessageContaining("100");
    }

    @Test
    void addOffice_whenOpeningDateInFuture_throwsException() {
        var office = validOffice();
        office.setOpeningDate(LocalDateTime.now().plusDays(1));
        when(typeOfficeService.existsById(2L)).thenReturn(true);
        when(localityService.existsLocalityById(1L, 10L, 100L)).thenReturn(true);
        when(officeRepository.findAllOfficesWithLocalityAndNotDeleted(100L)).thenReturn(List.of());

        assertThatThrownBy(() -> officeService.addOffice(office))
                .isInstanceOf(OfficeBadRequestDomainException.class)
                .hasMessageContaining("future");
    }

    @Test
    void addOffice_whenValid_savesAndReturns() {
        var office = validOffice();
        when(typeOfficeService.existsById(2L)).thenReturn(true);
        when(localityService.existsLocalityById(1L, 10L, 100L)).thenReturn(true);
        when(officeRepository.findAllOfficesWithLocalityAndNotDeleted(100L)).thenReturn(List.of());
        when(officeRepository.save(office)).thenReturn(office);

        var result = officeService.addOffice(office);

        assertThat(result).isEqualTo(office);
        verify(officeRepository).save(office);
    }

    // --- updateOffice ---

    @Test
    void updateOffice_whenOfficeNotFound_throwsException() {
        var office = validOffice();
        when(officeRepository.findByIdAndNotDeleted(10L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> officeService.updateOffice(10L, office))
                .isInstanceOf(OfficeNotExistsDomainException.class)
                .hasMessageContaining("10");
    }

    @Test
    void updateOffice_whenChangingNonCentralToCentral_throwsException() {
        var existing = validOffice(); // typeOffice id=2 (non-central)
        var updated = validOffice();
        updated.setTypeOffice(new TypeOffice(1L, "Central"));

        when(officeRepository.findByIdAndNotDeleted(10L)).thenReturn(Optional.of(existing));
        when(typeOfficeService.existsById(1L)).thenReturn(true);
        when(localityService.existsLocalityById(1L, 10L, 100L)).thenReturn(true);

        assertThatThrownBy(() -> officeService.updateOffice(10L, updated))
                .isInstanceOf(OfficeBadRequestDomainException.class)
                .hasMessageContaining("central");
    }

    @Test
    void updateOffice_whenChangingCentralToNonCentral_throwsException() {
        var existing = validOffice();
        existing.setTypeOffice(new TypeOffice(1L, "Central")); // existing is central
        var updated = validOffice(); // updated is non-central (id=2)

        when(officeRepository.findByIdAndNotDeleted(10L)).thenReturn(Optional.of(existing));
        when(typeOfficeService.existsById(2L)).thenReturn(true);
        when(localityService.existsLocalityById(1L, 10L, 100L)).thenReturn(true);

        assertThatThrownBy(() -> officeService.updateOffice(10L, updated))
                .isInstanceOf(OfficeBadRequestDomainException.class)
                .hasMessageContaining("central");
    }

    @Test
    void updateOffice_whenValid_savesAndReturns() {
        var existing = validOffice();
        var updated = validOffice();
        updated.setName("Nombre Actualizado");

        when(officeRepository.findByIdAndNotDeleted(10L)).thenReturn(Optional.of(existing));
        when(typeOfficeService.existsById(2L)).thenReturn(true);
        when(localityService.existsLocalityById(1L, 10L, 100L)).thenReturn(true);
        when(officeRepository.save(updated)).thenReturn(updated);

        var result = officeService.updateOffice(10L, updated);

        assertThat(result.getName()).isEqualTo("Nombre Actualizado");
        verify(officeRepository).save(updated);
    }

    // --- deleteOffice ---

    @Test
    void deleteOffice_whenOfficeExists_setsDeletedAt() {
        var office = validOffice();
        when(officeRepository.findByIdAndNotDeleted(10L)).thenReturn(Optional.of(office));

        officeService.deleteOffice(10L);

        var captor = ArgumentCaptor.forClass(Office.class);
        verify(officeRepository).save(captor.capture());
        assertThat(captor.getValue().getDeletedAt()).isNotNull();
    }

    @Test
    void deleteOffice_whenNotFound_throwsException() {
        when(officeRepository.findByIdAndNotDeleted(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> officeService.deleteOffice(99L))
                .isInstanceOf(OfficeNotExistsDomainException.class)
                .hasMessageContaining("99");

        verify(officeRepository, never()).save(any());
    }
}
