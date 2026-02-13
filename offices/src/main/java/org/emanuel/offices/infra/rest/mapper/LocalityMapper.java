package org.emanuel.offices.infra.rest.mapper;

import org.emanuel.offices.infra.rest.dto.LocalityGetDto;
import org.emanuel.offices.domain.Locality;
import org.springframework.stereotype.Component;

@Component
public class LocalityMapper {
    public LocalityGetDto toGetDto(Locality locality) {
        return new LocalityGetDto(locality.getId(), locality.getName());
    }
}
