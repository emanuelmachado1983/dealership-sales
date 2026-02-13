package org.emanuel.sales.infra.db.mapper;

import org.emanuel.sales.domain.SaleStatus;
import org.emanuel.sales.infra.db.dto.SaleStatusEntity;
import org.springframework.stereotype.Component;

@Component
public class SaleStatusEntityMapper {

    public SaleStatus toDomain(SaleStatusEntity entity) {
        if (entity == null) return null;
        return SaleStatus.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public SaleStatusEntity toEntity(SaleStatus status) {
        if (status == null) return null;
        return SaleStatusEntity.builder()
                .id(status.getId())
                .name(status.getName())
                .build();
    }

}
