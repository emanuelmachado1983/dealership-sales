package org.emanuel.sales.infra.db.repository;

import org.emanuel.sales.infra.db.dto.SaleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ISaleDao extends CrudRepository<SaleEntity, Long> {

    @Query("SELECT s FROM SaleEntity s WHERE "
            + "(:customerId IS NULL OR s.customerId = :customerId)"
            + "AND (:employeeId IS NULL OR s.employeeEntity.id = :employeeId)"
            + "AND (:employeeId IS NULL OR s.employeeEntity.id = :employeeId)"
            + "AND (:dateFrom IS NULL OR s.date >= :dateFrom)"
            + "AND (:dateTo IS NULL OR s.date <= :dateTo)"
            + "AND (:vehicleId IS NULL OR s.vehicleId = :vehicleId)"

    )
    List<SaleEntity> findAllByFilters(Long customerId,
                                      Long employeeId,
                                      LocalDateTime dateFrom,
                                      LocalDateTime dateTo,
                                      Long vehicleId);


}
