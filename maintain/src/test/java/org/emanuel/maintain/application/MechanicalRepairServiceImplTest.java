package org.emanuel.maintain.application;

import org.emanuel.maintain.application.impl.MechanicalRepairServiceImpl;
import org.emanuel.maintain.application.integration.ISalesService;
import org.emanuel.maintain.application.integration.IVehicleService;
import org.emanuel.maintain.domain.MechanicalRepair;
import org.emanuel.maintain.domain.StatusVehicleEnum;
import org.emanuel.maintain.domain.db.repository.MechanicalRepairRepository;
import org.emanuel.maintain.domain.exceptions.MechanicalRepairBadRequestException;
import org.emanuel.maintain.domain.exceptions.MechanicalRepairNotExistException;
import org.emanuel.maintain.domain.exceptions.VehicleBadRequestException;
import org.emanuel.maintain.domain.exceptions.VehicleNotExistException;
import org.emanuel.maintain.infra.integration.sales.dto.SaleFeignGetDto;
import org.emanuel.maintain.infra.integration.vehicles.dto.StatusVehicleFeignDto;
import org.emanuel.maintain.infra.integration.vehicles.dto.VehicleFeignDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MechanicalRepairServiceImplTest {

    @Mock
    MechanicalRepairRepository mechanicalRepairRepository;
    @Mock
    IVehicleService vehicleService;
    @Mock
    ISalesService salesService;

    @InjectMocks
    MechanicalRepairServiceImpl mechanicalRepairService;

    private static final Long VEHICLE_ID = 10L;
    private static final LocalDateTime ENTER = LocalDateTime.of(2024, 1, 1, 9, 0);
    private static final LocalDateTime ESTIMATED = LocalDateTime.of(2024, 1, 15, 9, 0);
    private static final LocalDateTime DELIVERED = LocalDateTime.of(2024, 1, 10, 9, 0);

    private VehicleFeignDto deliveredVehicle() {
        return VehicleFeignDto.builder()
                .id(VEHICLE_ID)
                .status(new StatusVehicleFeignDto(StatusVehicleEnum.DELIVERED.getId(), "Entregado"))
                .build();
    }

    private VehicleFeignDto inRepairVehicle() {
        return VehicleFeignDto.builder()
                .id(VEHICLE_ID)
                .status(new StatusVehicleFeignDto(StatusVehicleEnum.IN_REPAIR.getId(), "En reparación"))
                .build();
    }

    private MechanicalRepair repairInProgress() {
        return MechanicalRepair.builder()
                .id(1L)
                .vehicleId(VEHICLE_ID)
                .enterDate(ENTER)
                .deliveryDateEstimated(ESTIMATED)
                .deliveryDate(null)
                .kmUnit(50000L)
                .usingWarranty(true)
                .build();
    }

    // --- getMechanicalRepairById ---

    @Test
    void getMechanicalRepairById_whenFound_returnsRepair() {
        var repair = repairInProgress();
        when(mechanicalRepairRepository.findById(1L)).thenReturn(Optional.of(repair));

        var result = mechanicalRepairService.getMechanicalRepairById(1L);

        assertThat(result).isEqualTo(repair);
    }

    @Test
    void getMechanicalRepairById_whenNotFound_throwsException() {
        when(mechanicalRepairRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mechanicalRepairService.getMechanicalRepairById(99L))
                .isInstanceOf(MechanicalRepairNotExistException.class)
                .hasMessageContaining("99");
    }

    // --- getAllMechanicalRepairs ---

    @Test
    void getAllMechanicalRepairs_returnsListFromRepository() {
        var repairs = List.of(repairInProgress());
        when(mechanicalRepairRepository.findAllByFilters(VEHICLE_ID)).thenReturn(repairs);

        var result = mechanicalRepairService.getAllMechanicalRepairs(VEHICLE_ID);

        assertThat(result).isEqualTo(repairs);
    }

    // --- addMechanicalRepair ---

    @Test
    void addMechanicalRepair_whenEnterDateAfterEstimated_throwsBadRequest() {
        assertThatThrownBy(() -> mechanicalRepairService.addMechanicalRepair(
                ESTIMATED, ENTER, VEHICLE_ID, 50000L)) // enterDate = ESTIMATED > deliveryEstimated = ENTER
                .isInstanceOf(MechanicalRepairBadRequestException.class)
                .hasMessageContaining("Enter date cannot be after");
    }

    @Test
    void addMechanicalRepair_whenVehicleNotFound_throwsVehicleNotExist() {
        when(vehicleService.getVehicleById(VEHICLE_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mechanicalRepairService.addMechanicalRepair(ENTER, ESTIMATED, VEHICLE_ID, 50000L))
                .isInstanceOf(VehicleNotExistException.class);
    }

    @Test
    void addMechanicalRepair_whenVehicleNotDelivered_throwsBadRequest() {
        var soldVehicle = VehicleFeignDto.builder()
                .id(VEHICLE_ID)
                .status(new StatusVehicleFeignDto(StatusVehicleEnum.SOLD.getId(), "Vendido"))
                .build();
        when(vehicleService.getVehicleById(VEHICLE_ID)).thenReturn(Optional.of(soldVehicle));

        assertThatThrownBy(() -> mechanicalRepairService.addMechanicalRepair(ENTER, ESTIMATED, VEHICLE_ID, 50000L))
                .isInstanceOf(MechanicalRepairBadRequestException.class)
                .hasMessageContaining("not delivered");
    }

    @Test
    void addMechanicalRepair_whenSaleNull_throwsVehicleBadRequest() {
        when(vehicleService.getVehicleById(VEHICLE_ID)).thenReturn(Optional.of(deliveredVehicle()));
        when(salesService.findSales(VEHICLE_ID)).thenReturn(null);

        assertThatThrownBy(() -> mechanicalRepairService.addMechanicalRepair(ENTER, ESTIMATED, VEHICLE_ID, 50000L))
                .isInstanceOf(VehicleBadRequestException.class)
                .hasMessageContaining("warranty");
    }

    @Test
    void addMechanicalRepair_whenWithinWarranty_savesWithUsingWarrantyTrue() {
        // warranty expires 3 years after sale date → 2027, enter is 2024 → within warranty
        var sale = SaleFeignGetDto.builder()
                .date(LocalDateTime.of(2023, 1, 1, 0, 0))
                .warrantyYears(5)
                .build();
        when(vehicleService.getVehicleById(VEHICLE_ID)).thenReturn(Optional.of(deliveredVehicle()));
        when(salesService.findSales(VEHICLE_ID)).thenReturn(sale);
        when(mechanicalRepairRepository.save(any())).thenReturn(repairInProgress());
        doNothing().when(vehicleService).putVehicle(VEHICLE_ID, StatusVehicleEnum.IN_REPAIR.getId());

        mechanicalRepairService.addMechanicalRepair(ENTER, ESTIMATED, VEHICLE_ID, 50000L);

        var captor = ArgumentCaptor.forClass(MechanicalRepair.class);
        verify(mechanicalRepairRepository).save(captor.capture());
        assertThat(captor.getValue().getUsingWarranty()).isTrue();
        verify(vehicleService).putVehicle(VEHICLE_ID, StatusVehicleEnum.IN_REPAIR.getId());
    }

    @Test
    void addMechanicalRepair_whenOutsideWarranty_savesWithUsingWarrantyFalse() {
        // warranty expired 1 year after sale in 2020 → 2021, enter is 2024 → outside warranty
        var sale = SaleFeignGetDto.builder()
                .date(LocalDateTime.of(2020, 1, 1, 0, 0))
                .warrantyYears(1)
                .build();
        when(vehicleService.getVehicleById(VEHICLE_ID)).thenReturn(Optional.of(deliveredVehicle()));
        when(salesService.findSales(VEHICLE_ID)).thenReturn(sale);
        when(mechanicalRepairRepository.save(any())).thenReturn(repairInProgress());
        doNothing().when(vehicleService).putVehicle(VEHICLE_ID, StatusVehicleEnum.IN_REPAIR.getId());

        mechanicalRepairService.addMechanicalRepair(ENTER, ESTIMATED, VEHICLE_ID, 50000L);

        var captor = ArgumentCaptor.forClass(MechanicalRepair.class);
        verify(mechanicalRepairRepository).save(captor.capture());
        assertThat(captor.getValue().getUsingWarranty()).isFalse();
    }

    // --- updateMechanicalRepair ---

    @Test
    void updateMechanicalRepair_whenNotFound_throwsException() {
        when(mechanicalRepairRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mechanicalRepairService.updateMechanicalRepair(
                99L, ENTER, ESTIMATED, null, 50000L))
                .isInstanceOf(MechanicalRepairNotExistException.class)
                .hasMessageContaining("99");
    }

    @Test
    void updateMechanicalRepair_whenExistsByIdFalse_throwsException() {
        var repair = repairInProgress();
        when(mechanicalRepairRepository.findById(1L)).thenReturn(Optional.of(repair));
        when(mechanicalRepairRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> mechanicalRepairService.updateMechanicalRepair(
                1L, ENTER, ESTIMATED, null, 50000L))
                .isInstanceOf(MechanicalRepairNotExistException.class);
    }

    @Test
    void updateMechanicalRepair_whenEnterAfterEstimated_throwsBadRequest() {
        var repair = repairInProgress();
        when(mechanicalRepairRepository.findById(1L)).thenReturn(Optional.of(repair));
        when(mechanicalRepairRepository.existsById(1L)).thenReturn(true);

        assertThatThrownBy(() -> mechanicalRepairService.updateMechanicalRepair(
                1L, ESTIMATED, ENTER, null, 50000L)) // swapped: enter=ESTIMATED > estimated=ENTER
                .isInstanceOf(MechanicalRepairBadRequestException.class)
                .hasMessageContaining("Enter date cannot be after");
    }

    @Test
    void updateMechanicalRepair_whenDeliveryDateBeforeEnterDate_throwsBadRequest() {
        var repair = repairInProgress();
        when(mechanicalRepairRepository.findById(1L)).thenReturn(Optional.of(repair));
        when(mechanicalRepairRepository.existsById(1L)).thenReturn(true);
        // deliveryDate = ENTER (before ESTIMATED but same as ENTER)
        // Use a date strictly before ENTER
        var deliveryBeforeEnter = ENTER.minusDays(1);

        assertThatThrownBy(() -> mechanicalRepairService.updateMechanicalRepair(
                1L, ENTER, ESTIMATED, deliveryBeforeEnter, 50000L))
                .isInstanceOf(MechanicalRepairBadRequestException.class)
                .hasMessageContaining("Delivery date cannot be before");
    }

    @Test
    void updateMechanicalRepair_whenVehicleNotFound_throwsVehicleNotExist() {
        var repair = repairInProgress();
        when(mechanicalRepairRepository.findById(1L)).thenReturn(Optional.of(repair));
        when(mechanicalRepairRepository.existsById(1L)).thenReturn(true);
        when(vehicleService.getVehicleById(VEHICLE_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mechanicalRepairService.updateMechanicalRepair(
                1L, ENTER, ESTIMATED, null, 50000L))
                .isInstanceOf(VehicleNotExistException.class);
    }

    @Test
    void updateMechanicalRepair_whenVehicleNotInRepair_throwsBadRequest() {
        var repair = repairInProgress();
        when(mechanicalRepairRepository.findById(1L)).thenReturn(Optional.of(repair));
        when(mechanicalRepairRepository.existsById(1L)).thenReturn(true);
        when(vehicleService.getVehicleById(VEHICLE_ID)).thenReturn(Optional.of(deliveredVehicle())); // DELIVERED, not IN_REPAIR

        assertThatThrownBy(() -> mechanicalRepairService.updateMechanicalRepair(
                1L, ENTER, ESTIMATED, null, 50000L))
                .isInstanceOf(MechanicalRepairBadRequestException.class)
                .hasMessageContaining("already repaired");
    }

    @Test
    void updateMechanicalRepair_withoutDeliveryDate_savesAndDoesNotChangeVehicleStatus() {
        var repair = repairInProgress();
        when(mechanicalRepairRepository.findById(1L)).thenReturn(Optional.of(repair));
        when(mechanicalRepairRepository.existsById(1L)).thenReturn(true);
        when(vehicleService.getVehicleById(VEHICLE_ID)).thenReturn(Optional.of(inRepairVehicle()));
        when(mechanicalRepairRepository.save(any())).thenReturn(repair);

        mechanicalRepairService.updateMechanicalRepair(1L, ENTER, ESTIMATED, null, 60000L);

        verify(mechanicalRepairRepository).save(any());
        verify(vehicleService, never()).putVehicle(any(), any());
    }

    @Test
    void updateMechanicalRepair_withDeliveryDate_savesAndSetsVehicleDelivered() {
        var repair = repairInProgress();
        when(mechanicalRepairRepository.findById(1L)).thenReturn(Optional.of(repair));
        when(mechanicalRepairRepository.existsById(1L)).thenReturn(true);
        when(vehicleService.getVehicleById(VEHICLE_ID)).thenReturn(Optional.of(inRepairVehicle()));
        when(mechanicalRepairRepository.save(any())).thenReturn(repair);
        doNothing().when(vehicleService).putVehicle(VEHICLE_ID, StatusVehicleEnum.DELIVERED.getId());

        mechanicalRepairService.updateMechanicalRepair(1L, ENTER, ESTIMATED, DELIVERED, 60000L);

        verify(mechanicalRepairRepository).save(any());
        verify(vehicleService).putVehicle(VEHICLE_ID, StatusVehicleEnum.DELIVERED.getId());
    }

    // --- deleteMechanicalRepair ---

    @Test
    void deleteMechanicalRepair_whenNotFound_throwsException() {
        when(mechanicalRepairRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mechanicalRepairService.deleteMechanicalRepair(99L))
                .isInstanceOf(MechanicalRepairNotExistException.class)
                .hasMessageContaining("99");
    }

    @Test
    void deleteMechanicalRepair_whenAlreadyDelivered_throwsBadRequest() {
        var deliveredRepair = MechanicalRepair.builder()
                .id(1L).vehicleId(VEHICLE_ID)
                .enterDate(ENTER).deliveryDateEstimated(ESTIMATED)
                .deliveryDate(DELIVERED) // already delivered
                .kmUnit(50000L).usingWarranty(false)
                .build();
        when(mechanicalRepairRepository.findById(1L)).thenReturn(Optional.of(deliveredRepair));

        assertThatThrownBy(() -> mechanicalRepairService.deleteMechanicalRepair(1L))
                .isInstanceOf(MechanicalRepairBadRequestException.class)
                .hasMessageContaining("already been delivered");
    }

    @Test
    void deleteMechanicalRepair_whenVehicleNotFound_throwsVehicleNotExist() {
        var repair = repairInProgress();
        when(mechanicalRepairRepository.findById(1L)).thenReturn(Optional.of(repair));
        when(vehicleService.getVehicleById(VEHICLE_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> mechanicalRepairService.deleteMechanicalRepair(1L))
                .isInstanceOf(VehicleNotExistException.class);
    }

    @Test
    void deleteMechanicalRepair_whenVehicleNotInRepair_throwsBadRequest() {
        var repair = repairInProgress();
        when(mechanicalRepairRepository.findById(1L)).thenReturn(Optional.of(repair));
        when(vehicleService.getVehicleById(VEHICLE_ID)).thenReturn(Optional.of(deliveredVehicle()));

        assertThatThrownBy(() -> mechanicalRepairService.deleteMechanicalRepair(1L))
                .isInstanceOf(MechanicalRepairBadRequestException.class)
                .hasMessageContaining("already repaired");
    }

    @Test
    void deleteMechanicalRepair_whenValid_deletesAndSetsVehicleDelivered() {
        var repair = repairInProgress();
        when(mechanicalRepairRepository.findById(1L)).thenReturn(Optional.of(repair));
        when(vehicleService.getVehicleById(VEHICLE_ID)).thenReturn(Optional.of(inRepairVehicle()));
        doNothing().when(mechanicalRepairRepository).deleteById(1L);
        doNothing().when(vehicleService).putVehicle(VEHICLE_ID, StatusVehicleEnum.DELIVERED.getId());

        mechanicalRepairService.deleteMechanicalRepair(1L);

        verify(mechanicalRepairRepository).deleteById(1L);
        verify(vehicleService).putVehicle(VEHICLE_ID, StatusVehicleEnum.DELIVERED.getId());
    }
}
