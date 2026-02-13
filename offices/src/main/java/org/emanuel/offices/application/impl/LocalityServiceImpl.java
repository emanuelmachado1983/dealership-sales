package org.emanuel.offices.application.impl;

import lombok.RequiredArgsConstructor;
import org.emanuel.offices.application.ICountryService;
import org.emanuel.offices.application.ILocalityService;
import org.emanuel.offices.application.IProvinceService;
import org.emanuel.offices.domain.Locality;
import org.emanuel.offices.domain.exceptions.LocalityNotExistDomainException;
import org.emanuel.offices.domain.db.repository.LocalityRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalityServiceImpl implements ILocalityService {
    private final IProvinceService provinceService;
    private final ICountryService countryService;
    private final LocalityRepository localityRepository;

    @Override
    public Locality getLocalityById(Long idCountry, Long idProvince, Long idLocality) {
        countryService.getCountryById(idCountry);
        return localityRepository.findByIdAndNotDeleted(idProvince, idLocality).orElseThrow(() -> new LocalityNotExistDomainException("Locality doesn't exists with id: " + idLocality + " in province with id: " + idProvince));
    }

    @Override
    public List<Locality> getAllLocalities(Long idCountry, Long idProvince) {
        countryService.getCountryById(idCountry);
        return localityRepository.findAllAndNotDeleted(idProvince);
    }

    @Override
    public Locality addLocality(Long idCountry, Long idProvince, String name) {
        countryService.getCountryById(idCountry);
        var province = provinceService.getProvinceById(idCountry, idProvince);
        var locality = new Locality();
        locality.setName(name);
        locality.setProvinceId(province.getId());

        return localityRepository.save(locality);
    }

    @Override
    public Locality updateLocality(Long idCountry, Long idProvince, Long idLocality, String name) {
        countryService.getCountryById(idCountry);
        provinceService.getProvinceById(idCountry, idProvince);
        var locality = localityRepository.findByIdAndNotDeleted(idProvince, idLocality)
                .orElseThrow(() -> new LocalityNotExistDomainException("Locality doesn't exists with id: " + idLocality + " in province with id: " + idProvince));
        locality.setName(name);
        return localityRepository.save(locality);
    }

    @Override
    public void deleteLocality(Long idCountry, Long idProvince, Long idLocality) {
        countryService.getCountryById(idCountry);
        provinceService.getProvinceById(idCountry, idProvince);
        var locality = localityRepository.findByIdAndNotDeleted(idProvince, idLocality)
                .orElseThrow(() -> new LocalityNotExistDomainException("Locality doesn't exists with id: " + idLocality + " in province with id: " + idProvince));
        locality.setDeletedAt(LocalDateTime.now());
        localityRepository.save(locality);
    }

    @Override
    public Boolean existsLocalityById(Long idCountry, Long idProvince, Long idLocality) {
        var locality = localityRepository.findByIdAndNotDeleted(idProvince, idLocality);
        return locality.isPresent();
    }
}
