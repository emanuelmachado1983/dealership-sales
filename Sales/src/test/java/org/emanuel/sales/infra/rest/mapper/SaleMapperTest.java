package org.emanuel.sales.infra.rest.mapper;

import org.emanuel.sales.domain.Employee;
import org.emanuel.sales.domain.Sale;
import org.emanuel.sales.domain.SaleStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class SaleMapperTest {

    private final EmployeeMapper employeeMapper = new EmployeeMapper();
    private final SaleStatusMapper saleStatusMapper = new SaleStatusMapper();
    private final SaleMapper saleMapper = new SaleMapper(employeeMapper, saleStatusMapper);

    private static final LocalDateTime SALE_DATE = LocalDateTime.of(2024, 6, 15, 12, 0);

    @Test
    void toGetDto_mapsAllFields() {
        var employee = new Employee(1L, "Juan Perez", "12345678", "juan@mail.com", "1122334455");
        var status = new SaleStatus(1L, "Pendiente");
        var sale = Sale.builder()
                .id(10L)
                .employee(employee)
                .customerId(2L)
                .vehicleId(20L)
                .ammount(25000.0)
                .date(SALE_DATE)
                .warrantyYears(3)
                .saleStatus(status)
                .deliveryDays(5)
                .officeSeller(5L)
                .build();

        var dto = saleMapper.toGetDto(sale);

        assertThat(dto.getId()).isEqualTo(10L);
        assertThat(dto.getCustomerId()).isEqualTo(2L);
        assertThat(dto.getVehicleId()).isEqualTo(20L);
        assertThat(dto.getAmmount()).isEqualTo(25000.0);
        assertThat(dto.getDate()).isEqualTo(SALE_DATE);
        assertThat(dto.getWarrantyYears()).isEqualTo(3);
        assertThat(dto.getDeliveryDate()).isEqualTo(SALE_DATE.plusDays(5));
        assertThat(dto.getEmployeeGetDto().getId()).isEqualTo(1L);
        assertThat(dto.getEmployeeGetDto().getName()).isEqualTo("Juan Perez");
        assertThat(dto.getSaleStatusDto().getId()).isEqualTo(1L);
        assertThat(dto.getSaleStatusDto().getName()).isEqualTo("Pendiente");
    }

    @Test
    void toGetDto_deliveryDateIsDatePlusDeliveryDays() {
        var sale = Sale.builder()
                .id(1L)
                .employee(new Employee(1L, "X", "X", "x@x.com", "111"))
                .customerId(1L).vehicleId(1L).ammount(1000.0)
                .date(SALE_DATE)
                .warrantyYears(1)
                .saleStatus(new SaleStatus(1L, "Pendiente"))
                .deliveryDays(10)
                .build();

        var dto = saleMapper.toGetDto(sale);

        assertThat(dto.getDeliveryDate()).isEqualTo(SALE_DATE.plusDays(10));
    }
}
