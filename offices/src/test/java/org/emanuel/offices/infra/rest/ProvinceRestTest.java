package org.emanuel.offices.infra.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.emanuel.offices.application.IProvinceService;
import org.emanuel.offices.domain.Country;
import org.emanuel.offices.domain.Province;
import org.emanuel.offices.domain.exceptions.ProvinceNotExistDomainException;
import org.emanuel.offices.infra.rest.dto.ProvinceGetDto;
import org.emanuel.offices.infra.rest.dto.ProvinceModifyDto;
import org.emanuel.offices.infra.rest.mapper.ProvinceMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProvinceRest.class)
class ProvinceRestTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    IProvinceService provinceService;

    @MockBean
    ProvinceMapper provinceMapper;

    private Province province() {
        return new Province(10L, "Buenos Aires", new Country(1L, "Argentina"));
    }

    private ProvinceGetDto provinceDto() {
        return new ProvinceGetDto(10L, "Buenos Aires", 1L);
    }

    @Test
    void getProvinceById_whenFound_returns200() throws Exception {
        var province = province();
        var dto = provinceDto();
        when(provinceService.getProvinceById(1L, 10L)).thenReturn(province);
        when(provinceMapper.toGetDto(province)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/countries/1/provinces/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value("Buenos Aires"));
    }

    @Test
    void getProvinceById_whenNotFound_returns404() throws Exception {
        when(provinceService.getProvinceById(1L, 99L))
                .thenThrow(new ProvinceNotExistDomainException("Province not found with id: 99"));

        mockMvc.perform(get("/api/v1/countries/1/provinces/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllProvinces_whenEmpty_returns204() throws Exception {
        when(provinceService.getAllProvinces(1L)).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/countries/1/provinces"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllProvinces_whenNotEmpty_returns200() throws Exception {
        var province = province();
        var dto = provinceDto();
        when(provinceService.getAllProvinces(1L)).thenReturn(List.of(province));
        when(provinceMapper.toGetDto(province)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/countries/1/provinces"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(10));
    }

    @Test
    void addProvince_returns201() throws Exception {
        var body = new ProvinceModifyDto("Buenos Aires");
        var province = province();
        var dto = provinceDto();
        when(provinceService.addProvince(1L, "Buenos Aires")).thenReturn(province);
        when(provinceMapper.toGetDto(province)).thenReturn(dto);

        mockMvc.perform(post("/api/v1/countries/1/provinces")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10));
    }

    @Test
    void updateProvince_returns200() throws Exception {
        var body = new ProvinceModifyDto("Nuevo Nombre");
        var province = province();
        var dto = provinceDto();
        when(provinceService.updateProvince(1L, 10L, "Nuevo Nombre")).thenReturn(province);
        when(provinceMapper.toGetDto(province)).thenReturn(dto);

        mockMvc.perform(put("/api/v1/countries/1/provinces/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteProvince_returns200() throws Exception {
        doNothing().when(provinceService).deleteProvince(1L, 10L);

        mockMvc.perform(delete("/api/v1/countries/1/provinces/10"))
                .andExpect(status().isOk());
    }
}
