package org.emanuel.offices.infra.db.repository;

import org.emanuel.offices.infra.db.entity.ProvinceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IProvinceDao extends CrudRepository<ProvinceEntity, Long> {
    @Query("SELECT p FROM ProvinceEntity p WHERE p.country.id = :idCountry AND p.id = :idProvince AND p.deletedAt IS NULL")
    Optional<ProvinceEntity> findByIdAndCountryIdAndNotDeleted(@Param("idCountry") Long idCountry, @Param("idProvince") Long idProvince);

    @Query("SELECT p FROM ProvinceEntity p WHERE p.id = :idProvince AND p.deletedAt IS NULL")
    Optional<ProvinceEntity> findByIdAndNotDeleted(@Param("idProvince") Long idProvince);

    @Query("SELECT p FROM ProvinceEntity p WHERE p.country.id = :idCountry AND p.deletedAt IS NULL")
    List<ProvinceEntity> findAllByCountryIdAndNotDeleted(@Param("idCountry") Long idCountry);

    @Query("SELECT p FROM ProvinceEntity p WHERE p.deletedAt IS NULL")
    List<ProvinceEntity> findAllAndNotDeleted();

    @Query("SELECT COUNT(p) > 0 FROM ProvinceEntity p WHERE p.id = :idProvince AND p.deletedAt IS NULL")
    boolean existsByIdAndNotDeleted(@Param("idProvince") Long idProvince);
}
