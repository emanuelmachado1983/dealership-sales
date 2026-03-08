package org.emanuel.sales.application;

import org.emanuel.sales.application.impl.SaleStatusServiceImpl;
import org.emanuel.sales.domain.SaleStatus;
import org.emanuel.sales.domain.db.repository.SaleStatusRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaleStatusServiceImplTest {

    @Mock
    SaleStatusRepository saleStatusRepository;

    @InjectMocks
    SaleStatusServiceImpl saleStatusService;

    @Test
    void getAllSaleStates_returnsListFromRepository() {
        var statuses = List.of(
                new SaleStatus(1L, "Pendiente"),
                new SaleStatus(2L, "Completada"),
                new SaleStatus(3L, "Cancelada")
        );
        when(saleStatusRepository.findAll()).thenReturn(statuses);

        var result = saleStatusService.getAllSaleStates();

        assertThat(result).isEqualTo(statuses);
    }

    @Test
    void getAllSaleStates_whenEmpty_returnsEmptyList() {
        when(saleStatusRepository.findAll()).thenReturn(List.of());

        var result = saleStatusService.getAllSaleStates();

        assertThat(result).isEmpty();
    }
}
