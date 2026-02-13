package org.emanuel.offices.infra.rest.mapper;

import org.emanuel.offices.infra.rest.dto.CountryGetDto;
import org.emanuel.offices.domain.Country;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper {
    public CountryGetDto toGetDto(Country country) {
        return new CountryGetDto(country.getId(), country.getName());
    }

    public Country toModel(CountryGetDto countryGetDto) {
        return new Country(countryGetDto.getId(), countryGetDto.getName());
    }
}
