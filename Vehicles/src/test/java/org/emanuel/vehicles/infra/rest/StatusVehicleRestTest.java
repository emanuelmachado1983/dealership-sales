package org.emanuel.vehicles.infra.rest;

import org.emanuel.vehicles.application.IStatusVehicleService;
import org.emanuel.vehicles.domain.StatusVehicle;
import org.emanuel.vehicles.infra.rest.dto.StatusVehicleGetDto;
import org.emanuel.vehicles.infra.rest.mapper.StatusVehicleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StatusVehicleRest.class)
class StatusVehicleRestTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    IStatusVehicleService statusVehicleService;

    @MockBean
    StatusVehicleMapper statusVehicleMapper;

    @Test
    void getAllStates_whenEmpty_returns204() throws Exception {
        when(statusVehicleService.getAllStates()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/status-vehicles"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllStates_whenNotEmpty_returns200WithList() throws Exception {
        var status = new StatusVehicle(1L, "Disponible");
        var dto = new StatusVehicleGetDto(1L, "Disponible");
        when(statusVehicleService.getAllStates()).thenReturn(List.of(status));
        when(statusVehicleMapper.toGetDto(status)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/status-vehicles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Disponible"));
    }
}
