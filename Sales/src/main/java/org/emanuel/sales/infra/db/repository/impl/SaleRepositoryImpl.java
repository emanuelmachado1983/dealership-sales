package org.emanuel.sales.infra.db.repository.impl;

import lombok.RequiredArgsConstructor;
import org.emanuel.sales.domain.Sale;
import org.emanuel.sales.domain.db.repository.SaleRepository;
import org.emanuel.sales.infra.db.mapper.SaleEntityMapper;
import org.emanuel.sales.infra.db.repository.ISaleDao;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SaleRepositoryImpl implements SaleRepository {
    private final ISaleDao saleDao;
    private final SaleEntityMapper saleEntityMapper;

    @Override
    public Optional<Sale> findById(Long id) {
        return saleDao.findById(id).map(saleEntityMapper::toDomain);
    }

    @Override
    public List<Sale> findAllByFilters(Long customerId, Long employeeId, LocalDateTime dateFrom, LocalDateTime dateTo, Long vehicleId) {
        return saleDao.findAllByFilters(customerId, employeeId, dateFrom, dateTo, vehicleId)
                .stream()
                .map(saleEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Sale save(Sale sale) {
        return saleEntityMapper.toDomain(saleDao.save(saleEntityMapper.toEntity(sale)));
    }
}
