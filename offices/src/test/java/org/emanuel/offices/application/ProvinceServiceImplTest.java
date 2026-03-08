package org.emanuel.offices.application;

import org.emanuel.offices.application.impl.ProvinceServiceImpl;
import org.emanuel.offices.domain.Country;
import org.emanuel.offices.domain.Province;
import org.emanuel.offices.domain.db.repository.ProvinceRepository;
import org.emanuel.offices.domain.exceptions.ProvinceNotExistDomainException;
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
class ProvinceServiceImplTest {

    @Mock
    ICountryService countryService;

    @Mock
    ProvinceRepository provinceRepository;

    @InjectMocks
    ProvinceServiceImpl provinceService;

    private Country country() {
        return new Country(1L, "Argentina");
    }

    private Province province() {
        return new Province(10L, "Buenos Aires", country());
    }

    @Test
    void getProvinceById_whenProvinceExists_returnsProvince() {
        var province = province();
        when(provinceRepository.findByIdAndNotDeleted(10L)).thenReturn(Optional.of(province));

        var result = provinceService.getProvinceById(1L, 10L);

        assertThat(result).isEqualTo(province);
    }

    @Test
    void getProvinceById_whenProvinceNotFound_throwsException() {
        when(provinceRepository.findByIdAndNotDeleted(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> provinceService.getProvinceById(1L, 99L))
                .isInstanceOf(ProvinceNotExistDomainException.class)
                .hasMessageContaining("99");
    }

    @Test
    void getAllProvinces_returnsListFromRepository() {
        var provinces = List.of(province());
        when(provinceRepository.findAllByCountryIdAndNotDeleted(1L)).thenReturn(provinces);

        var result = provinceService.getAllProvinces(1L);

        assertThat(result).isEqualTo(provinces);
    }

    @Test
    void addProvince_validatesCountryAndSaves() {
        var country = country();
        when(countryService.getCountryById(1L)).thenReturn(country);
        var saved = province();
        when(provinceRepository.save(any(Province.class))).thenReturn(saved);

        var result = provinceService.addProvince(1L, "Buenos Aires");

        assertThat(result).isEqualTo(saved);
        var captor = ArgumentCaptor.forClass(Province.class);
        verify(provinceRepository).save(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo("Buenos Aires");
        assertThat(captor.getValue().getCountry()).isEqualTo(country);
    }

    @Test
    void updateProvince_whenProvinceExists_updatesName() {
        var province = province();
        when(provinceRepository.findByIdAndNotDeleted(10L)).thenReturn(Optional.of(province));
        when(provinceRepository.save(any(Province.class))).thenReturn(province);

        var result = provinceService.updateProvince(1L, 10L, "Nuevo Nombre");

        assertThat(result.getName()).isEqualTo("Nuevo Nombre");
        verify(provinceRepository).save(province);
    }

    @Test
    void updateProvince_whenProvinceNotFound_throwsException() {
        when(provinceRepository.findByIdAndNotDeleted(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> provinceService.updateProvince(1L, 99L, "Nombre"))
                .isInstanceOf(ProvinceNotExistDomainException.class)
                .hasMessageContaining("99");

        verify(provinceRepository, never()).save(any());
    }

    @Test
    void deleteProvince_whenProvinceExists_setsDeletedAt() {
        var province = province();
        when(provinceRepository.findByIdAndNotDeleted(10L)).thenReturn(Optional.of(province));

        provinceService.deleteProvince(1L, 10L);

        var captor = ArgumentCaptor.forClass(Province.class);
        verify(provinceRepository).save(captor.capture());
        assertThat(captor.getValue().getDeletedAt()).isNotNull();
    }

    @Test
    void deleteProvince_whenProvinceNotFound_throwsException() {
        when(provinceRepository.findByIdAndNotDeleted(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> provinceService.deleteProvince(1L, 99L))
                .isInstanceOf(ProvinceNotExistDomainException.class)
                .hasMessageContaining("99");

        verify(provinceRepository, never()).save(any());
    }
}
