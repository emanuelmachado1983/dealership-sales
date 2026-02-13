package org.emanuel.offices.infra.rest;

import org.emanuel.offices.application.ILocalityService;
import org.emanuel.offices.infra.rest.dto.LocalityGetDto;
import org.emanuel.offices.infra.rest.dto.LocalityModifyDto;
import org.emanuel.offices.infra.rest.mapper.LocalityMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/countries/{idCountry}/provinces/{idProvince}/localities")
public class LocalityRest {
    private final ILocalityService localityService;
    private final LocalityMapper localityMapper;

    public LocalityRest(ILocalityService localityService, LocalityMapper localityMapper) {
        this.localityService = localityService;
        this.localityMapper = localityMapper;
    }

    @GetMapping("/{idLocality}")
    public ResponseEntity<LocalityGetDto> getLocalityById(@PathVariable Long idCountry, @PathVariable Long idProvince, @PathVariable Long idLocality) {
        return ResponseEntity.ok(
                localityMapper.toGetDto(localityService.getLocalityById(idCountry, idProvince, idLocality))
        );
    }

    @GetMapping("")
    public ResponseEntity<List<LocalityGetDto>> getAllLocalities(@PathVariable Long idCountry, @PathVariable Long idProvince) {
        var localities = localityService.getAllLocalities(idCountry, idProvince);
        if (localities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(localities.stream().map(localityMapper::toGetDto).toList());
    }

    @PostMapping("")
    public ResponseEntity<LocalityGetDto> addLocality(@PathVariable Long idCountry, @PathVariable Long idProvince, @RequestBody LocalityModifyDto locality) {
        var localityDto = localityMapper.toGetDto(localityService.addLocality(
                idCountry,
                idProvince,
                locality.getName()
        ));

        return ResponseEntity.created(URI.create("/countries/" + idCountry + "/provinces/" + idProvince + "/localities/" + localityDto.getId()))
                .body(localityDto);
    }

    @PutMapping("/{idLocality}")
    public ResponseEntity<LocalityGetDto> updateLocality(@PathVariable Long idCountry, @PathVariable Long idProvince, @PathVariable Long idLocality, @RequestBody LocalityModifyDto localityModifyDto) {
        return ResponseEntity.ok(
                localityMapper.toGetDto(localityService.updateLocality(idCountry, idProvince, idLocality, localityModifyDto.getName())));
    }

    @DeleteMapping("/{idLocality}")
    public void deleteLocality(@PathVariable Long idCountry, @PathVariable Long idProvince, @PathVariable Long idLocality) {
        localityService.deleteLocality(idCountry, idProvince, idLocality);
    }
}
