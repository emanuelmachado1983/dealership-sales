package org.emanuel.maintain.infra.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.emanuel.maintain.application.IMechanicalRepairService;
import org.emanuel.maintain.domain.exceptions.MechanicalRepairBadRequestException;
import org.emanuel.maintain.domain.exceptions.MechanicalRepairNotExistException;
import org.emanuel.maintain.domain.exceptions.VehicleBadRequestException;
import org.emanuel.maintain.domain.exceptions.VehicleNotExistException;
import org.emanuel.maintain.infra.rest.dto.MechanicalRepairGetDto;
import org.emanuel.maintain.infra.rest.dto.MechanicalRepairModifyDto;
import org.emanuel.maintain.infra.rest.dto.MechanicalRepairPostDto;
import org.emanuel.maintain.infra.rest.mapper.MechanicalRepairMapper;
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

@WebMvcTest(MechanicalRepairRest.class)
class MechanicalRepairRestTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    IMechanicalRepairService mechanicalRepairService;

    @MockBean
    MechanicalRepairMapper mechanicalRepairMapper;

    private MechanicalRepairGetDto repairDto() {
        return new MechanicalRepairGetDto(1L, null, null, null, 10L, 50000L, true);
    }

    private MechanicalRepairPostDto postDto() {
        var dto = new MechanicalRepairPostDto();
        dto.setVehicleId(10L);
        dto.setKmUnit(50000L);
        return dto;
    }

    private MechanicalRepairModifyDto modifyDto() {
        return new MechanicalRepairModifyDto(null, null, null, 60000L);
    }

    // --- GET /{id} ---

    @Test
    void getMechanicalRepairById_whenFound_returns200() throws Exception {
        var repair = new org.emanuel.maintain.domain.MechanicalRepair();
        when(mechanicalRepairService.getMechanicalRepairById(1L)).thenReturn(repair);
        when(mechanicalRepairMapper.toGetDto(repair)).thenReturn(repairDto());

        mockMvc.perform(get("/api/v1/mechanical-repairs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.vehicleId").value(10));
    }

    @Test
    void getMechanicalRepairById_whenNotFound_returns404() throws Exception {
        when(mechanicalRepairService.getMechanicalRepairById(99L))
                .thenThrow(new MechanicalRepairNotExistException("MechanicalRepair not found with id: 99"));

        mockMvc.perform(get("/api/v1/mechanical-repairs/99"))
                .andExpect(status().isNotFound());
    }

    // --- GET / ---

    @Test
    void getAllMechanicalRepairs_whenEmpty_returns204() throws Exception {
        when(mechanicalRepairService.getAllMechanicalRepairs(null)).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/mechanical-repairs"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllMechanicalRepairs_whenNotEmpty_returns200WithList() throws Exception {
        var repair = new org.emanuel.maintain.domain.MechanicalRepair();
        when(mechanicalRepairService.getAllMechanicalRepairs(null)).thenReturn(List.of(repair));
        when(mechanicalRepairMapper.toGetDto(repair)).thenReturn(repairDto());

        mockMvc.perform(get("/api/v1/mechanical-repairs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void getAllMechanicalRepairs_withVehicleIdParam_passesItToService() throws Exception {
        when(mechanicalRepairService.getAllMechanicalRepairs(10L)).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/mechanical-repairs?customerId=10"))
                .andExpect(status().isNoContent());

        verify(mechanicalRepairService).getAllMechanicalRepairs(10L);
    }

    // --- POST / ---

    @Test
    void addMechanicalRepair_whenValid_returns201() throws Exception {
        doNothing().when(mechanicalRepairService).addMechanicalRepair(any(), any(), any(), any());

        mockMvc.perform(post("/api/v1/mechanical-repairs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto())))
                .andExpect(status().isCreated());
    }

    @Test
    void addMechanicalRepair_whenEnterAfterEstimated_returns400() throws Exception {
        doThrow(new MechanicalRepairBadRequestException("Enter date cannot be after the estimated delivery date."))
                .when(mechanicalRepairService).addMechanicalRepair(any(), any(), any(), any());

        mockMvc.perform(post("/api/v1/mechanical-repairs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addMechanicalRepair_whenVehicleNotFound_returns400() throws Exception {
        doThrow(new VehicleNotExistException("Vehicle not exists."))
                .when(mechanicalRepairService).addMechanicalRepair(any(), any(), any(), any());

        mockMvc.perform(post("/api/v1/mechanical-repairs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addMechanicalRepair_whenNoSaleAssociated_returns400() throws Exception {
        doThrow(new VehicleBadRequestException("The vehicle does not have a sale associated."))
                .when(mechanicalRepairService).addMechanicalRepair(any(), any(), any(), any());

        mockMvc.perform(post("/api/v1/mechanical-repairs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto())))
                .andExpect(status().isBadRequest());
    }

    // --- PUT /{id} ---

    @Test
    void updateMechanicalRepair_whenValid_returns204() throws Exception {
        doNothing().when(mechanicalRepairService).updateMechanicalRepair(any(), any(), any(), any(), any());

        mockMvc.perform(put("/api/v1/mechanical-repairs/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyDto())))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateMechanicalRepair_whenNotFound_returns404() throws Exception {
        doThrow(new MechanicalRepairNotExistException("MechanicalRepair not found with id: 99"))
                .when(mechanicalRepairService).updateMechanicalRepair(any(), any(), any(), any(), any());

        mockMvc.perform(put("/api/v1/mechanical-repairs/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyDto())))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateMechanicalRepair_whenVehicleNotInRepair_returns400() throws Exception {
        doThrow(new MechanicalRepairBadRequestException("The vehicle is already repaired."))
                .when(mechanicalRepairService).updateMechanicalRepair(any(), any(), any(), any(), any());

        mockMvc.perform(put("/api/v1/mechanical-repairs/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyDto())))
                .andExpect(status().isBadRequest());
    }

    // --- DELETE /{id} ---

    @Test
    void deleteMechanicalRepair_whenValid_returns204() throws Exception {
        doNothing().when(mechanicalRepairService).deleteMechanicalRepair(1L);

        mockMvc.perform(delete("/api/v1/mechanical-repairs/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteMechanicalRepair_whenNotFound_returns404() throws Exception {
        doThrow(new MechanicalRepairNotExistException("MechanicalRepair not found with id: 99"))
                .when(mechanicalRepairService).deleteMechanicalRepair(99L);

        mockMvc.perform(delete("/api/v1/mechanical-repairs/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteMechanicalRepair_whenAlreadyDelivered_returns400() throws Exception {
        doThrow(new MechanicalRepairBadRequestException("Cannot delete a mechanical repair that has already been delivered."))
                .when(mechanicalRepairService).deleteMechanicalRepair(1L);

        mockMvc.perform(delete("/api/v1/mechanical-repairs/1"))
                .andExpect(status().isBadRequest());
    }
}
