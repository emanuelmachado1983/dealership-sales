package org.emanuel.offices.domain.db.repository;

import org.emanuel.offices.domain.Office;

import java.util.List;
import java.util.Optional;

public interface OfficeRepository {
    Optional<Office> findByIdAndNotDeleted(Long id);

    List<Office> findAllAndNotDeleted();

    Office save(Office office);

    boolean existsByIdAndNotDeleted(Long id);

    List<Office> findAllOfficesWithLocalityAndNotDeleted(Long idLocality);
}

