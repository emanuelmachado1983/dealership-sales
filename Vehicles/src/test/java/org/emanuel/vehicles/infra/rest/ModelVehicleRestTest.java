package org.emanuel.vehicles.infra.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.emanuel.vehicles.application.IModelVehicleService;
import org.emanuel.vehicles.domain.ModelVehicle;
import org.emanuel.vehicles.domain.exceptions.ModelVehicleNotExistException;
import org.emanuel.vehicles.infra.rest.dto.ModelVehicleGetDto;
import org.emanuel.vehicles.infra.rest.dto.ModelVehicleModifyDto;
import org.emanuel.vehicles.infra.rest.mapper.ModelVehicleMapper;
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

@WebMvcTest(ModelVehicleRest.class)
class ModelVehicleRestTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    IModelVehicleService modelVehicleService;

    @MockBean
    ModelVehicleMapper modelVehicleMapper;

    private ModelVehicle model() {
        return new ModelVehicle(1L, "Toyota", "Corolla", 2023L, 25000.0);
    }

    private ModelVehicleGetDto modelDto() {
        return new ModelVehicleGetDto(1L, "Toyota", "Corolla", 2023L, 25000.0);
    }

    private ModelVehicleModifyDto modifyDto() {
        return ModelVehicleModifyDto.builder()
                .brand("Toyota").model("Corolla").year(2023L).price(25000.0).build();
    }

    @Test
    void getModelVehicleById_whenFound_returns200() throws Exception {
        var model = model();
        when(modelVehicleService.getModelVehicleById(1L)).thenReturn(model);
        when(modelVehicleMapper.toGetDto(model)).thenReturn(modelDto());

        mockMvc.perform(get("/api/v1/model-vehicles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.brand").value("Toyota"))
                .andExpect(jsonPath("$.model").value("Corolla"));
    }

    @Test
    void getModelVehicleById_whenNotFound_returns404() throws Exception {
        when(modelVehicleService.getModelVehicleById(99L))
                .thenThrow(new ModelVehicleNotExistException("ModelVehicle not found with id: 99"));

        mockMvc.perform(get("/api/v1/model-vehicles/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllModelVehicles_whenEmpty_returns204() throws Exception {
        when(modelVehicleService.getAllModelVehicles()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/model-vehicles"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllModelVehicles_whenNotEmpty_returns200() throws Exception {
        var model = model();
        when(modelVehicleService.getAllModelVehicles()).thenReturn(List.of(model));
        when(modelVehicleMapper.toGetDto(model)).thenReturn(modelDto());

        mockMvc.perform(get("/api/v1/model-vehicles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void addModelVehicle_returns201() throws Exception {
        var model = model();
        when(modelVehicleMapper.toModel(eq(null), any(ModelVehicleModifyDto.class))).thenReturn(model);
        doNothing().when(modelVehicleService).addModelVehicle(model);

        mockMvc.perform(post("/api/v1/model-vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyDto())))
                .andExpect(status().isCreated());
    }

    @Test
    void updateModelVehicle_whenFound_returns204() throws Exception {
        var model = model();
        when(modelVehicleMapper.toModel(eq(1L), any(ModelVehicleModifyDto.class))).thenReturn(model);
        doNothing().when(modelVehicleService).updateModelVehicle(1L, model);

        mockMvc.perform(put("/api/v1/model-vehicles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyDto())))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateModelVehicle_whenNotFound_returns404() throws Exception {
        var model = model();
        when(modelVehicleMapper.toModel(eq(99L), any())).thenReturn(model);
        doThrow(new ModelVehicleNotExistException("Model vehicle not found with id: 99"))
                .when(modelVehicleService).updateModelVehicle(99L, model);

        mockMvc.perform(put("/api/v1/model-vehicles/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyDto())))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteModelVehicle_whenFound_returns204() throws Exception {
        doNothing().when(modelVehicleService).deleteModelVehicle(1L);

        mockMvc.perform(delete("/api/v1/model-vehicles/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteModelVehicle_whenNotFound_returns404() throws Exception {
        doThrow(new ModelVehicleNotExistException("Model vehicle not found with id: 99"))
                .when(modelVehicleService).deleteModelVehicle(99L);

        mockMvc.perform(delete("/api/v1/model-vehicles/99"))
                .andExpect(status().isNotFound());
    }
}
