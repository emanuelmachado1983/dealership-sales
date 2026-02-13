package org.emanuel.offices.infra.db.mapper;

import org.emanuel.offices.domain.Locality;
import org.emanuel.offices.infra.db.entity.LocalityEntity;
import org.emanuel.offices.infra.db.entity.ProvinceEntity;
import org.springframework.stereotype.Component;

@Component
public class LocalityEntityMapper {

    public Locality toDomain(LocalityEntity entity) {
        if (entity == null) return null;
        return Locality.builder()
                .id(entity.getId())
                .name(entity.getName())
                .provinceId(entity.getProvince().getId())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public LocalityEntity toEntity(Locality domain) {
        if (domain == null) return null;
        return LocalityEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .province(ProvinceEntity.builder().id(domain.getProvinceId()).build())
                .deletedAt(domain.getDeletedAt())
                .build();
    }
}

