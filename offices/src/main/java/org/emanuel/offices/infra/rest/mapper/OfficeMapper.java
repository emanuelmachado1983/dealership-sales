package org.emanuel.offices.infra.rest.mapper;

import org.emanuel.offices.infra.db.entity.TypeOfficeEntity;
import org.emanuel.offices.infra.rest.dto.OfficeGetDto;
import org.emanuel.offices.infra.rest.dto.OfficeModifyDto;
import org.emanuel.offices.infra.rest.dto.TypeOfficeDto;
import org.emanuel.offices.domain.Office;
import org.emanuel.offices.domain.TypeOffice;
import org.springframework.stereotype.Component;

@Component
public class OfficeMapper {
    public OfficeGetDto toDto(Office office) {
        var officeDto = new OfficeGetDto();
        officeDto.setId(office.getId());
        officeDto.setIdCountry(office.getIdCountry());
        officeDto.setIdProvince(office.getIdProvince());
        officeDto.setIdLocality(office.getIdLocality());
        officeDto.setAddress(office.getAddress());
        officeDto.setName(office.getName());
        officeDto.setOpeningDate(office.getOpeningDate());
        officeDto.setTypeOffice(new TypeOfficeDto(
                        office.getTypeOffice().getId(),
                        office.getTypeOffice().getName(
                        )
                )
        );
        return officeDto;
    }

    public Office toModel(Long id, OfficeModifyDto officeModifyDto) {
        var office = new Office();
        office.setId(id);
        office.setIdCountry(officeModifyDto.getIdCountry());
        office.setIdProvince(officeModifyDto.getIdProvince());
        office.setIdLocality(officeModifyDto.getIdLocality());
        office.setTypeOffice(new TypeOffice(officeModifyDto.getTypeOfficeId(), null));
        office.setAddress(officeModifyDto.getAddress());
        office.setName(officeModifyDto.getName());
        office.setOpeningDate(officeModifyDto.getOpeningDate());
        return office;
    }
}
