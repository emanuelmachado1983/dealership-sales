package org.emanuel.sales.infra.db.repository.impl;

import lombok.RequiredArgsConstructor;
import org.emanuel.sales.domain.SaleStatus;
import org.emanuel.sales.domain.db.repository.SaleStatusRepository;
import org.emanuel.sales.infra.db.mapper.SaleStatusEntityMapper;
import org.emanuel.sales.infra.db.repository.ISaleStatusDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SaleStatusRepositoryImpl implements SaleStatusRepository {

    private final ISaleStatusDao saleStatusDao;
    private final SaleStatusEntityMapper saleStatusEntityMapper;

    @Override
    public List<SaleStatus> findAll() {
        return saleStatusDao.findAll().stream().map(saleStatusEntityMapper::toDomain).toList();
    }
}
