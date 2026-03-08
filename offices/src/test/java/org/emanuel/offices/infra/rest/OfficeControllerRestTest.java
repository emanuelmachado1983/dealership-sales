package org.emanuel.offices.infra.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.emanuel.offices.application.IOfficeService;
import org.emanuel.offices.domain.Office;
import org.emanuel.offices.domain.TypeOffice;
import org.emanuel.offices.domain.exceptions.OfficeBadRequestDomainException;
import org.emanuel.offices.domain.exceptions.OfficeNotExistsDomainException;
import org.emanuel.offices.infra.rest.dto.OfficeGetDto;
import org.emanuel.offices.infra.rest.dto.OfficeModifyDto;
import org.emanuel.offices.infra.rest.dto.TypeOfficeDto;
import org.emanuel.offices.infra.rest.mapper.OfficeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OfficeControllerRest.class)
class OfficeControllerRestTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    IOfficeService officeService;

    @MockBean
    OfficeMapper officeMapper;

    private OfficeGetDto officeDto() {
        return OfficeGetDto.builder()
                .id(10L)
                .idCountry(1L).idProvince(10L).idLocality(100L)
                .address("Av. Siempreviva 742")
                .name("Sucursal Springfield")
                .openingDate(LocalDateTime.of(2020, 1, 1, 0, 0))
                .typeOffice(new TypeOfficeDto(2L, "Sucursal"))
                .build();
    }

    private Office office() {
        return Office.builder()
                .id(10L)
                .typeOffice(new TypeOffice(2L, "Sucursal"))
                .build();
    }

    private OfficeModifyDto modifyDto() {
        return OfficeModifyDto.builder()
                .idCountry(1L).idProvince(10L).idLocality(100L)
                .address("Av. Siempreviva 742")
                .name("Sucursal Springfield")
                .openingDate(LocalDateTime.of(2020, 1, 1, 0, 0))
                .typeOfficeId(2L)
                .build();
    }

    @Test
    void getOfficeById_whenFound_returns200() throws Exception {
        var office = office();
        var dto = officeDto();
        when(officeService.getOfficeById(10L)).thenReturn(office);
        when(officeMapper.toDto(office)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/offices/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value("Sucursal Springfield"));
    }

    @Test
    void getOfficeById_whenNotFound_returns404() throws Exception {
        when(officeService.getOfficeById(99L))
                .thenThrow(new OfficeNotExistsDomainException("Office with id 99 does not exist or has been deleted"));

        mockMvc.perform(get("/api/v1/offices/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllOffices_whenEmpty_returns204() throws Exception {
        when(officeService.getAllOffices()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/offices"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllOffices_whenNotEmpty_returns200WithList() throws Exception {
        var office = office();
        var dto = officeDto();
        when(officeService.getAllOffices()).thenReturn(List.of(office));
        when(officeMapper.toDto(office)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/offices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(10));
    }

    @Test
    void addOffice_whenValid_returns201() throws Exception {
        var office = office();
        var dto = officeDto();
        when(officeMapper.toModel(eq(null), any(OfficeModifyDto.class))).thenReturn(office);
        when(officeService.addOffice(office)).thenReturn(office);
        when(officeMapper.toDto(office)).thenReturn(dto);

        mockMvc.perform(post("/api/v1/offices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyDto())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10));
    }

    @Test
    void addOffice_whenBadRequest_returns400() throws Exception {
        var office = office();
        when(officeMapper.toModel(eq(null), any(OfficeModifyDto.class))).thenReturn(office);
        when(officeService.addOffice(office))
                .thenThrow(new OfficeBadRequestDomainException("You cannot create a new office with type 'Central'."));

        mockMvc.perform(post("/api/v1/offices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyDto())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateOffice_whenValid_returns200() throws Exception {
        var office = office();
        var dto = officeDto();
        when(officeMapper.toModel(eq(10L), any(OfficeModifyDto.class))).thenReturn(office);
        when(officeService.updateOffice(10L, office)).thenReturn(office);
        when(officeMapper.toDto(office)).thenReturn(dto);

        mockMvc.perform(put("/api/v1/offices/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyDto())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10));
    }

    @Test
    void deleteOffice_whenExists_returns204() throws Exception {
        doNothing().when(officeService).deleteOffice(10L);

        mockMvc.perform(delete("/api/v1/offices/10"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteOffice_whenNotFound_returns404() throws Exception {
        doThrow(new OfficeNotExistsDomainException("Office with id 99 does not exist or has been deleted"))
                .when(officeService).deleteOffice(99L);

        mockMvc.perform(delete("/api/v1/offices/99"))
                .andExpect(status().isNotFound());
    }
}
