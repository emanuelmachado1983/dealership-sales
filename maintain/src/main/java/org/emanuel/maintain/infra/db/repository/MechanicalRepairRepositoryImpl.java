package org.emanuel.maintain.infra.db.repository;

import lombok.RequiredArgsConstructor;
import org.emanuel.maintain.domain.MechanicalRepair;
import org.emanuel.maintain.domain.db.repository.MechanicalRepairRepository;
import org.emanuel.maintain.infra.db.mapper.MechanicalRepairEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MechanicalRepairRepositoryImpl implements MechanicalRepairRepository {

    private final IMechanicalRepairDao mechanicalRepairDao;
    private final MechanicalRepairEntityMapper mechanicalRepairEntityMapper;

    @Override
    public Optional<MechanicalRepair> findById(Long id) {
        return mechanicalRepairDao.findById(id).map(mechanicalRepairEntityMapper::toDomain);
    }

    @Override
    public List<MechanicalRepair> findAllByFilters(Long id) {
        return mechanicalRepairDao.findAllByFilters(id).stream().map(mechanicalRepairEntityMapper::toDomain).toList();
    }

    @Override
    public MechanicalRepair save(MechanicalRepair mechanicalRepair) {
        return mechanicalRepairEntityMapper.toDomain(mechanicalRepairDao.save(mechanicalRepairEntityMapper.toEntity(mechanicalRepair)));
    }

    @Override
    public boolean existsById(Long id) {
        return mechanicalRepairDao.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        mechanicalRepairDao.deleteById(id);
    }
}
