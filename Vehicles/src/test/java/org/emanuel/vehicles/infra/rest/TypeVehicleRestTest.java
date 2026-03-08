package org.emanuel.vehicles.infra.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.emanuel.vehicles.application.ITypeVehicleService;
import org.emanuel.vehicles.domain.TypeVehicle;
import org.emanuel.vehicles.domain.exceptions.TypeVehicleNotExistException;
import org.emanuel.vehicles.infra.rest.dto.TypeVehicleGetDto;
import org.emanuel.vehicles.infra.rest.dto.TypeVehicleModifyDto;
import org.emanuel.vehicles.infra.rest.mapper.TypeVehicleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TypeVehicleRest.class)
class TypeVehicleRestTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ITypeVehicleService typeVehicleService;

    @MockBean
    TypeVehicleMapper typeVehicleMapper;

    private TypeVehicle typeVehicle() {
        return new TypeVehicle(1L, "Sedán", 3);
    }

    private TypeVehicleGetDto typeVehicleDto() {
        return new TypeVehicleGetDto(1L, "Sedán", 3);
    }

    private TypeVehicleModifyDto modifyDto() {
        return TypeVehicleModifyDto.builder().name("Sedán").warrantyYears(3).build();
    }

    @Test
    void getTypeVehicleById_whenFound_returns200() throws Exception {
        var type = typeVehicle();
        when(typeVehicleService.getTypeVehicleById(1L)).thenReturn(type);
        when(typeVehicleMapper.toGetDto(type)).thenReturn(typeVehicleDto());

        mockMvc.perform(get("/api/v1/type-vehicles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Sedán"))
                .andExpect(jsonPath("$.warrantyYears").value(3));
    }

    @Test
    void getTypeVehicleById_whenNotFound_returns404() throws Exception {
        when(typeVehicleService.getTypeVehicleById(99L))
                .thenThrow(new TypeVehicleNotExistException("Type vehicle not found with id: 99"));

        mockMvc.perform(get("/api/v1/type-vehicles/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllTypeVehicles_whenEmpty_returns204() throws Exception {
        when(typeVehicleService.getAllTypes()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/type-vehicles"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllTypeVehicles_whenNotEmpty_returns200() throws Exception {
        var type = typeVehicle();
        when(typeVehicleService.getAllTypes()).thenReturn(List.of(type));
        when(typeVehicleMapper.toGetDto(type)).thenReturn(typeVehicleDto());

        mockMvc.perform(get("/api/v1/type-vehicles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void addTypeVehicle_returns201() throws Exception {
        var type = typeVehicle();
        when(typeVehicleMapper.toModel(eq(null), any(TypeVehicleModifyDto.class))).thenReturn(type);
        doNothing().when(typeVehicleService).addTypeVehicle(type);

        mockMvc.perform(post("/api/v1/type-vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyDto())))
                .andExpect(status().isCreated());
    }

    @Test
    void updateTypeVehicle_whenFound_returns204() throws Exception {
        var type = typeVehicle();
        when(typeVehicleMapper.toModel(eq(1L), any(TypeVehicleModifyDto.class))).thenReturn(type);
        doNothing().when(typeVehicleService).updateTypeVehicle(1L, type);

        mockMvc.perform(put("/api/v1/type-vehicles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyDto())))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateTypeVehicle_whenNotFound_returns404() throws Exception {
        var type = typeVehicle();
        when(typeVehicleMapper.toModel(eq(99L), any())).thenReturn(type);
        doThrow(new TypeVehicleNotExistException("TypeVehicle not found with id: 99"))
                .when(typeVehicleService).updateTypeVehicle(99L, type);

        mockMvc.perform(put("/api/v1/type-vehicles/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyDto())))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTypeVehicle_whenFound_returns204() throws Exception {
        doNothing().when(typeVehicleService).deleteTypeVehicle(1L);

        mockMvc.perform(delete("/api/v1/type-vehicles/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteTypeVehicle_whenNotFound_returns404() throws Exception {
        doThrow(new TypeVehicleNotExistException("TypeVehicle not found with id: 99"))
                .when(typeVehicleService).deleteTypeVehicle(99L);

        mockMvc.perform(delete("/api/v1/type-vehicles/99"))
                .andExpect(status().isNotFound());
    }
}
