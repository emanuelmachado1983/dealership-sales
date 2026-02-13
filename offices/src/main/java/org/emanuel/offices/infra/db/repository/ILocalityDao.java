package org.emanuel.offices.infra.db.repository;

import org.emanuel.offices.infra.db.entity.LocalityEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ILocalityDao extends CrudRepository<LocalityEntity, Long> {
    @Query("SELECT l FROM LocalityEntity l WHERE l.id = :idLocality AND l.province.id = :idProvince AND l.deletedAt IS NULL")
    Optional<LocalityEntity> findByIdAndNotDeleted(@Param("idProvince") Long idProvince, @Param("idLocality") Long idLocality);

    @Query("SELECT l FROM LocalityEntity l WHERE l.province.id = :idProvince AND l.deletedAt IS NULL")
    List<LocalityEntity> findAllAndNotDeleted(@Param("idProvince") Long idProvince);
}
