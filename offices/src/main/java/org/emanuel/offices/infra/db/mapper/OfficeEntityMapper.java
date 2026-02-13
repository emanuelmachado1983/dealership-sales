package org.emanuel.offices.infra.db.mapper;

import lombok.RequiredArgsConstructor;
import org.emanuel.offices.domain.Country;
import org.emanuel.offices.domain.Office;
import org.emanuel.offices.infra.db.entity.CountryEntity;
import org.emanuel.offices.infra.db.entity.OfficeEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OfficeEntityMapper {
    private final TypeOfficeEntityMapper typeOfficeEntityMapper;

    public Office toDomain(OfficeEntity entity) {
        if (entity == null) return null;
        return Office.builder()
                .id(entity.getId())
                .idCountry(entity.getIdCountry())
                .idProvince(entity.getIdProvince())
                .idLocality(entity.getIdLocality())
                .address(entity.getAddress())
                .name(entity.getName())
                .openingDate(entity.getOpeningDate())
                .typeOffice(typeOfficeEntityMapper.toDomain(entity.getTypeOffice()))
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public OfficeEntity toEntity(Office domain) {
        if (domain == null) return null;
        return OfficeEntity.builder()
                .id(domain.getId())
                .idCountry(domain.getIdCountry())
                .idProvince(domain.getIdProvince())
                .idLocality(domain.getIdLocality())
                .address(domain.getAddress())
                .name(domain.getName())
                .openingDate(domain.getOpeningDate())
                .typeOffice(typeOfficeEntityMapper.toEntity(domain.getTypeOffice()))
                .deletedAt(domain.getDeletedAt())
                .build();
    }
}

