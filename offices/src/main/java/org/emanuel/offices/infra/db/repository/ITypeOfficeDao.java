package org.emanuel.offices.infra.db.repository;

import org.emanuel.offices.infra.db.entity.TypeOfficeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ITypeOfficeDao extends CrudRepository<TypeOfficeEntity, Long> {
    @Override
    List<TypeOfficeEntity> findAll();
}
