package org.emanuel.offices.infra.db.repository.impl;

import lombok.RequiredArgsConstructor;
import org.emanuel.offices.domain.Country;
import org.emanuel.offices.domain.db.repository.CountryRepository;
import org.emanuel.offices.infra.db.mapper.CountryEntityMapper;
import org.emanuel.offices.infra.db.repository.ICountryDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CountryRepositoryImpl implements CountryRepository {

    private final ICountryDao countryDao;
    private final CountryEntityMapper countryEntityMapper;

    @Override
    public Optional<Country> findByIdAndNotDeleted(Long id) {
        return countryDao.findByIdAndNotDeleted(id)
                .map(countryEntityMapper::toDomain);
    }

    @Override
    public List<Country> findAllAndNotDeleted() {
        return countryDao.findAllAndNotDeleted().stream()
                .map(countryEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Country save(Country country) {
        var entity = countryEntityMapper.toEntity(country);
        var savedEntity = countryDao.save(entity);
        return countryEntityMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsByIdAndNotDeleted(Long id) {
        return countryDao.existsByIdAndNotDeleted(id);
    }
}

