package org.emanuel.offices.infra.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.emanuel.offices.application.ICountryService;
import org.emanuel.offices.domain.Country;
import org.emanuel.offices.domain.exceptions.CountryNotExistDomainException;
import org.emanuel.offices.infra.rest.dto.CountryGetDto;
import org.emanuel.offices.infra.rest.dto.CountryModifyDto;
import org.emanuel.offices.infra.rest.mapper.CountryMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CountryRest.class)
class CountryRestTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ICountryService countryService;

    @MockBean
    CountryMapper countryMapper;

    @Test
    void getCountryById_whenFound_returns200() throws Exception {
        var country = new Country(1L, "Argentina");
        var dto = new CountryGetDto(1L, "Argentina");
        when(countryService.getCountryById(1L)).thenReturn(country);
        when(countryMapper.toGetDto(country)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/countries/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Argentina"));
    }

    @Test
    void getCountryById_whenNotFound_returns404() throws Exception {
        when(countryService.getCountryById(99L))
                .thenThrow(new CountryNotExistDomainException("Country not found with id: 99"));

        mockMvc.perform(get("/api/v1/countries/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllCountries_whenEmpty_returns204() throws Exception {
        when(countryService.getAllCountries()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/countries"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllCountries_whenNotEmpty_returns200WithList() throws Exception {
        var country = new Country(1L, "Argentina");
        var dto = new CountryGetDto(1L, "Argentina");
        when(countryService.getAllCountries()).thenReturn(List.of(country));
        when(countryMapper.toGetDto(country)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/countries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Argentina"));
    }

    @Test
    void addCountry_returns201WithCreatedCountry() throws Exception {
        var body = new CountryModifyDto("Argentina");
        var country = new Country(1L, "Argentina");
        var dto = new CountryGetDto(1L, "Argentina");
        when(countryService.addCountry("Argentina")).thenReturn(country);
        when(countryMapper.toGetDto(country)).thenReturn(dto);

        mockMvc.perform(post("/api/v1/countries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Argentina"));
    }

    @Test
    void updateCountry_returns200WithUpdatedCountry() throws Exception {
        var body = new CountryModifyDto("Argentina Actualizado");
        var country = new Country(1L, "Argentina Actualizado");
        var dto = new CountryGetDto(1L, "Argentina Actualizado");
        when(countryService.updateCountry(1L, "Argentina Actualizado")).thenReturn(country);
        when(countryMapper.toGetDto(country)).thenReturn(dto);

        mockMvc.perform(put("/api/v1/countries/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Argentina Actualizado"));
    }

    @Test
    void updateCountry_whenNotFound_returns404() throws Exception {
        var body = new CountryModifyDto("Nombre");
        when(countryService.updateCountry(eq(99L), any()))
                .thenThrow(new CountryNotExistDomainException("Country not found with id: 99"));

        mockMvc.perform(put("/api/v1/countries/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteCountry_returns200() throws Exception {
        doNothing().when(countryService).deleteCountry(1L);

        mockMvc.perform(delete("/api/v1/countries/1"))
                .andExpect(status().isOk());
    }
}
