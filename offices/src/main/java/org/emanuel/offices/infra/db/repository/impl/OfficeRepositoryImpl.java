package org.emanuel.offices.infra.db.repository.impl;

import lombok.RequiredArgsConstructor;
import org.emanuel.offices.domain.Office;
import org.emanuel.offices.domain.db.repository.OfficeRepository;
import org.emanuel.offices.infra.db.mapper.OfficeEntityMapper;
import org.emanuel.offices.infra.db.repository.IOfficeDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OfficeRepositoryImpl implements OfficeRepository {

    private final IOfficeDao officeDao;
    private final OfficeEntityMapper officeEntityMapper;

    @Override
    public Optional<Office> findByIdAndNotDeleted(Long id) {
        return officeDao.findByIdAndNotDeleted(id)
                .map(officeEntityMapper::toDomain);
    }

    @Override
    public List<Office> findAllAndNotDeleted() {
        return officeDao.findAllAndNotDeleted().stream()
                .map(officeEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Office save(Office office) {
        var entity = officeEntityMapper.toEntity(office);
        var savedEntity = officeDao.save(entity);
        return officeEntityMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsByIdAndNotDeleted(Long id) {
        return officeDao.existsByIdAndNotDeleted(id);
    }

    @Override
    public List<Office> findAllOfficesWithLocalityAndNotDeleted(Long idLocality) {
        return officeDao.findAllOfficesWithLocalityAndNotDeleted(idLocality).stream()
                .map(officeEntityMapper::toDomain)
                .toList();
    }
}

