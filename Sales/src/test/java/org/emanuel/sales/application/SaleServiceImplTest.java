package org.emanuel.sales.application;

import org.emanuel.sales.application.impl.SaleServiceImpl;
import org.emanuel.sales.application.integration.customer.ICustomerService;
import org.emanuel.sales.application.integration.office.IOfficeService;
import org.emanuel.sales.application.integration.vehicle.IVehicleService;
import org.emanuel.sales.domain.Employee;
import org.emanuel.sales.domain.Sale;
import org.emanuel.sales.domain.SaleStatus;
import org.emanuel.sales.domain.db.repository.SaleRepository;
import org.emanuel.sales.domain.exceptions.EmployeeNotExistException;
import org.emanuel.sales.domain.exceptions.SaleBadRequestException;
import org.emanuel.sales.domain.exceptions.SaleNotExistException;
import org.emanuel.sales.domain.office.Office;
import org.emanuel.sales.domain.vehicle.ModelVehicle;
import org.emanuel.sales.domain.vehicle.StatusVehicle;
import org.emanuel.sales.domain.vehicle.TypeVehicle;
import org.emanuel.sales.domain.vehicle.Vehicle;
import org.emanuel.sales.infra.rest.mapper.SaleMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaleServiceImplTest {

    @Mock
    SaleRepository saleRepository;
    @Mock
    IVehicleService vehicleService;
    @Mock
    IOfficeService officeService;
    @Mock
    SaleMapper saleMapper;
    @Mock
    ICustomerService customerService;
    @Mock
    IEmployeeService employeeService;

    @InjectMocks
    SaleServiceImpl saleService;

    private static final Long EMPLOYEE_ID = 1L;
    private static final Long CUSTOMER_ID = 2L;
    private static final Long VEHICLE_ID = 10L;
    private static final Long OFFICE_SELLER_ID = 5L;
    private static final LocalDateTime SALE_DATE = LocalDateTime.of(2024, 6, 15, 12, 0);

    private Vehicle availableVehicleInSellerOffice() {
        return Vehicle.builder()
                .id(VEHICLE_ID)
                .model(new ModelVehicle(1L, "Toyota", "Corolla", 2023L, 25000.0))
                .status(new StatusVehicle(1L, "Disponible"))
                .type(new TypeVehicle(1L, "Sedan", 3))
                .officeLocationId(OFFICE_SELLER_ID)
                .build();
    }

    private Vehicle availableVehicleInCentralOffice() {
        return Vehicle.builder()
                .id(VEHICLE_ID)
                .model(new ModelVehicle(1L, "Toyota", "Corolla", 2023L, 25000.0))
                .status(new StatusVehicle(1L, "Disponible"))
                .type(new TypeVehicle(1L, "Sedan", 3))
                .officeLocationId(1L) // central
                .build();
    }

    private Office office() {
        return Office.builder().id(OFFICE_SELLER_ID).name("Sucursal Sur").build();
    }

    private Sale savedSale() {
        return Sale.builder()
                .id(100L)
                .vehicleId(VEHICLE_ID)
                .employee(new Employee(EMPLOYEE_ID))
                .customerId(CUSTOMER_ID)
                .ammount(25000.0)
                .date(SALE_DATE)
                .warrantyYears(3)
                .saleStatus(new SaleStatus(1L, "Pendiente"))
                .deliveryDays(3)
                .officeSeller(OFFICE_SELLER_ID)
                .build();
    }

    // --- getSaleById ---

    @Test
    void getSaleById_whenFound_returnsSale() {
        var sale = savedSale();
        when(saleRepository.findById(100L)).thenReturn(Optional.of(sale));

        var result = saleService.getSaleById(100L);

        assertThat(result).isEqualTo(sale);
    }

    @Test
    void getSaleById_whenNotFound_throwsException() {
        when(saleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> saleService.getSaleById(99L))
                .isInstanceOf(SaleNotExistException.class)
                .hasMessageContaining("99");
    }

    // --- getAllSales ---

    @Test
    void getAllSales_delegatesToRepositoryWithFilters() {
        var sales = List.of(savedSale());
        when(saleRepository.findAllByFilters(CUSTOMER_ID, EMPLOYEE_ID, null, null, VEHICLE_ID)).thenReturn(sales);

        var result = saleService.getAllSales(CUSTOMER_ID, EMPLOYEE_ID, null, null, VEHICLE_ID);

        assertThat(result).isEqualTo(sales);
    }

    // --- addSale ---

    @Test
    void addSale_whenVehicleNotFound_throwsBadRequest() {
        when(vehicleService.getVehicleById(VEHICLE_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> saleService.addSale(EMPLOYEE_ID, CUSTOMER_ID, VEHICLE_ID, SALE_DATE, OFFICE_SELLER_ID))
                .isInstanceOf(SaleBadRequestException.class)
                .hasMessageContaining(VEHICLE_ID.toString());
    }

    @Test
    void addSale_whenVehicleNotAvailable_throwsBadRequest() {
        var notAvailable = Vehicle.builder()
                .id(VEHICLE_ID)
                .status(new StatusVehicle(2L, "Reservado"))
                .officeLocationId(OFFICE_SELLER_ID)
                .build();
        when(vehicleService.getVehicleById(VEHICLE_ID)).thenReturn(Optional.of(notAvailable));

        assertThatThrownBy(() -> saleService.addSale(EMPLOYEE_ID, CUSTOMER_ID, VEHICLE_ID, SALE_DATE, OFFICE_SELLER_ID))
                .isInstanceOf(SaleBadRequestException.class)
                .hasMessageContaining("not available");
    }

    @Test
    void addSale_whenVehicleOfficeNotFound_throwsBadRequest() {
        var vehicle = availableVehicleInSellerOffice();
        when(vehicleService.getVehicleById(VEHICLE_ID)).thenReturn(Optional.of(vehicle));
        when(officeService.getOfficeById(OFFICE_SELLER_ID)).thenReturn(Optional.empty()); // vehicle's office

        assertThatThrownBy(() -> saleService.addSale(EMPLOYEE_ID, CUSTOMER_ID, VEHICLE_ID, SALE_DATE, OFFICE_SELLER_ID))
                .isInstanceOf(SaleBadRequestException.class);
    }

    @Test
    void addSale_whenOfficeSelllerIsCentral_throwsBadRequest() {
        var vehicle = availableVehicleInSellerOffice();
        when(vehicleService.getVehicleById(VEHICLE_ID)).thenReturn(Optional.of(vehicle));
        when(officeService.getOfficeById(OFFICE_SELLER_ID)).thenReturn(Optional.of(office()));

        // officeSellerId = 1L (central)
        assertThatThrownBy(() -> saleService.addSale(EMPLOYEE_ID, CUSTOMER_ID, VEHICLE_ID, SALE_DATE, 1L))
                .isInstanceOf(SaleBadRequestException.class)
                .hasMessageContaining("central office");
    }

    @Test
    void addSale_whenOfficeSellerNotFound_throwsBadRequest() {
        var vehicle = availableVehicleInSellerOffice();
        when(vehicleService.getVehicleById(VEHICLE_ID)).thenReturn(Optional.of(vehicle));
        when(officeService.getOfficeById(OFFICE_SELLER_ID))
                .thenReturn(Optional.of(office())) // vehicle's office call
                .thenReturn(Optional.empty());      // seller office call

        assertThatThrownBy(() -> saleService.addSale(EMPLOYEE_ID, CUSTOMER_ID, VEHICLE_ID, SALE_DATE, OFFICE_SELLER_ID))
                .isInstanceOf(SaleBadRequestException.class)
                .hasMessageContaining(OFFICE_SELLER_ID.toString());
    }

    @Test
    void addSale_whenVehicleInOtherOfficeNotCentral_throwsBadRequest() {
        // Vehicle is in office 7L, seller is office 5L — neither is central
        var vehicle = Vehicle.builder()
                .id(VEHICLE_ID)
                .model(new ModelVehicle(1L, "Toyota", "Corolla", 2023L, 25000.0))
                .status(new StatusVehicle(1L, "Disponible"))
                .type(new TypeVehicle(1L, "Sedan", 3))
                .officeLocationId(7L)
                .build();
        when(vehicleService.getVehicleById(VEHICLE_ID)).thenReturn(Optional.of(vehicle));
        when(officeService.getOfficeById(7L)).thenReturn(Optional.of(office())); // vehicle's office
        when(officeService.getOfficeById(OFFICE_SELLER_ID)).thenReturn(Optional.of(office())); // seller's office

        assertThatThrownBy(() -> saleService.addSale(EMPLOYEE_ID, CUSTOMER_ID, VEHICLE_ID, SALE_DATE, OFFICE_SELLER_ID))
                .isInstanceOf(SaleBadRequestException.class)
                .hasMessageContaining("seller's office");
    }

    @Test
    void addSale_whenCustomerNotExists_throwsBadRequest() {
        var vehicle = availableVehicleInSellerOffice();
        when(vehicleService.getVehicleById(VEHICLE_ID)).thenReturn(Optional.of(vehicle));
        when(officeService.getOfficeById(OFFICE_SELLER_ID)).thenReturn(Optional.of(office()));
        when(customerService.existsById(CUSTOMER_ID)).thenReturn(false);

        assertThatThrownBy(() -> saleService.addSale(EMPLOYEE_ID, CUSTOMER_ID, VEHICLE_ID, SALE_DATE, OFFICE_SELLER_ID))
                .isInstanceOf(SaleBadRequestException.class)
                .hasMessageContaining(CUSTOMER_ID.toString());
    }

    @Test
    void addSale_whenEmployeeNotExists_throwsBadRequest() {
        var vehicle = availableVehicleInSellerOffice();
        when(vehicleService.getVehicleById(VEHICLE_ID)).thenReturn(Optional.of(vehicle));
        when(officeService.getOfficeById(OFFICE_SELLER_ID)).thenReturn(Optional.of(office()));
        when(customerService.existsById(CUSTOMER_ID)).thenReturn(true);
        when(employeeService.getEmployeeById(EMPLOYEE_ID)).thenThrow(new EmployeeNotExistException("not found"));


        assertThatThrownBy(() -> saleService.addSale(EMPLOYEE_ID, CUSTOMER_ID, VEHICLE_ID, SALE_DATE, OFFICE_SELLER_ID))
                .isInstanceOf(SaleBadRequestException.class)
                .hasMessageContaining(EMPLOYEE_ID.toString());
    }

    @Test
    void addSale_whenNoDeliverySchedule_throwsBadRequest() {
        var vehicle = availableVehicleInSellerOffice();
        when(vehicleService.getVehicleById(VEHICLE_ID)).thenReturn(Optional.of(vehicle));
        when(officeService.getOfficeById(OFFICE_SELLER_ID)).thenReturn(Optional.of(office()));
        when(customerService.existsById(CUSTOMER_ID)).thenReturn(true);
        when(employeeService.getEmployeeById(EMPLOYEE_ID)).thenReturn(new Employee(EMPLOYEE_ID, "Emp", "123", "e@mail.com", "111"));
        when(officeService.getDeliveryScheduleIdByOfficeId(OFFICE_SELLER_ID, OFFICE_SELLER_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> saleService.addSale(EMPLOYEE_ID, CUSTOMER_ID, VEHICLE_ID, SALE_DATE, OFFICE_SELLER_ID))
                .isInstanceOf(SaleBadRequestException.class)
                .hasMessageContaining("delivery schedule");
    }

    @Test
    void addSale_whenVehicleInSellerOffice_success() {
        var vehicle = availableVehicleInSellerOffice();
        var saleSaved = Sale.builder().id(100L).build();
        var saleReturned = savedSale();

        when(vehicleService.getVehicleById(VEHICLE_ID)).thenReturn(Optional.of(vehicle));
        when(officeService.getOfficeById(OFFICE_SELLER_ID)).thenReturn(Optional.of(office()));
        when(customerService.existsById(CUSTOMER_ID)).thenReturn(true);
        when(employeeService.getEmployeeById(EMPLOYEE_ID)).thenReturn(new Employee(EMPLOYEE_ID, "Emp", "123", "e@mail.com", "111"));
        when(officeService.getDeliveryScheduleIdByOfficeId(OFFICE_SELLER_ID, OFFICE_SELLER_ID)).thenReturn(Optional.of(3));
        when(saleRepository.save(any(Sale.class))).thenReturn(saleSaved);
        when(saleRepository.findById(100L)).thenReturn(Optional.of(saleReturned));
        doNothing().when(vehicleService).putVehicle(VEHICLE_ID, 2L);

        var result = saleService.addSale(EMPLOYEE_ID, CUSTOMER_ID, VEHICLE_ID, SALE_DATE, OFFICE_SELLER_ID);

        assertThat(result).isEqualTo(saleReturned);
        verify(vehicleService).putVehicle(VEHICLE_ID, 2L);
        verify(saleRepository).save(any(Sale.class));
    }

    @Test
    void addSale_whenVehicleInCentralOffice_addsExtraDelivery() {
        var vehicle = availableVehicleInCentralOffice(); // officeLocationId = 1L
        var saleSaved = Sale.builder().id(100L).build();
        var saleReturned = savedSale();

        when(vehicleService.getVehicleById(VEHICLE_ID)).thenReturn(Optional.of(vehicle));
        when(officeService.getOfficeById(1L)).thenReturn(Optional.of(office()));   // vehicle's office (central)
        when(officeService.getOfficeById(OFFICE_SELLER_ID)).thenReturn(Optional.of(office())); // seller's office
        when(customerService.existsById(CUSTOMER_ID)).thenReturn(true);
        when(employeeService.getEmployeeById(EMPLOYEE_ID)).thenReturn(new Employee(EMPLOYEE_ID, "Emp", "123", "e@mail.com", "111"));
        when(officeService.getDeliveryScheduleIdByOfficeId(OFFICE_SELLER_ID, OFFICE_SELLER_ID)).thenReturn(Optional.of(2));
        when(officeService.getDeliveryScheduleIdByOfficeId(1L, OFFICE_SELLER_ID)).thenReturn(Optional.of(3));
        when(saleRepository.save(any(Sale.class))).thenReturn(saleSaved);
        when(saleRepository.findById(100L)).thenReturn(Optional.of(saleReturned));
        doNothing().when(vehicleService).putVehicle(VEHICLE_ID, 2L);

        saleService.addSale(EMPLOYEE_ID, CUSTOMER_ID, VEHICLE_ID, SALE_DATE, OFFICE_SELLER_ID);

        // Both delivery schedule calls should happen
        verify(officeService).getDeliveryScheduleIdByOfficeId(OFFICE_SELLER_ID, OFFICE_SELLER_ID);
        verify(officeService).getDeliveryScheduleIdByOfficeId(1L, OFFICE_SELLER_ID);
    }

    // --- patchSale ---

    @Test
    void patchSale_whenSaleNotFound_throwsException() {
        when(saleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> saleService.patchSale(99L, null, null, null))
                .isInstanceOf(SaleNotExistException.class)
                .hasMessageContaining("99");
    }

    @Test
    void patchSale_whenNoStatusChange_savesAndDoesNotChangeVehicle() {
        var existingSale = Sale.builder()
                .id(1L).vehicleId(VEHICLE_ID)
                .employee(new Employee(EMPLOYEE_ID)).customerId(CUSTOMER_ID)
                .saleStatus(new SaleStatus(1L, "Pendiente"))
                .build();
        var vehicle = Vehicle.builder().id(VEHICLE_ID).status(new StatusVehicle(1L, "Disponible")).build();

        when(saleRepository.findById(1L)).thenReturn(Optional.of(existingSale));
        when(vehicleService.getVehicleById(VEHICLE_ID)).thenReturn(Optional.of(vehicle));
        when(customerService.existsById(CUSTOMER_ID)).thenReturn(true);
        when(employeeService.getEmployeeById(EMPLOYEE_ID)).thenReturn(new Employee(EMPLOYEE_ID));
        when(saleRepository.save(any())).thenReturn(existingSale);

        saleService.patchSale(1L, null, null, null);

        verify(saleRepository).save(any());
        verify(vehicleService, never()).putVehicle(any(), any());
    }

    @Test
    void patchSale_whenCancelStatus_setsVehicleAvailable() {
        var existingSale = Sale.builder()
                .id(1L).vehicleId(VEHICLE_ID)
                .employee(new Employee(EMPLOYEE_ID)).customerId(CUSTOMER_ID)
                .saleStatus(new SaleStatus(1L, "Pendiente"))
                .build();
        var vehicle = Vehicle.builder().id(VEHICLE_ID).status(new StatusVehicle(2L, "Reservado")).build();

        when(saleRepository.findById(1L)).thenReturn(Optional.of(existingSale));
        when(vehicleService.getVehicleById(VEHICLE_ID)).thenReturn(Optional.of(vehicle));
        when(customerService.existsById(CUSTOMER_ID)).thenReturn(true);
        when(employeeService.getEmployeeById(EMPLOYEE_ID)).thenReturn(new Employee(EMPLOYEE_ID));
        when(saleRepository.save(any())).thenReturn(existingSale);
        doNothing().when(vehicleService).putVehicle(VEHICLE_ID, 1L);

        saleService.patchSale(1L, null, null, 3L); // status 3 = canceled

        verify(vehicleService).putVehicle(VEHICLE_ID, 1L); // vehicle → available
    }

    @Test
    void patchSale_whenCancelAndVehicleAlreadySold_throwsBadRequest() {
        var existingSale = Sale.builder()
                .id(1L).vehicleId(VEHICLE_ID)
                .employee(new Employee(EMPLOYEE_ID)).customerId(CUSTOMER_ID)
                .saleStatus(new SaleStatus(2L, "Completada"))
                .build();
        var vehicle = Vehicle.builder().id(VEHICLE_ID).status(new StatusVehicle(3L, "Vendido")).build();

        when(saleRepository.findById(1L)).thenReturn(Optional.of(existingSale));
        when(vehicleService.getVehicleById(VEHICLE_ID)).thenReturn(Optional.of(vehicle));

        assertThatThrownBy(() -> saleService.patchSale(1L, null, null, 3L))
                .isInstanceOf(SaleBadRequestException.class)
                .hasMessageContaining("already sold");
    }

    @Test
    void patchSale_whenCompleteStatus_setsVehicleSold() {
        var existingSale = Sale.builder()
                .id(1L).vehicleId(VEHICLE_ID)
                .employee(new Employee(EMPLOYEE_ID)).customerId(CUSTOMER_ID)
                .saleStatus(new SaleStatus(1L, "Pendiente"))
                .build();
        var vehicle = Vehicle.builder().id(VEHICLE_ID).status(new StatusVehicle(2L, "Reservado")).build();

        when(saleRepository.findById(1L)).thenReturn(Optional.of(existingSale));
        when(vehicleService.getVehicleById(VEHICLE_ID)).thenReturn(Optional.of(vehicle));
        when(customerService.existsById(CUSTOMER_ID)).thenReturn(true);
        when(employeeService.getEmployeeById(EMPLOYEE_ID)).thenReturn(new Employee(EMPLOYEE_ID));
        when(saleRepository.save(any())).thenReturn(existingSale);
        doNothing().when(vehicleService).putVehicle(VEHICLE_ID, 3L);

        saleService.patchSale(1L, null, null, 2L); // status 2 = completed

        verify(vehicleService).putVehicle(VEHICLE_ID, 3L); // vehicle → sold
    }

    @Test
    void patchSale_whenCustomerNotExists_throwsBadRequest() {
        var existingSale = Sale.builder()
                .id(1L).vehicleId(VEHICLE_ID)
                .employee(new Employee(EMPLOYEE_ID)).customerId(CUSTOMER_ID)
                .saleStatus(new SaleStatus(1L, "Pendiente"))
                .build();
        var vehicle = Vehicle.builder().id(VEHICLE_ID).status(new StatusVehicle(1L, "Disponible")).build();

        when(saleRepository.findById(1L)).thenReturn(Optional.of(existingSale));
        when(vehicleService.getVehicleById(VEHICLE_ID)).thenReturn(Optional.of(vehicle));
        when(customerService.existsById(CUSTOMER_ID)).thenReturn(false);

        assertThatThrownBy(() -> saleService.patchSale(1L, null, null, null))
                .isInstanceOf(SaleBadRequestException.class)
                .hasMessageContaining(CUSTOMER_ID.toString());
    }

    @Test
    void patchSale_whenEmployeeNotExists_throwsBadRequest() {
        var existingSale = Sale.builder()
                .id(1L).vehicleId(VEHICLE_ID)
                .employee(new Employee(EMPLOYEE_ID)).customerId(CUSTOMER_ID)
                .saleStatus(new SaleStatus(1L, "Pendiente"))
                .build();
        var vehicle = Vehicle.builder().id(VEHICLE_ID).status(new StatusVehicle(1L, "Disponible")).build();

        when(saleRepository.findById(1L)).thenReturn(Optional.of(existingSale));
        when(vehicleService.getVehicleById(VEHICLE_ID)).thenReturn(Optional.of(vehicle));
        when(customerService.existsById(CUSTOMER_ID)).thenReturn(true);
        when(employeeService.getEmployeeById(EMPLOYEE_ID)).thenThrow(new EmployeeNotExistException("not found"));

        assertThatThrownBy(() -> saleService.patchSale(1L, null, null, null))
                .isInstanceOf(SaleBadRequestException.class);
    }
}
