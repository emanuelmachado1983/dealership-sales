package org.emanuel.offices.infra.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.emanuel.offices.application.ILocalityService;
import org.emanuel.offices.domain.Locality;
import org.emanuel.offices.domain.exceptions.LocalityNotExistDomainException;
import org.emanuel.offices.infra.rest.dto.LocalityGetDto;
import org.emanuel.offices.infra.rest.dto.LocalityModifyDto;
import org.emanuel.offices.infra.rest.mapper.LocalityMapper;
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

@WebMvcTest(LocalityRest.class)
class LocalityRestTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ILocalityService localityService;

    @MockBean
    LocalityMapper localityMapper;

    private Locality locality() {
        return Locality.builder().id(100L).name("La Plata").provinceId(10L).build();
    }

    private LocalityGetDto localityDto() {
        return new LocalityGetDto(100L, "La Plata");
    }

    @Test
    void getLocalityById_whenFound_returns200() throws Exception {
        var locality = locality();
        var dto = localityDto();
        when(localityService.getLocalityById(1L, 10L, 100L)).thenReturn(locality);
        when(localityMapper.toGetDto(locality)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/countries/1/provinces/10/localities/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.name").value("La Plata"));
    }

    @Test
    void getLocalityById_whenNotFound_returns404() throws Exception {
        when(localityService.getLocalityById(1L, 10L, 99L))
                .thenThrow(new LocalityNotExistDomainException("Locality doesn't exists with id: 99 in province with id: 10"));

        mockMvc.perform(get("/api/v1/countries/1/provinces/10/localities/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllLocalities_whenEmpty_returns204() throws Exception {
        when(localityService.getAllLocalities(1L, 10L)).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/countries/1/provinces/10/localities"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllLocalities_whenNotEmpty_returns200() throws Exception {
        var locality = locality();
        var dto = localityDto();
        when(localityService.getAllLocalities(1L, 10L)).thenReturn(List.of(locality));
        when(localityMapper.toGetDto(locality)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/countries/1/provinces/10/localities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(100));
    }

    @Test
    void addLocality_returns201() throws Exception {
        var body = new LocalityModifyDto("La Plata");
        var locality = locality();
        var dto = localityDto();
        when(localityService.addLocality(1L, 10L, "La Plata")).thenReturn(locality);
        when(localityMapper.toGetDto(locality)).thenReturn(dto);

        mockMvc.perform(post("/api/v1/countries/1/provinces/10/localities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(100));
    }

    @Test
    void updateLocality_returns200() throws Exception {
        var body = new LocalityModifyDto("Nuevo Nombre");
        var locality = locality();
        var dto = localityDto();
        when(localityService.updateLocality(1L, 10L, 100L, "Nuevo Nombre")).thenReturn(locality);
        when(localityMapper.toGetDto(locality)).thenReturn(dto);

        mockMvc.perform(put("/api/v1/countries/1/provinces/10/localities/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteLocality_returns200() throws Exception {
        doNothing().when(localityService).deleteLocality(1L, 10L, 100L);

        mockMvc.perform(delete("/api/v1/countries/1/provinces/10/localities/100"))
                .andExpect(status().isOk());
    }
}
