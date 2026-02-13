package org.emanuel.sales.infra.integration.offices.mapper;

import org.emanuel.sales.domain.office.Office;
import org.emanuel.sales.domain.office.TypeOffice;
import org.emanuel.sales.infra.integration.offices.dto.OfficeFeignDto;
import org.springframework.stereotype.Component;

@Component
public class OfficeMapper {
    public Office toModel(OfficeFeignDto officeFeignDto) {
        var office = new Office();
        office.setId(officeFeignDto.getId());
        office.setIdCountry(officeFeignDto.getIdCountry());
        office.setIdProvince(officeFeignDto.getIdProvince());
        office.setIdLocality(officeFeignDto.getIdLocality());
        office.setAddress(officeFeignDto.getAddress());
        office.setName(officeFeignDto.getName());
        office.setOpeningDate(officeFeignDto.getOpeningDate());
        office.setTypeOffice(new TypeOffice(officeFeignDto.getId(), officeFeignDto.getName()));
        return office;
    }
}
