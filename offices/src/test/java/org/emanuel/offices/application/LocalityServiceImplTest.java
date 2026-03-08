package org.emanuel.offices.application;

import org.emanuel.offices.application.impl.LocalityServiceImpl;
import org.emanuel.offices.domain.Country;
import org.emanuel.offices.domain.Locality;
import org.emanuel.offices.domain.Province;
import org.emanuel.offices.domain.db.repository.LocalityRepository;
import org.emanuel.offices.domain.exceptions.LocalityNotExistDomainException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocalityServiceImplTest {

    @Mock
    IProvinceService provinceService;

    @Mock
    ICountryService countryService;

    @Mock
    LocalityRepository localityRepository;

    @InjectMocks
    LocalityServiceImpl localityService;

    private Country country() {
        return new Country(1L, "Argentina");
    }

    private Province province() {
        return new Province(10L, "Buenos Aires", country());
    }

    private Locality locality() {
        return Locality.builder().id(100L).name("La Plata").provinceId(10L).build();
    }

    @Test
    void getLocalityById_whenLocalityExists_returnsLocality() {
        var locality = locality();
        when(countryService.getCountryById(1L)).thenReturn(country());
        when(localityRepository.findByIdAndNotDeleted(10L, 100L)).thenReturn(Optional.of(locality));

        var result = localityService.getLocalityById(1L, 10L, 100L);

        assertThat(result).isEqualTo(locality);
    }

    @Test
    void getLocalityById_whenLocalityNotFound_throwsException() {
        when(countryService.getCountryById(1L)).thenReturn(country());
        when(localityRepository.findByIdAndNotDeleted(10L, 99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> localityService.getLocalityById(1L, 10L, 99L))
                .isInstanceOf(LocalityNotExistDomainException.class)
                .hasMessageContaining("99");
    }

    @Test
    void getAllLocalities_validatesCountryAndReturnsList() {
        var localities = List.of(locality());
        when(countryService.getCountryById(1L)).thenReturn(country());
        when(localityRepository.findAllAndNotDeleted(10L)).thenReturn(localities);

        var result = localityService.getAllLocalities(1L, 10L);

        assertThat(result).isEqualTo(localities);
    }

    @Test
    void addLocality_validatesCountryAndProvinceAndSaves() {
        var province = province();
        when(countryService.getCountryById(1L)).thenReturn(country());
        when(provinceService.getProvinceById(1L, 10L)).thenReturn(province);
        var saved = locality();
        when(localityRepository.save(any(Locality.class))).thenReturn(saved);

        var result = localityService.addLocality(1L, 10L, "La Plata");

        assertThat(result).isEqualTo(saved);
        var captor = ArgumentCaptor.forClass(Locality.class);
        verify(localityRepository).save(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo("La Plata");
        assertThat(captor.getValue().getProvinceId()).isEqualTo(10L);
    }

    @Test
    void updateLocality_whenLocalityExists_updatesName() {
        var locality = locality();
        when(countryService.getCountryById(1L)).thenReturn(country());
        when(provinceService.getProvinceById(1L, 10L)).thenReturn(province());
        when(localityRepository.findByIdAndNotDeleted(10L, 100L)).thenReturn(Optional.of(locality));
        when(localityRepository.save(locality)).thenReturn(locality);

        var result = localityService.updateLocality(1L, 10L, 100L, "Nuevo Nombre");

        assertThat(result.getName()).isEqualTo("Nuevo Nombre");
        verify(localityRepository).save(locality);
    }

    @Test
    void updateLocality_whenLocalityNotFound_throwsException() {
        when(countryService.getCountryById(1L)).thenReturn(country());
        when(provinceService.getProvinceById(1L, 10L)).thenReturn(province());
        when(localityRepository.findByIdAndNotDeleted(10L, 99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> localityService.updateLocality(1L, 10L, 99L, "Nombre"))
                .isInstanceOf(LocalityNotExistDomainException.class)
                .hasMessageContaining("99");
    }

    @Test
    void deleteLocality_whenLocalityExists_setsDeletedAt() {
        var locality = locality();
        when(countryService.getCountryById(1L)).thenReturn(country());
        when(provinceService.getProvinceById(1L, 10L)).thenReturn(province());
        when(localityRepository.findByIdAndNotDeleted(10L, 100L)).thenReturn(Optional.of(locality));

        localityService.deleteLocality(1L, 10L, 100L);

        var captor = ArgumentCaptor.forClass(Locality.class);
        verify(localityRepository).save(captor.capture());
        assertThat(captor.getValue().getDeletedAt()).isNotNull();
    }

    @Test
    void existsLocalityById_whenLocalityExists_returnsTrue() {
        when(localityRepository.findByIdAndNotDeleted(10L, 100L)).thenReturn(Optional.of(locality()));

        assertThat(localityService.existsLocalityById(1L, 10L, 100L)).isTrue();
    }

    @Test
    void existsLocalityById_whenLocalityNotFound_returnsFalse() {
        when(localityRepository.findByIdAndNotDeleted(10L, 99L)).thenReturn(Optional.empty());

        assertThat(localityService.existsLocalityById(1L, 10L, 99L)).isFalse();
    }
}
