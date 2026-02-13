package org.emanuel.sales.infra.db.repository;

import org.emanuel.sales.infra.db.dto.EmployeeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IEmployeeDao extends CrudRepository<EmployeeEntity, Long> {
    @Query("SELECT c FROM EmployeeEntity c WHERE c.id = :idEmployee AND c.deletedAt IS NULL")
    Optional<EmployeeEntity> findByIdAndNotDeleted(@Param("idEmployee") Long idEmployee);

    @Query("SELECT c FROM EmployeeEntity c WHERE c.deletedAt IS NULL")
    List<EmployeeEntity> findAllAndNotDeleted();

    @Query("SELECT COUNT(c) > 0 FROM EmployeeEntity c WHERE c.id = :idEmployee AND c.deletedAt IS NULL")
    boolean existsByIdAndNotDeleted(@Param("idEmployee") Long idEmployee);

}
