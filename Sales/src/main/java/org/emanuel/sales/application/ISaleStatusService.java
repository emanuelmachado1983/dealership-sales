package org.emanuel.sales.application;

import org.emanuel.sales.domain.SaleStatus;

import java.util.List;

public interface ISaleStatusService {
    List<SaleStatus> getAllSaleStates();
}
