package org.emanuel.sales.application.impl;

import lombok.RequiredArgsConstructor;
import org.emanuel.sales.application.ISaleStatusService;
import org.emanuel.sales.domain.SaleStatus;
import org.emanuel.sales.domain.db.repository.SaleStatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleStatusServiceImpl implements ISaleStatusService {
    private final SaleStatusRepository saleStatusRepository;

    @Override
    public List<SaleStatus> getAllSaleStates() {
        return saleStatusRepository.findAll();
    }
}
