package org.emanuel.offices.application;

import org.emanuel.offices.domain.Office;

import java.util.List;

public interface IOfficeService {
    Office getOfficeById(Long id);

    Boolean officeExists(Long id);

    List<Office> getAllOffices();

    Office addOffice(Office officeModifyDto);

    Office updateOffice(Long id, Office officeModifyDto);

    void deleteOffice(Long id);

}
