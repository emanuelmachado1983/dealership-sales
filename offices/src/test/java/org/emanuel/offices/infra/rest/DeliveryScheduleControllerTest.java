package org.emanuel.offices.infra.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.emanuel.offices.application.IDeliveryScheduleService;
import org.emanuel.offices.domain.DeliverySchedule;
import org.emanuel.offices.domain.Office;
import org.emanuel.offices.domain.TypeOffice;
import org.emanuel.offices.domain.exceptions.DeliveryScheduleBadRequestDomainException;
import org.emanuel.offices.domain.exceptions.DeliveryScheduleNotExistsDomainException;
import org.emanuel.offices.infra.rest.dto.DeliveryScheduleGetDto;
import org.emanuel.offices.infra.rest.dto.DeliverySchedulePostDto;
import org.emanuel.offices.infra.rest.dto.OfficeGetDto;
import org.emanuel.offices.infra.rest.dto.TypeOfficeDto;
import org.emanuel.offices.infra.rest.mapper.DeliveryScheduleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DeliveryScheduleController.class)
class DeliveryScheduleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    IDeliveryScheduleService deliveryScheduleService;

    @MockBean
    DeliveryScheduleMapper deliveryScheduleMapper;

    private OfficeGetDto officeDto(Long id) {
        return OfficeGetDto.builder()
                .id(id).idCountry(1L).idProvince(10L).idLocality(100L)
                .name("Oficina " + id)
                .address("Calle Falsa 123")
                .openingDate(LocalDateTime.of(2020, 1, 1, 0, 0))
                .typeOffice(new TypeOfficeDto(2L, "Sucursal"))
                .build();
    }

    private DeliveryScheduleGetDto scheduleDto() {
        return new DeliveryScheduleGetDto(1L, officeDto(1L), officeDto(2L), 3);
    }

    private DeliverySchedule schedule() {
        return DeliverySchedule.builder()
                .id(1L)
                .officeFrom(Office.builder().id(1L).build())
                .officeTo(Office.builder().id(2L).build())
                .days(3)
                .build();
    }

    @Test
    void getDeliverySchedules_withoutParam_whenEmpty_returns204() throws Exception {
        when(deliveryScheduleService.getAllDeliverySchedules()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/delivery-schedules-configuration"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getDeliverySchedules_withoutParam_whenNotEmpty_returns200() throws Exception {
        var schedule = schedule();
        var dto = scheduleDto();
        when(deliveryScheduleService.getAllDeliverySchedules()).thenReturn(List.of(schedule));
        when(deliveryScheduleMapper.toDto(schedule)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/delivery-schedules-configuration"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].days").value(3));
    }

    @Test
    void getDeliverySchedules_withOfficeTo_whenNotEmpty_returns200() throws Exception {
        var schedule = schedule();
        var dto = scheduleDto();
        when(deliveryScheduleService.getAllDeliverySchedules(2L)).thenReturn(List.of(schedule));
        when(deliveryScheduleMapper.toDto(schedule)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/delivery-schedules-configuration?officeTo=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void getDeliverySchedules_withOfficeTo_whenOfficeNotFound_returns400() throws Exception {
        when(deliveryScheduleService.getAllDeliverySchedules(99L))
                .thenThrow(new DeliveryScheduleBadRequestDomainException("Office with id 99 does not exist."));

        mockMvc.perform(get("/api/v1/delivery-schedules-configuration?officeTo=99"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addDeliverySchedule_whenValid_returns201() throws Exception {
        var body = new DeliverySchedulePostDto(1L, 2L, 3);
        doNothing().when(deliveryScheduleService).addDeliverySchedule(1L, 2L, 3);

        mockMvc.perform(post("/api/v1/delivery-schedules-configuration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated());
    }

    @Test
    void addDeliverySchedule_whenBadRequest_returns400() throws Exception {
        var body = new DeliverySchedulePostDto(1L, 2L, -1);
        doThrow(new DeliveryScheduleBadRequestDomainException("Days must be greater than zero."))
                .when(deliveryScheduleService).addDeliverySchedule(1L, 2L, -1);

        mockMvc.perform(post("/api/v1/delivery-schedules-configuration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteDeliverySchedule_whenExists_returns204() throws Exception {
        doNothing().when(deliveryScheduleService).deleteDeliverySchedule(1L);

        mockMvc.perform(delete("/api/v1/delivery-schedules-configuration/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteDeliverySchedule_whenNotFound_returns404() throws Exception {
        doThrow(new DeliveryScheduleNotExistsDomainException("Delivery schedule with id 99 does not exist."))
                .when(deliveryScheduleService).deleteDeliverySchedule(99L);

        mockMvc.perform(delete("/api/v1/delivery-schedules-configuration/99"))
                .andExpect(status().isNotFound());
    }
}
