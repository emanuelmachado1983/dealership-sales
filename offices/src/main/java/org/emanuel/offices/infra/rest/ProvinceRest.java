package org.emanuel.offices.infra.rest;

import org.emanuel.offices.application.IProvinceService;
import org.emanuel.offices.infra.rest.dto.ProvinceGetDto;
import org.emanuel.offices.infra.rest.dto.ProvinceModifyDto;
import org.emanuel.offices.infra.rest.mapper.ProvinceMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/countries/{idCountry}/provinces")
public class ProvinceRest {

    private final IProvinceService provinceService;
    private final ProvinceMapper provinceMapper;

    public ProvinceRest(IProvinceService provinceService,
                        ProvinceMapper provinceMapper) {
        this.provinceService = provinceService;
        this.provinceMapper = provinceMapper;
    }

    @GetMapping("/{idProvince}")
    public ResponseEntity<ProvinceGetDto> getProvinceById(@PathVariable Long idCountry, @PathVariable Long idProvince) {
        return ResponseEntity.ok(provinceMapper.toGetDto(provinceService.getProvinceById(idCountry, idProvince)));
    }

    @GetMapping("")
    public ResponseEntity<List<ProvinceGetDto>> getAllProvinces(@PathVariable Long idCountry) {
        var provinces = provinceService.getAllProvinces(idCountry);
        if (provinces.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(provinces.stream().map(provinceMapper::toGetDto).toList());
    }

    @PostMapping("")
    public ResponseEntity<ProvinceGetDto> addProvince(@PathVariable Long idCountry, @RequestBody ProvinceModifyDto province) {

        var provinceDto = provinceService.addProvince(
                idCountry,
                province.getName()
        );

        return ResponseEntity.created(URI.create("/countries/" + idCountry + "/provinces/" + provinceDto.getId()))
                .body(provinceMapper.toGetDto(provinceDto));
    }

    @PutMapping("/{idProvince}")
    public ResponseEntity<ProvinceGetDto> updateProvince(@PathVariable Long idCountry, @PathVariable Long idProvince, @RequestBody ProvinceModifyDto provinceModifyDto) {
        return ResponseEntity.ok(
                provinceMapper.toGetDto(provinceService.updateProvince(idCountry, idProvince, provinceModifyDto.getName())));
    }

    @DeleteMapping("/{idProvince}")
    public void deleteProvince(@PathVariable Long idCountry, @PathVariable Long idProvince) {
        provinceService.deleteProvince(idCountry, idProvince);
    }

}
