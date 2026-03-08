package org.emanuel.offices.infra.rest;

import org.emanuel.offices.application.ITypeOfficeService;
import org.emanuel.offices.domain.TypeOffice;
import org.emanuel.offices.domain.exceptions.TypeOfficeNotExistsDomainException;
import org.emanuel.offices.infra.rest.dto.TypeOfficeDto;
import org.emanuel.offices.infra.rest.mapper.TypeOfficeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TypeControllerRest.class)
class TypeControllerRestTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ITypeOfficeService typeOfficeService;

    @MockBean
    TypeOfficeMapper typeOfficeMapper;

    @Test
    void getAllTypeOffices_whenEmpty_returns204() throws Exception {
        when(typeOfficeService.getTypeOffices()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/types"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllTypeOffices_whenNotEmpty_returns200() throws Exception {
        var type = new TypeOffice(1L, "Central");
        var dto = new TypeOfficeDto(1L, "Central");
        when(typeOfficeService.getTypeOffices()).thenReturn(List.of(type));
        when(typeOfficeMapper.toDto(type)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Central"));
    }

    @Test
    void getTypeOfficeById_whenFound_returns200() throws Exception {
        var type = new TypeOffice(2L, "Sucursal");
        var dto = new TypeOfficeDto(2L, "Sucursal");
        when(typeOfficeService.getTypeOfficeById(2L)).thenReturn(type);
        when(typeOfficeMapper.toDto(type)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/types/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Sucursal"));
    }

    @Test
    void getTypeOfficeById_whenNotFound_returns404() throws Exception {
        when(typeOfficeService.getTypeOfficeById(99L))
                .thenThrow(new TypeOfficeNotExistsDomainException("Office type with id 99 does not exist"));

        mockMvc.perform(get("/api/v1/types/99"))
                .andExpect(status().isNotFound());
    }
}
