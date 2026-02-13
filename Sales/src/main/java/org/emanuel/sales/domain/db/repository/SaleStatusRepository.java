package org.emanuel.sales.domain.db.repository;

import org.emanuel.sales.domain.SaleStatus;

import java.util.List;

public interface SaleStatusRepository {
    List<SaleStatus> findAll();
}
