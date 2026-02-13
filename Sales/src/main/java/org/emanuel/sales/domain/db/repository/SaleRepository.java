package org.emanuel.sales.domain.db.repository;

import org.emanuel.sales.domain.Sale;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SaleRepository {
    Optional<Sale> findById(Long id);

    List<Sale> findAllByFilters(Long customerId,
                                Long employeeId,
                                LocalDateTime dateFrom,
                                LocalDateTime dateTo,
                                Long vehicleId);

    Sale save(Sale sale);
}
