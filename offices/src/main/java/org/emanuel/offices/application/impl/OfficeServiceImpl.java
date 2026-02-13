package org.emanuel.offices.application.impl;

import lombok.RequiredArgsConstructor;
import org.emanuel.offices.application.ILocalityService;
import org.emanuel.offices.application.IOfficeService;
import org.emanuel.offices.application.ITypeOfficeService;
import org.emanuel.offices.domain.Office;
import org.emanuel.offices.domain.exceptions.OfficeBadRequestDomainException;
import org.emanuel.offices.domain.exceptions.OfficeLocalityNotExistsDomainException;
import org.emanuel.offices.domain.exceptions.OfficeNotExistsDomainException;
import org.emanuel.offices.domain.db.repository.OfficeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OfficeServiceImpl implements IOfficeService {
    private final ITypeOfficeService typeOfficeService;
    private final ILocalityService localityService;
    private final OfficeRepository officeRepository;

    private final static long ID_CENTRAL = 1;


    @Override
    public Office getOfficeById(Long id) {
        return officeRepository.findByIdAndNotDeleted(id).orElseThrow(() -> new OfficeNotExistsDomainException("Office with id " + id + " does not exist or has been deleted"));
    }

    @Override
    public Boolean officeExists(Long id) {
        return officeRepository.existsByIdAndNotDeleted(id);
    }

    @Override
    public List<Office> getAllOffices() {
        return officeRepository.findAllAndNotDeleted();
    }

    @Override
    public Office addOffice(Office office) {
        if (office.getTypeOffice().getId() == ID_CENTRAL) {
            throw new OfficeBadRequestDomainException("You cannot create a new office with type 'Central'.");
        }
        validations(office);
        var auxOffices = officeRepository.findAllOfficesWithLocalityAndNotDeleted(office.getIdLocality());
        if (!auxOffices.isEmpty()) {
            throw new OfficeBadRequestDomainException("There is already an office in the locality with id: " + office.getIdLocality());
        }

        if (office.getOpeningDate().isAfter(LocalDateTime.now())) {
            throw new OfficeBadRequestDomainException("Opening date cannot be in the future.");
        }
        return officeRepository.save(office);
    }

    @Override
    public Office updateOffice(Long id, Office office) {
        var officeBefore = officeRepository.findByIdAndNotDeleted(office.getId()).orElseThrow(() -> new OfficeNotExistsDomainException("Office with id " + office.getId() + " does not exist or has been deleted"));
        validations(office);
        if (!officeBefore.getTypeOffice().getId().equals(ID_CENTRAL)
                && office.getTypeOffice().getId().equals(ID_CENTRAL)) {
            throw new OfficeBadRequestDomainException("You cannot change an office as a central office");
        }

        if (!office.getTypeOffice().getId().equals(ID_CENTRAL)
                && officeBefore.getTypeOffice().getId().equals(ID_CENTRAL)) {
            throw new OfficeBadRequestDomainException("You cannot change an office as a central office");
        }

        return officeRepository.save(office);
    }

    @Override
    public void deleteOffice(Long id) {
        var office = officeRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new OfficeNotExistsDomainException("Office with id " + id + " does not exist or has been deleted"));

        office.setDeletedAt(LocalDateTime.now());
        officeRepository.save(office);
    }

    private void validations(Office office) {
        if (office.getTypeOffice() == null) {
            throw new OfficeBadRequestDomainException("Should provide a type of office");
        }
        if (!typeOfficeService.existsById(office.getTypeOffice().getId())) {
            throw new OfficeBadRequestDomainException("Non existent type of office: " + office.getTypeOffice().getId());
        }
        if (office.getIdCountry() == null) {
            throw new OfficeBadRequestDomainException("Should provide a country id");
        }
        if (office.getIdProvince() == null) {
            throw new OfficeBadRequestDomainException("Should provide a province id");
        }
        if (office.getIdLocality() == null) {
            throw new OfficeBadRequestDomainException("Should provide a locality id");
        }
        if (!localityService.existsLocalityById(office.getIdCountry(), office.getIdProvince(), office.getIdLocality())) {
            throw new OfficeLocalityNotExistsDomainException("Non existent locality: " + office.getIdLocality());
        }
    }
}
