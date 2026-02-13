package org.emanuel.offices.domain.db.repository;

import org.emanuel.offices.domain.Province;

import java.util.List;
import java.util.Optional;

public interface ProvinceRepository {
    Optional<Province> findByIdAndNotDeleted(Long id);
    List<Province> findAllByCountryIdAndNotDeleted(Long countryId);
    List<Province> findAllAndNotDeleted();
    Province save(Province province);
    boolean existsByIdAndNotDeleted(Long id);
}

