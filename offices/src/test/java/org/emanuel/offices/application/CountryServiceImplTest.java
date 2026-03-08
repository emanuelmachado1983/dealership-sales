package org.emanuel.offices.application;

import org.emanuel.offices.application.impl.CountryServiceImpl;
import org.emanuel.offices.domain.Country;
import org.emanuel.offices.domain.db.repository.CountryRepository;
import org.emanuel.offices.domain.exceptions.CountryNotExistDomainException;
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
class CountryServiceImplTest {

    @Mock
    CountryRepository countryRepository;

    @InjectMocks
    CountryServiceImpl countryService;

    @Test
    void getCountryById_whenCountryExists_returnsCountry() {
        var country = new Country(1L, "Argentina");
        when(countryRepository.findByIdAndNotDeleted(1L)).thenReturn(Optional.of(country));

        var result = countryService.getCountryById(1L);

        assertThat(result).isEqualTo(country);
    }

    @Test
    void getCountryById_whenCountryNotFound_throwsException() {
        when(countryRepository.findByIdAndNotDeleted(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> countryService.getCountryById(99L))
                .isInstanceOf(CountryNotExistDomainException.class)
                .hasMessageContaining("99");
    }

    @Test
    void getAllCountries_returnsListFromRepository() {
        var countries = List.of(new Country(1L, "Argentina"), new Country(2L, "Brasil"));
        when(countryRepository.findAllAndNotDeleted()).thenReturn(countries);

        var result = countryService.getAllCountries();

        assertThat(result).hasSize(2).containsExactlyElementsOf(countries);
    }

    @Test
    void addCountry_savesAndReturnsCountry() {
        var saved = new Country(1L, "Argentina");
        when(countryRepository.save(any(Country.class))).thenReturn(saved);

        var result = countryService.addCountry("Argentina");

        assertThat(result).isEqualTo(saved);
        var captor = ArgumentCaptor.forClass(Country.class);
        verify(countryRepository).save(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo("Argentina");
        assertThat(captor.getValue().getId()).isNull();
    }

    @Test
    void updateCountry_whenCountryExists_savesAndReturns() {
        when(countryRepository.existsByIdAndNotDeleted(1L)).thenReturn(true);
        var updated = new Country(1L, "Argentina Actualizado");
        when(countryRepository.save(any(Country.class))).thenReturn(updated);

        var result = countryService.updateCountry(1L, "Argentina Actualizado");

        assertThat(result).isEqualTo(updated);
    }

    @Test
    void updateCountry_whenCountryNotFound_throwsException() {
        when(countryRepository.existsByIdAndNotDeleted(99L)).thenReturn(false);

        assertThatThrownBy(() -> countryService.updateCountry(99L, "Nombre"))
                .isInstanceOf(CountryNotExistDomainException.class)
                .hasMessageContaining("99");
    }

    @Test
    void deleteCountry_whenCountryExists_setsDeletedAt() {
        var country = new Country(1L, "Argentina");
        when(countryRepository.findByIdAndNotDeleted(1L)).thenReturn(Optional.of(country));

        countryService.deleteCountry(1L);

        var captor = ArgumentCaptor.forClass(Country.class);
        verify(countryRepository).save(captor.capture());
        assertThat(captor.getValue().getDeletedAt()).isNotNull();
    }

    @Test
    void deleteCountry_whenCountryNotFound_throwsException() {
        when(countryRepository.findByIdAndNotDeleted(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> countryService.deleteCountry(99L))
                .isInstanceOf(CountryNotExistDomainException.class)
                .hasMessageContaining("99");

        verify(countryRepository, never()).save(any());
    }
}
