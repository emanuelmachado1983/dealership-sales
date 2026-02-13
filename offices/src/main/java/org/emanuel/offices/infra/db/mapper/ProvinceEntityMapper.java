package org.emanuel.offices.infra.db.mapper;

import lombok.RequiredArgsConstructor;
import org.emanuel.offices.domain.Province;
import org.emanuel.offices.infra.db.entity.ProvinceEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProvinceEntityMapper {

    private final CountryEntityMapper countryEntityMapper;

    public Province toDomain(ProvinceEntity entity) {
        if (entity == null) return null;
        return Province.builder()
                .id(entity.getId())
                .name(entity.getName())
                .deletedAt(entity.getDeletedAt())
                .country(countryEntityMapper.toDomain(entity.getCountry()))
                .build();
    }

    public ProvinceEntity toEntity(Province domain) {
        if (domain == null) return null;
        return ProvinceEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .deletedAt(domain.getDeletedAt())
                .country(countryEntityMapper.toEntity(domain.getCountry()))
                .build();
    }
}
