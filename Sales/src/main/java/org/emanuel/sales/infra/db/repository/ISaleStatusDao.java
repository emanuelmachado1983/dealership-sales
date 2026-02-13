package org.emanuel.sales.infra.db.repository;

import org.emanuel.sales.infra.db.dto.SaleStatusEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ISaleStatusDao extends CrudRepository<SaleStatusEntity, Long> {
    @Override
    List<SaleStatusEntity> findAll();
}
