package org.emanuel.maintain.application.integration;


import org.emanuel.maintain.infra.integration.sales.dto.SaleFeignGetDto;

public interface ISalesService {
    SaleFeignGetDto findSales(Long idVehicle);
}
