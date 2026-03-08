package org.emanuel.vehicles.infra.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.emanuel.vehicles.application.IVehicleService;
import org.emanuel.vehicles.domain.ModelVehicle;
import org.emanuel.vehicles.domain.StatusVehicle;
import org.emanuel.vehicles.domain.TypeVehicle;
import org.emanuel.vehicles.domain.Vehicle;
import org.emanuel.vehicles.domain.exceptions.VehicleBadRequestException;
import org.emanuel.vehicles.domain.exceptions.VehicleNotExistException;
import org.emanuel.vehicles.infra.rest.dto.*;
import org.emanuel.vehicles.infra.rest.mapper.VehicleMapper;
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

@WebMvcTest(VehicleRest.class)
class VehicleRestTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    IVehicleService vehicleService;

    @MockBean
    VehicleMapper vehicleMapper;

    private Vehicle vehicle() {
        return Vehicle.builder()
                .id(10L)
                .model(new ModelVehicle(1L, "Toyota", "Corolla", 2023L, 25000.0))
                .description("Impecable")
                .status(new StatusVehicle(1L, "Disponible"))
                .type(new TypeVehicle(1L, "Sedán", 3))
                .patent("ABC123")
                .officeLocationId(5L)
                .build();
    }

    private VehicleGetDto vehicleDto() {
        return VehicleGetDto.builder()
                .id(10L)
                .model(new ModelVehicleGetDto(1L, "Toyota", "Corolla", 2023L, 25000.0))
                .description("Impecable")
                .status(new StatusVehicleGetDto(1L, "Disponible"))
                .type(new TypeVehicleGetDto(1L, "Sedán", 3))
                .patent("ABC123")
                .officeLocationId(5L)
                .build();
    }

    private VehicleModifyDto modifyDto() {
        return VehicleModifyDto.builder()
                .modelId(1L).statusId(1L).typeId(1L)
                .description("Impecable").patent("ABC123").officeLocationId(5L)
                .build();
    }

    // --- GET /{id} ---

    @Test
    void getVehicleById_whenFound_returns200() throws Exception {
        var vehicle = vehicle();
        when(vehicleService.getVehicleById(10L)).thenReturn(vehicle);
        when(vehicleMapper.toGetDto(vehicle)).thenReturn(vehicleDto());

        mockMvc.perform(get("/api/v1/vehicles/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.patent").value("ABC123"));
    }

    @Test
    void getVehicleById_whenNotFound_returns404() throws Exception {
        when(vehicleService.getVehicleById(99L))
                .thenThrow(new VehicleNotExistException("Vehicle not found with id: 99"));

        mockMvc.perform(get("/api/v1/vehicles/99"))
                .andExpect(status().isNotFound());
    }

    // --- GET / (con filtros opcionales) ---

    @Test
    void getAllVehicles_withoutFilters_whenEmpty_returns204() throws Exception {
        when(vehicleService.getAllVehicles(null, null, null, null)).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/vehicles"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllVehicles_withFilters_whenNotEmpty_returns200() throws Exception {
        var vehicle = vehicle();
        when(vehicleService.getAllVehicles(5L, 1L, 1L, 1L)).thenReturn(List.of(vehicle));
        when(vehicleMapper.toGetDto(vehicle)).thenReturn(vehicleDto());

        mockMvc.perform(get("/api/v1/vehicles?officeLocationId=5&stateId=1&modelId=1&typeId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(10));
    }

    // --- POST / ---

    @Test
    void addVehicle_whenValid_returns201() throws Exception {
        var vehicle = vehicle();
        when(vehicleMapper.modifyToModel(eq(null), any(VehicleModifyDto.class))).thenReturn(vehicle);
        doNothing().when(vehicleService).addVehicle(vehicle);

        mockMvc.perform(post("/api/v1/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyDto())))
                .andExpect(status().isCreated());
    }

    @Test
    void addVehicle_whenOfficeNotExist_returns400() throws Exception {
        var vehicle = vehicle();
        when(vehicleMapper.modifyToModel(eq(null), any())).thenReturn(vehicle);
        doThrow(new VehicleBadRequestException("The office with id 5 does not exist."))
                .when(vehicleService).addVehicle(vehicle);

        mockMvc.perform(post("/api/v1/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyDto())))
                .andExpect(status().isBadRequest());
    }

    // --- PUT /{id} ---

    @Test
    void updateVehicle_whenValid_returns204() throws Exception {
        var vehicle = vehicle();
        when(vehicleMapper.modifyToModel(eq(10L), any(VehicleModifyDto.class))).thenReturn(vehicle);
        doNothing().when(vehicleService).updateVehicle(10L, vehicle);

        mockMvc.perform(put("/api/v1/vehicles/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyDto())))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateVehicle_whenNotFound_returns404() throws Exception {
        var vehicle = vehicle();
        when(vehicleMapper.modifyToModel(eq(99L), any())).thenReturn(vehicle);
        doThrow(new VehicleNotExistException("Vehicle not found with id: 99"))
                .when(vehicleService).updateVehicle(99L, vehicle);

        mockMvc.perform(put("/api/v1/vehicles/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyDto())))
                .andExpect(status().isNotFound());
    }

    // --- PUT /{id}/state ---

    @Test
    void updateVehicleState_whenValid_returns204() throws Exception {
        var body = new VehicleStateModifyDto(2L);
        doNothing().when(vehicleService).updateStateVehicle(10L, 2L);

        mockMvc.perform(put("/api/v1/vehicles/10/state")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateVehicleState_whenNotFound_returns404() throws Exception {
        var body = new VehicleStateModifyDto(2L);
        doThrow(new VehicleNotExistException("Vehicle not found with id: 99"))
                .when(vehicleService).updateStateVehicle(99L, 2L);

        mockMvc.perform(put("/api/v1/vehicles/99/state")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isNotFound());
    }
}
