package org.emanuel.offices.infra.db.repository;

import org.emanuel.offices.infra.db.entity.CountryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ICountryDao extends CrudRepository<CountryEntity, Long> {
    @Query("SELECT c FROM CountryEntity c WHERE c.id = :idCountry AND c.deletedAt IS NULL")
    Optional<CountryEntity> findByIdAndNotDeleted(@Param("idCountry") Long idCountry);

    @Query("SELECT c FROM CountryEntity c WHERE c.deletedAt IS NULL")
    List<CountryEntity> findAllAndNotDeleted();

    @Query("SELECT COUNT(c) > 0 FROM CountryEntity c WHERE c.id = :idCountry AND c.deletedAt IS NULL")
    boolean existsByIdAndNotDeleted(@Param("idCountry") Long idCountry);
}
