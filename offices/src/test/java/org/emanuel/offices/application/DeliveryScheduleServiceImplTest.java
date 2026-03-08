package org.emanuel.offices.application;

import org.emanuel.offices.application.impl.DeliveryScheduleServiceImpl;
import org.emanuel.offices.domain.DeliverySchedule;
import org.emanuel.offices.domain.Office;
import org.emanuel.offices.domain.db.repository.DeliveryScheduleRepository;
import org.emanuel.offices.domain.exceptions.DeliveryScheduleBadRequestDomainException;
import org.emanuel.offices.domain.exceptions.DeliveryScheduleNotExistsDomainException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryScheduleServiceImplTest {

    @Mock
    IOfficeService officeService;

    @Mock
    DeliveryScheduleRepository deliveryScheduleRepository;

    @InjectMocks
    DeliveryScheduleServiceImpl deliveryScheduleService;

    private DeliverySchedule schedule() {
        return DeliverySchedule.builder()
                .id(1L)
                .officeFrom(Office.builder().id(1L).build())
                .officeTo(Office.builder().id(2L).build())
                .days(3)
                .build();
    }

    // --- getAllDeliverySchedules() ---

    @Test
    void getAllDeliverySchedules_returnsAllFromRepository() {
        var schedules = List.of(schedule());
        when(deliveryScheduleRepository.findAll()).thenReturn(schedules);

        var result = deliveryScheduleService.getAllDeliverySchedules();

        assertThat(result).isEqualTo(schedules);
    }

    // --- getAllDeliverySchedules(officeTo) ---

    @Test
    void getAllDeliverySchedules_withOfficeTo_whenOfficeExists_returnsFiltered() {
        var schedules = List.of(schedule());
        when(officeService.officeExists(2L)).thenReturn(true);
        when(deliveryScheduleRepository.findDeliveryScheduleByOfficeTo(2L)).thenReturn(schedules);

        var result = deliveryScheduleService.getAllDeliverySchedules(2L);

        assertThat(result).isEqualTo(schedules);
    }

    @Test
    void getAllDeliverySchedules_withOfficeTo_whenOfficeNotExist_throwsException() {
        when(officeService.officeExists(99L)).thenReturn(false);

        assertThatThrownBy(() -> deliveryScheduleService.getAllDeliverySchedules(99L))
                .isInstanceOf(DeliveryScheduleBadRequestDomainException.class)
                .hasMessageContaining("99");

        verify(deliveryScheduleRepository, never()).findDeliveryScheduleByOfficeTo(any());
    }

    // --- addDeliverySchedule ---

    @Test
    void addDeliverySchedule_whenOfficeFromNotExist_throwsException() {
        when(officeService.officeExists(99L)).thenReturn(false);

        assertThatThrownBy(() -> deliveryScheduleService.addDeliverySchedule(99L, 2L, 3))
                .isInstanceOf(DeliveryScheduleBadRequestDomainException.class)
                .hasMessageContaining("do not exist");
    }

    @Test
    void addDeliverySchedule_whenOfficeToNotExist_throwsException() {
        when(officeService.officeExists(1L)).thenReturn(true);
        when(officeService.officeExists(99L)).thenReturn(false);

        assertThatThrownBy(() -> deliveryScheduleService.addDeliverySchedule(1L, 99L, 3))
                .isInstanceOf(DeliveryScheduleBadRequestDomainException.class)
                .hasMessageContaining("do not exist");
    }

    @Test
    void addDeliverySchedule_whenDaysZeroOrNegative_throwsException() {
        when(officeService.officeExists(1L)).thenReturn(true);
        when(officeService.officeExists(2L)).thenReturn(true);

        assertThatThrownBy(() -> deliveryScheduleService.addDeliverySchedule(1L, 2L, 0))
                .isInstanceOf(DeliveryScheduleBadRequestDomainException.class)
                .hasMessageContaining("greater than zero");

        assertThatThrownBy(() -> deliveryScheduleService.addDeliverySchedule(1L, 2L, -5))
                .isInstanceOf(DeliveryScheduleBadRequestDomainException.class)
                .hasMessageContaining("greater than zero");
    }

    @Test
    void addDeliverySchedule_whenScheduleAlreadyExists_throwsException() {
        when(officeService.officeExists(1L)).thenReturn(true);
        when(officeService.officeExists(2L)).thenReturn(true);
        when(deliveryScheduleRepository.existsByOfficeFromAndOfficeTo(1L, 2L)).thenReturn(true);

        assertThatThrownBy(() -> deliveryScheduleService.addDeliverySchedule(1L, 2L, 3))
                .isInstanceOf(DeliveryScheduleBadRequestDomainException.class)
                .hasMessageContaining("already exists");
    }

    @Test
    void addDeliverySchedule_whenValid_savesSchedule() {
        when(officeService.officeExists(1L)).thenReturn(true);
        when(officeService.officeExists(2L)).thenReturn(true);
        when(deliveryScheduleRepository.existsByOfficeFromAndOfficeTo(1L, 2L)).thenReturn(false);

        deliveryScheduleService.addDeliverySchedule(1L, 2L, 5);

        var captor = ArgumentCaptor.forClass(DeliverySchedule.class);
        verify(deliveryScheduleRepository).save(captor.capture());
        assertThat(captor.getValue().getOfficeFrom().getId()).isEqualTo(1L);
        assertThat(captor.getValue().getOfficeTo().getId()).isEqualTo(2L);
        assertThat(captor.getValue().getDays()).isEqualTo(5);
    }

    // --- deleteDeliverySchedule ---

    @Test
    void deleteDeliverySchedule_whenExists_deletesById() {
        when(deliveryScheduleRepository.findById(1L)).thenReturn(Optional.of(schedule()));

        deliveryScheduleService.deleteDeliverySchedule(1L);

        verify(deliveryScheduleRepository).deleteById(1L);
    }

    @Test
    void deleteDeliverySchedule_whenNotFound_throwsException() {
        when(deliveryScheduleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> deliveryScheduleService.deleteDeliverySchedule(99L))
                .isInstanceOf(DeliveryScheduleNotExistsDomainException.class)
                .hasMessageContaining("99");

        verify(deliveryScheduleRepository, never()).deleteById(any());
    }
}
