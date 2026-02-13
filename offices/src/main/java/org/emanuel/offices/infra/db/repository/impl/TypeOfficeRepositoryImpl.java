package org.emanuel.offices.infra.db.repository.impl;

import lombok.RequiredArgsConstructor;
import org.emanuel.offices.domain.TypeOffice;
import org.emanuel.offices.domain.db.repository.TypeOfficeRepository;
import org.emanuel.offices.infra.db.mapper.TypeOfficeEntityMapper;
import org.emanuel.offices.infra.db.repository.ITypeOfficeDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TypeOfficeRepositoryImpl implements TypeOfficeRepository {

    private final ITypeOfficeDao typeOfficeDao;
    private final TypeOfficeEntityMapper typeOfficeEntityMapper;

    @Override
    public Optional<TypeOffice> findById(Long id) {
        return typeOfficeDao.findById(id).map(typeOfficeEntityMapper::toDomain);
    }

    @Override
    public List<TypeOffice> findAll() {
        return typeOfficeDao.findAll().stream()
                .map(typeOfficeEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Boolean existsById(Long id) {
        return typeOfficeDao.existsById(id);
    }

}

