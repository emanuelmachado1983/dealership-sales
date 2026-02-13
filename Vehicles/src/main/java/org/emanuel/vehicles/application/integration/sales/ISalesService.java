package org.emanuel.vehicles.application.integration.sales;

import org.emanuel.vehicles.infra.integration.sales.dto.SaleFeignGetDto;

public interface ISalesService {
    SaleFeignGetDto findSales(Long idVehicle);
}
