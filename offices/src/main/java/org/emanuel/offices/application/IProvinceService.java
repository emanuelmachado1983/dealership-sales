package org.emanuel.offices.application;

import org.emanuel.offices.domain.Province;

import java.util.List;

public interface IProvinceService {
    Province getProvinceById(Long idCountry, Long idProvince);

    List<Province> getAllProvinces(Long idCountry);

    Province addProvince(Long idCountry, String name);

    Province updateProvince(Long idCountry, Long idProvince, String name);

    void deleteProvince(Long idCountry, Long idProvince);
}
