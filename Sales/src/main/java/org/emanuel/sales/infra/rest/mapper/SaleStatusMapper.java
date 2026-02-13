package org.emanuel.sales.infra.rest.mapper;

import org.emanuel.sales.infra.rest.dto.SaleStatusDto;
import org.emanuel.sales.domain.SaleStatus;
import org.springframework.stereotype.Component;

@Component
public class SaleStatusMapper {
    public SaleStatusDto toDto(SaleStatus status) {
        return new SaleStatusDto(status.getId(), status.getName());
    }

}
