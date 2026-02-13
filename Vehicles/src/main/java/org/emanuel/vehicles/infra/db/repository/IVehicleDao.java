package org.emanuel.vehicles.infra.db.repository;

import org.emanuel.vehicles.infra.db.entity.ModelVehicleEntity;
import org.emanuel.vehicles.infra.db.entity.VehicleEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IVehicleDao extends CrudRepository<VehicleEntity, Long> {
    @Query("SELECT v FROM VehicleEntity v WHERE "
            + "(:officeLocationId IS NULL OR v.officeLocationId = :officeLocationId)"
            + " AND (:statusId IS NULL OR v.status.id = :statusId)"
            + " AND (:modelId IS NULL OR v.model.id = :modelId)"
            + " AND (:type IS NULL OR v.type.id = :type)"
    )
    List<VehicleEntity> findAllByFilters(Long officeLocationId, Long statusId, Long modelId, Long type);

    @Modifying
    @Query("UPDATE VehicleEntity v SET v.status.id = :statusId WHERE v.id = :vehicleId")
    void updateVehicleStatus(Long vehicleId, Long statusId);

    @Override
    List<VehicleEntity> findAll();
}
