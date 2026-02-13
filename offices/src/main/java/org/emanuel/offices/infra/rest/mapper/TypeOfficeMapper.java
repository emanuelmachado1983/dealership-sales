package org.emanuel.offices.infra.rest.mapper;

import org.emanuel.offices.infra.rest.dto.TypeOfficeDto;
import org.emanuel.offices.domain.TypeOffice;
import org.springframework.stereotype.Component;

@Component
public class TypeOfficeMapper {
    public TypeOfficeDto toDto(TypeOffice typeOffice) {
        return new TypeOfficeDto(typeOffice.getId(), typeOffice.getName());
    }
}
