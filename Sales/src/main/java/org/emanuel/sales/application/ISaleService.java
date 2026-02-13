package org.emanuel.sales.application;

import org.emanuel.sales.domain.Sale;

import java.time.LocalDateTime;
import java.util.List;

public interface ISaleService {
    Sale getSaleById(Long id);

    List<Sale> getAllSales(Long customerId,
                           Long employeeId,
                           LocalDateTime dateFrom,
                           LocalDateTime dateTo,
                           Long vehicleId);

    Sale addSale(Long employeeId,
                 Long customerId,
                 Long vehicleId,
                 LocalDateTime date,
                 Long idOfficeSeller);

    void patchSale(Long saleId,
                   Long employeeId,
                   Long customerId,
                   Long statusId);

}
