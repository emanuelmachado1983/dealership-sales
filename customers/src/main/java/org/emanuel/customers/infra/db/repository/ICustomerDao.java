package org.emanuel.customers.infra.db.repository;

import org.emanuel.customers.infra.db.entity.CustomerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ICustomerDao extends CrudRepository<CustomerEntity, Long> {
    @Query("SELECT c FROM CustomerEntity c WHERE c.id = :idCustomer AND c.deletedAt IS NULL")
    Optional<CustomerEntity> findByIdAndNotDeleted(@Param("idCustomer") Long idCustomer);

    @Query("SELECT c FROM CustomerEntity c WHERE c.deletedAt IS NULL")
    List<CustomerEntity> findAllAndNotDeleted();

    @Query("SELECT COUNT(c) > 0 FROM CustomerEntity c WHERE c.id = :idCustomer AND c.deletedAt IS NULL")
    boolean existsByIdAndNotDeleted(@Param("idCustomer") Long idCustomer);

}
