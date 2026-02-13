package org.emanuel.offices.application.impl;

import lombok.RequiredArgsConstructor;
import org.emanuel.offices.application.ICountryService;
import org.emanuel.offices.domain.Country;
import org.emanuel.offices.domain.exceptions.CountryNotExistDomainException;
import org.emanuel.offices.domain.db.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements ICountryService {
    private final CountryRepository countryRepository;

    @Override
    public Country getCountryById(Long id) {
        return countryRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new CountryNotExistDomainException("Country not found with id: " + id));
    }

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAllAndNotDeleted();
    }

    @Override
    public Country addCountry(String name) {
        return countryRepository.save(new Country(null, name));
    }

    @Override
    public Country updateCountry(Long idCountry, String name) {
        if (countryRepository.existsByIdAndNotDeleted(idCountry)) {
            return countryRepository.save(new Country(idCountry, name));
        } else {
            throw new CountryNotExistDomainException("Country not found with id: " + idCountry);
        }
    }

    @Override
    public void deleteCountry(Long id) {
        //TODO: Must verify if there is an office in the country
        // If there is an office, throw an exception
        var country = countryRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new CountryNotExistDomainException("Country not found with id: " + id));
        country.setDeletedAt(LocalDateTime.now());
        countryRepository.save(country);
    }
}
