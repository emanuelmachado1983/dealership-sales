package org.emanuel.offices.infra.db.repository.impl;

import lombok.RequiredArgsConstructor;
import org.emanuel.offices.domain.Locality;
import org.emanuel.offices.domain.db.repository.LocalityRepository;
import org.emanuel.offices.infra.db.mapper.LocalityEntityMapper;
import org.emanuel.offices.infra.db.repository.ILocalityDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LocalityRepositoryImpl implements LocalityRepository {

    private final ILocalityDao localityDao;
    private final LocalityEntityMapper localityEntityMapper;

    @Override
    public Optional<Locality> findByIdAndNotDeleted(Long idProvince, Long idLocality) {
        return localityDao.findByIdAndNotDeleted(idProvince, idLocality)
                .map(localityEntityMapper::toDomain);
    }

    @Override
    public List<Locality> findAllAndNotDeleted(Long idProvince) {
        return localityDao.findAllAndNotDeleted(idProvince).stream()
                .map(localityEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Locality save(Locality locality) {
        var entity = localityEntityMapper.toEntity(locality);
        var savedEntity = localityDao.save(entity);
        return localityEntityMapper.toDomain(savedEntity);
    }

}

