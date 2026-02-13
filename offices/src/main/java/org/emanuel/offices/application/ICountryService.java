package org.emanuel.offices.application;

import org.emanuel.offices.domain.Country;

import java.util.List;

public interface ICountryService {
    Country getCountryById(Long id);

    List<Country> getAllCountries();

    Country addCountry(String name);

    Country updateCountry(Long idCountry, String name);

    void deleteCountry(Long id);
}
