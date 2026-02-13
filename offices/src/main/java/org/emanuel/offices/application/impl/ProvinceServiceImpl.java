package org.emanuel.offices.application.impl;

import lombok.RequiredArgsConstructor;
import org.emanuel.offices.application.ICountryService;
import org.emanuel.offices.application.IProvinceService;
import org.emanuel.offices.domain.Province;
import org.emanuel.offices.domain.exceptions.ProvinceNotExistDomainException;
import org.emanuel.offices.domain.db.repository.ProvinceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProvinceServiceImpl implements IProvinceService {
    private final ICountryService countryService;
    private final ProvinceRepository provinceRepository;

    @Override
    public Province getProvinceById(Long idCountry, Long idProvince) {
        return provinceRepository.findByIdAndNotDeleted(idProvince)
                .orElseThrow(() -> new ProvinceNotExistDomainException("Province not found with id: " + idProvince));
    }

    @Override
    public List<Province> getAllProvinces(Long idCountry) {
        return provinceRepository.findAllByCountryIdAndNotDeleted(idCountry);
    }

    @Override
    public Province addProvince(Long idCountry, String name) {
        var country = countryService.getCountryById(idCountry);
        var province = Province.builder()
                .name(name)
                .country(country)
                .build();
        return provinceRepository.save(province);
    }

    @Override
    public Province updateProvince(Long idCountry, Long idProvince, String name) {
        var province = provinceRepository.findByIdAndNotDeleted(idProvince)
                .orElseThrow(() -> new ProvinceNotExistDomainException("Province not found with id: " + idProvince));
        province.setName(name);
        return provinceRepository.save(province);
    }

    @Override
    public void deleteProvince(Long idCountry, Long idProvince) {
        //TODO: Must verify if there is an office in the province
        // If there is an office, throw an exception
        var province = provinceRepository.findByIdAndNotDeleted(idProvince)
                .orElseThrow(() -> new ProvinceNotExistDomainException("Province not found with id: " + idProvince));
        province.setDeletedAt(LocalDateTime.now());
        provinceRepository.save(province);
    }
}
