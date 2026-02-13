package org.emanuel.offices.application;

import org.emanuel.offices.domain.TypeOffice;

import java.util.List;

public interface ITypeOfficeService {
    List<TypeOffice> getTypeOffices();

    TypeOffice getTypeOfficeById(Long id);

    Boolean existsById(Long id);
}
