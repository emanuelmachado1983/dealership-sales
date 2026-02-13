package org.emanuel.offices.infra.db.mapper;

import org.emanuel.offices.domain.Country;
import org.emanuel.offices.domain.TypeOffice;
import org.emanuel.offices.infra.db.entity.CountryEntity;
import org.emanuel.offices.infra.db.entity.TypeOfficeEntity;
import org.springframework.stereotype.Component;

@Component
public class TypeOfficeEntityMapper {

    public TypeOffice toDomain(TypeOfficeEntity entity) {
        if (entity == null) return null;
        return TypeOffice.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public TypeOfficeEntity toEntity(TypeOffice domain) {
        if (domain == null) return null;
        return TypeOfficeEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .build();
    }
}

