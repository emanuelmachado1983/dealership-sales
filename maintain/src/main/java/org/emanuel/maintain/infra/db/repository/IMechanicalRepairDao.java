package org.emanuel.maintain.infra.db.repository;

import org.emanuel.maintain.infra.db.dto.MechanicalRepairEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IMechanicalRepairDao extends CrudRepository<MechanicalRepairEntity, Long> {
    @Query("SELECT m FROM MechanicalRepairEntity m WHERE (:vehicleId IS NULL OR m.vehicleId = :vehicleId)")
    List<MechanicalRepairEntity> findAllByFilters(Long vehicleId);
}
