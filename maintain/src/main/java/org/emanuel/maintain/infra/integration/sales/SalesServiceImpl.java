package org.emanuel.maintain.infra.integration.sales;

import org.emanuel.maintain.application.integration.ISalesService;
import org.emanuel.maintain.infra.integration.sales.dto.SaleFeignGetDto;
import org.springframework.stereotype.Service;

@Service
public class SalesServiceImpl implements ISalesService {
    private final ISalesFeign salesFeign;
    private static final long SALE_STATUS_COMPLETED = 2L;

    public SalesServiceImpl(ISalesFeign salesFeign) {
        this.salesFeign = salesFeign;
    }

    @Override
    public SaleFeignGetDto findSales(Long idVehicle) {
        var sales = salesFeign.findAll(idVehicle);
        if (sales != null && !sales.isEmpty()) {
            return sales.stream().filter(sale -> sale.getSaleStatus().getId().equals(SALE_STATUS_COMPLETED))
                    .findFirst().orElse(null);
        }
        return null;
    }
}
