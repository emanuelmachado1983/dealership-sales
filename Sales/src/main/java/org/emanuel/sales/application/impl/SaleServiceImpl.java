package org.emanuel.sales.application.impl;

import lombok.RequiredArgsConstructor;
import org.emanuel.sales.application.IEmployeeService;
import org.emanuel.sales.application.ISaleService;
import org.emanuel.sales.application.integration.customer.ICustomerService;
import org.emanuel.sales.application.integration.office.IOfficeService;
import org.emanuel.sales.application.integration.vehicle.IVehicleService;
import org.emanuel.sales.application.util.GlobalConstants;
import org.emanuel.sales.domain.Employee;
import org.emanuel.sales.domain.Sale;
import org.emanuel.sales.domain.SaleStatus;
import org.emanuel.sales.domain.db.repository.SaleRepository;
import org.emanuel.sales.domain.exceptions.EmployeeNotExistException;
import org.emanuel.sales.domain.exceptions.SaleBadRequestException;
import org.emanuel.sales.domain.exceptions.SaleNotExistException;
import org.emanuel.sales.infra.rest.mapper.SaleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements ISaleService {
    private final SaleRepository saleRepository;
    private final IVehicleService vehicleService;
    private final IOfficeService officeService;
    private final SaleMapper saleMapper;
    private final ICustomerService customerService;
    private final IEmployeeService employeeService;
    private static final Long SALE_STATUS_PENDING_ID = 1L;
    private static final Long SALE_STATUS_COMPLETED_ID = 2L;
    private static final Long SALE_STATUS_CANCELED_ID = 3L;

    private static final Long VEHICLE_STATUS_AVAILABLE_ID = 1L;
    private static final Long VEHICLE_STATUS_RESERVED_ID = 2L;
    private static final Long VEHICLE_STATUS_SOLD = 3L;
    private static final Long VEHICLE_STATUS_DELIVERED = 4L;

    @Override
    public Sale getSaleById(Long id) {
        return saleRepository.findById(id).orElseThrow(() -> new SaleNotExistException("Sale not found with id: " + id));
    }

    @Override
    public List<Sale> getAllSales(Long customerId,
                                  Long employeeId,
                                  LocalDateTime dateFrom,
                                  LocalDateTime dateTo,
                                  Long vehicleId) {
        return saleRepository.findAllByFilters(
                customerId,
                employeeId,
                dateFrom,
                dateTo,
                vehicleId);
    }

    @Override
    @Transactional(rollbackFor = {SaleBadRequestException.class})
    public Sale addSale(Long employeeId,
                        Long customerId,
                        Long vehicleId,
                        LocalDateTime date,
                        Long officeSellerId) {

        var vehicle = vehicleService.getVehicleById(vehicleId);
        if (vehicle.isEmpty()) {
            throw new SaleBadRequestException("Vehicle with id " + vehicleId + " does not exist.");
        }
        if (!VEHICLE_STATUS_AVAILABLE_ID.equals(vehicle.get().getStatus().getId())) {
            throw new SaleBadRequestException("Vehicle with id " + vehicleId + " is not available for sale.");
        }

        var office = officeService.getOfficeById(vehicle.get().getOfficeLocationId());
        if (office.isEmpty()) {
            throw new SaleBadRequestException("Office of the vehicle with id " + vehicle.get().getOfficeLocationId() + " does not exist.");
        }

        if (GlobalConstants.CENTRAL_OFFICE_ID.equals(officeSellerId)) {
            throw new SaleBadRequestException("You cannot create a sale with the central office as the seller.");
        }

        var officeSeller = officeService.getOfficeById(officeSellerId);
        if (officeSeller.isEmpty()) {
            throw new SaleBadRequestException("Office with id " + officeSellerId + " does not exist.");
        }

        if (!Objects.equals(vehicle.get().getOfficeLocationId(), officeSellerId) &&
                !GlobalConstants.CENTRAL_OFFICE_ID.equals(vehicle.get().getOfficeLocationId())) {
            throw new SaleBadRequestException("You cannot sale a vehicle that is not in the seller's office or the central office.");
        }

        if (!customerService.existsById(customerId)) {
            throw new SaleBadRequestException("Non existent customerId: " + customerId);
        }

        try {
            employeeService.getEmployeeById(employeeId);
        } catch (EmployeeNotExistException e) {
            throw new SaleBadRequestException("Non existent employeeId: " + employeeId);
        }

        Integer deliveryDays = deliveryFrom(officeSellerId, officeSellerId);

        if (GlobalConstants.CENTRAL_OFFICE_ID.equals(vehicle.get().getOfficeLocationId())) {
            deliveryDays += deliveryFrom(GlobalConstants.CENTRAL_OFFICE_ID, officeSellerId);
        }

        Employee employee = new Employee();
        employee.setId(employeeId);
        SaleStatus saleStatus = new SaleStatus();
        saleStatus.setId(SALE_STATUS_PENDING_ID);

        Sale sale = new Sale();
        sale.setVehicleId(vehicleId);
        sale.setEmployee(employee);
        sale.setCustomerId(customerId);
        sale.setAmmount(vehicle.get().getModel().getPrice());
        sale.setDate(date);
        sale.setWarrantyYears(vehicle.get().getType().getWarrantyYears());
        sale.setSaleStatus(saleStatus);
        sale.setDeliveryDays(deliveryDays);
        sale.setOfficeSeller(officeSellerId);

        var saleSaved = saleRepository.save(sale);

        //Ahora modifico el estado del vehículo a vendido
        //TODO: en un futuro hacer esto por kafka.
        //TODO: debería usar un PATCH para modificar el estado del vehículo. Ahora el PATCH me está
        //      tirando error con la librería de Feign. Tengo que investigar por qué.
        changeStatusOfVehicle(vehicle.get().getId(), VEHICLE_STATUS_RESERVED_ID);

        try {
            return getSaleById(saleSaved.getId());
        } catch (SaleNotExistException e) {
            throw new RuntimeException("Error inesperado: la venta recién guardada no existe.", e);
        }

    }

    @Override
    @Transactional(rollbackFor = {SaleBadRequestException.class})
    public void patchSale(Long saleId,
                          Long employeeId,
                          Long customerId,
                          Long statusId) {
        var saleToSave = saleRepository.findById(saleId)
                .orElseThrow(() -> new SaleNotExistException("Sale with id " + saleId + " does not exist"));
        saleToSave.setEmployee(employeeId == null ? saleToSave.getEmployee() : new Employee(employeeId));
        saleToSave.setCustomerId(customerId == null ? saleToSave.getCustomerId() : customerId);
        saleToSave.setSaleStatus(statusId == null ? saleToSave.getSaleStatus() : new SaleStatus(statusId));
        var vehicle = vehicleService.getVehicleById(saleToSave.getVehicleId());
        if (vehicle.isEmpty()) {
            throw new SaleBadRequestException("Vehicle with id " + saleToSave.getVehicleId() + " does not exist.");
        }
        if (SALE_STATUS_CANCELED_ID.equals(statusId)) {
            if (vehicle.get().getStatus().getId() > 2) {
                throw new SaleBadRequestException("Vehicle with id " + saleToSave.getVehicleId() + " already sold, you cannot cancel the sale.");
            }
        }

        if (!customerService.existsById(saleToSave.getCustomerId())) {
            throw new SaleBadRequestException("Non existent customerId: " + saleToSave.getCustomerId());
        }

        try {
            employeeService.getEmployeeById(saleToSave.getEmployee().getId());
        } catch (EmployeeNotExistException e) {
            throw new SaleBadRequestException("Non existent employeeId: " + employeeId);
        }

        var saved = saleRepository.save(saleToSave);

        if (SALE_STATUS_CANCELED_ID.equals(statusId)) {
            changeStatusOfVehicle(vehicle.get().getId(), VEHICLE_STATUS_AVAILABLE_ID);
        }
        if (SALE_STATUS_COMPLETED_ID.equals(statusId)) {
            changeStatusOfVehicle(vehicle.get().getId(), VEHICLE_STATUS_SOLD);
        }
    }


    private void changeStatusOfVehicle(Long idVehicle, Long idStatus) {
        try {
            vehicleService.putVehicle(idVehicle, idStatus);
        } catch (Exception e) {
            throw new SaleBadRequestException("Error updating vehicle status: " + e.getMessage());
        }
    }


    private Integer deliveryFrom(Long officeFromId, Long officeToId) {
        var delivery = officeService.getDeliveryScheduleIdByOfficeId(officeFromId, officeToId);
        if (delivery.isEmpty() && Objects.equals(officeFromId, officeToId)) {
            throw new SaleBadRequestException("The office with id" + officeFromId + " does not have a delivery schedule to itself.");
        }
        if (delivery.isEmpty() && !Objects.equals(officeFromId, officeToId)) {
            throw new SaleBadRequestException("The office with id " + officeToId + " does not have a delivery schedule of " + officeFromId);
        }

        return delivery.get();
    }

}
