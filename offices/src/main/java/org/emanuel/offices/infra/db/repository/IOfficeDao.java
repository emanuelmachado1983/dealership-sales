package org.emanuel.offices.infra.db.repository;

import org.emanuel.offices.infra.db.entity.OfficeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IOfficeDao extends CrudRepository<OfficeEntity, Long> {
    @Query("SELECT o FROM OfficeEntity o WHERE o.id = :idOffice AND o.deletedAt IS NULL")
    Optional<OfficeEntity> findByIdAndNotDeleted(@Param("idOffice") Long idOffice);

    @Query("SELECT o FROM OfficeEntity o WHERE o.deletedAt IS NULL")
    List<OfficeEntity> findAllAndNotDeleted();

    @Query("SELECT COUNT(o) > 0 FROM OfficeEntity o WHERE o.id = :idOffice AND o.deletedAt IS NULL")
    boolean existsByIdAndNotDeleted(@Param("idOffice") Long idOffice);

    @Query("SELECT o FROM OfficeEntity o WHERE o.idLocality = :idLocality AND o.deletedAt IS NULL")
    List<OfficeEntity> findAllOfficesWithLocalityAndNotDeleted(@Param("idLocality") Long idLocality);
}
