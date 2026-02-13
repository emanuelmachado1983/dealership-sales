package org.emanuel.offices.infra.rest;

import org.emanuel.offices.application.ICountryService;
import org.emanuel.offices.infra.rest.dto.CountryGetDto;
import org.emanuel.offices.infra.rest.dto.CountryModifyDto;
import org.emanuel.offices.infra.rest.mapper.CountryMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/countries")
public class CountryRest {
    private final ICountryService countryService;
    private final CountryMapper countryMapper;

    public CountryRest(ICountryService countryService,
                       CountryMapper countryMapper) {
        this.countryService = countryService;
        this.countryMapper = countryMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryGetDto> getCountryById(@PathVariable Long id) {
        return ResponseEntity.ok(countryMapper.toGetDto(countryService.getCountryById(id)));
    }

    @GetMapping("")
    public ResponseEntity<List<CountryGetDto>> getAllCountries() {
        var countries = countryService.getAllCountries();
        if (countries.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(
                countries.stream().map(countryMapper::toGetDto).toList()
        );
    }

    @PostMapping("")
    public ResponseEntity<CountryGetDto> addCountry(@RequestBody CountryModifyDto country) {

        var countryDto = countryMapper.toGetDto(countryService.addCountry(country.getName()));

        return ResponseEntity.created(URI.create("/countries/" + countryDto.getId()))
                .body(countryDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CountryGetDto> updateCountry(@PathVariable Long id, @RequestBody CountryModifyDto countryModifyDto) {
        return ResponseEntity.ok(
                countryMapper.toGetDto(countryService.updateCountry(id, countryModifyDto.getName())));
    }

    @DeleteMapping("/{id}")
    public void deleteCountry(@PathVariable Long id) {
        countryService.deleteCountry(id);
    }

}
