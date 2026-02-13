package org.emanuel.offices.infra.db.repository.impl;

import lombok.RequiredArgsConstructor;
import org.emanuel.offices.domain.Province;
import org.emanuel.offices.domain.db.repository.ProvinceRepository;
import org.emanuel.offices.infra.db.mapper.ProvinceEntityMapper;
import org.emanuel.offices.infra.db.repository.IProvinceDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProvinceRepositoryImpl implements ProvinceRepository {

    private final IProvinceDao provinceDao;
    private final ProvinceEntityMapper provinceEntityMapper;

    @Override
    public Optional<Province> findByIdAndNotDeleted(Long id) {
        return provinceDao.findByIdAndNotDeleted(id)
                .map(provinceEntityMapper::toDomain);
    }

    @Override
    public List<Province> findAllByCountryIdAndNotDeleted(Long countryId) {
        return provinceDao.findAllByCountryIdAndNotDeleted(countryId).stream()
                .map(provinceEntityMapper::toDomain)
                .toList();
    }

    @Override
    public List<Province> findAllAndNotDeleted() {
        return provinceDao.findAllAndNotDeleted().stream()
                .map(provinceEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Province save(Province province) {
        var entity = provinceEntityMapper.toEntity(province);
        var savedEntity = provinceDao.save(entity);
        return provinceEntityMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsByIdAndNotDeleted(Long id) {
        return provinceDao.existsByIdAndNotDeleted(id);
    }
}
