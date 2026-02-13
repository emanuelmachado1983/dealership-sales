package org.emanuel.offices.domain.db.repository;

import org.emanuel.offices.domain.Locality;

import java.util.List;
import java.util.Optional;

public interface LocalityRepository {
    Optional<Locality> findByIdAndNotDeleted(Long idProvince, Long idLocality);

    List<Locality> findAllAndNotDeleted(Long idProvince);

    Locality save(Locality locality);

}

