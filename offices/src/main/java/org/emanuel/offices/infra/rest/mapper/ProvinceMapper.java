package org.emanuel.offices.infra.rest.mapper;

import org.emanuel.offices.infra.rest.dto.ProvinceGetDto;
import org.emanuel.offices.domain.Country;
import org.emanuel.offices.domain.Province;
import org.springframework.stereotype.Component;

@Component
public class ProvinceMapper {

    public ProvinceGetDto toGetDto(Province province) {
        return new ProvinceGetDto(province.getId(), province.getName(), province.getCountry().getId());
    }

    public Province toModel(ProvinceGetDto provinceGetDto) {
        Country country = new Country();
        country.setId(provinceGetDto.getCountryId());
        return new Province(provinceGetDto.getId(), provinceGetDto.getName(), country);
    }

}
