package org.emanuel.offices.infra.db.mapper;

import org.emanuel.offices.domain.Country;
import org.emanuel.offices.infra.db.entity.CountryEntity;
import org.springframework.stereotype.Component;

@Component
public class CountryEntityMapper {

    public Country toDomain(CountryEntity entity) {
        if (entity == null) return null;
        return Country.builder()
                .id(entity.getId())
                .name(entity.getName())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public CountryEntity toEntity(Country domain) {
        if (domain == null) return null;
        return CountryEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .deletedAt(domain.getDeletedAt())
                .build();
    }
}

