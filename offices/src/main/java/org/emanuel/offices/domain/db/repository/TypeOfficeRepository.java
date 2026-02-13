package org.emanuel.offices.domain.db.repository;

import org.emanuel.offices.domain.TypeOffice;

import java.util.List;
import java.util.Optional;

public interface TypeOfficeRepository {

    Optional<TypeOffice> findById(Long id);

    List<TypeOffice> findAll();

    Boolean existsById(Long id);

}

