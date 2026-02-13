package org.emanuel.offices.infra.db.repository;

import org.emanuel.offices.infra.db.entity.DeliveryScheduleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IDeliveryScheduleDao extends CrudRepository<DeliveryScheduleEntity, Long> {
    @Override
    List<DeliveryScheduleEntity> findAll();

    @Query("SELECT d FROM DeliveryScheduleEntity d WHERE d.officeTo.id = :idOfficeTo")
    List<DeliveryScheduleEntity> findDeliveryScheduleByOfficeTo(@Param("idOfficeTo") Long idOffice);

    @Query("SELECT d FROM DeliveryScheduleEntity d WHERE d.officeFrom.id = :officeFrom AND d.officeTo.id = :officeTo")
    DeliveryScheduleEntity findDeliveryScheduleByOfficeFromAndOfficeTo(@Param("officeFrom") Long officeFrom, @Param("officeTo") Long officeTo);

    @Query("SELECT COUNT(d) > 0 FROM DeliveryScheduleEntity d WHERE d.officeFrom.id = :officeFrom AND d.officeTo.id = :officeTo")
    boolean existsByOfficeFromAndOfficeTo(@Param("officeFrom") Long officeFrom, @Param("officeTo") Long officeTo);
}
