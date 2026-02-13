package org.emanuel.offices.application;

import org.emanuel.offices.domain.Locality;

import java.util.List;

public interface ILocalityService {
    Locality getLocalityById(Long idCountry, Long idProvince, Long idLocality);

    List<Locality> getAllLocalities(Long idCountry, Long idProvince);

    Locality addLocality(Long idCountry, Long idProvince, String name);

    Locality updateLocality(Long idCountry, Long idProvince, Long idLocality, String name);

    void deleteLocality(Long idCountry, Long idProvince, Long idLocality);

    Boolean existsLocalityById(Long idCountry, Long idProvince, Long idLocality);
}
