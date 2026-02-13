package org.emanuel.maintain.domain.db.repository;

import org.emanuel.maintain.domain.MechanicalRepair;

import java.util.List;
import java.util.Optional;

public interface MechanicalRepairRepository {
    Optional<MechanicalRepair> findById(Long id);

    List<MechanicalRepair> findAllByFilters(Long id);

    MechanicalRepair save(MechanicalRepair mechanicalRepair);

    boolean existsById(Long id);

    void deleteById(Long id);
}
