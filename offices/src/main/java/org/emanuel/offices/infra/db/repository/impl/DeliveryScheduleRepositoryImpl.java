package org.emanuel.offices.infra.db.repository.impl;

import lombok.RequiredArgsConstructor;
import org.emanuel.offices.domain.DeliverySchedule;
import org.emanuel.offices.domain.db.repository.DeliveryScheduleRepository;
import org.emanuel.offices.infra.db.mapper.DeliveryScheduleEntityMapper;
import org.emanuel.offices.infra.db.repository.IDeliveryScheduleDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DeliveryScheduleRepositoryImpl implements DeliveryScheduleRepository {

    private final IDeliveryScheduleDao deliveryScheduleDao;
    private final DeliveryScheduleEntityMapper deliveryScheduleEntityMapper;

    @Override
    public List<DeliverySchedule> findAll() {
        return deliveryScheduleDao.findAll().stream()
                .map(deliveryScheduleEntityMapper::toDomain)
                .toList();
    }

    @Override
    public List<DeliverySchedule> findDeliveryScheduleByOfficeTo(Long officeTo) {
        return deliveryScheduleDao.findDeliveryScheduleByOfficeTo(officeTo).stream()
                .map(deliveryScheduleEntityMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByOfficeFromAndOfficeTo(Long officeFrom, Long officeTo) {
        return deliveryScheduleDao.existsByOfficeFromAndOfficeTo(officeFrom, officeTo);
    }

    @Override
    public Optional<DeliverySchedule> findById(Long id) {
        return deliveryScheduleDao.findById(id)
                .map(deliveryScheduleEntityMapper::toDomain);
    }

    @Override
    public DeliverySchedule save(DeliverySchedule deliverySchedule) {
        var entity = deliveryScheduleEntityMapper.toEntity(deliverySchedule);
        var savedEntity = deliveryScheduleDao.save(entity);
        return deliveryScheduleEntityMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(Long id) {
        deliveryScheduleDao.deleteById(id);
    }



    /*
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
    }*/
}

