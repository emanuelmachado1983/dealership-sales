package org.emanuel.offices.domain.db.repository;

import org.emanuel.offices.domain.Country;

import java.util.List;
import java.util.Optional;

public interface CountryRepository {
    Optional<Country> findByIdAndNotDeleted(Long id);
    List<Country> findAllAndNotDeleted();
    Country save(Country country);
    boolean existsByIdAndNotDeleted(Long id);
}

