package org.emanuel.vehicles.infra.integration.offices;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(name = "offices-service", url = "${offices.service.url}")
public interface IOfficeFeign {
    @GetMapping("/api/v1/offices/{idOffice}")
    Map<String, Object> findById(@PathVariable("idOffice") Long idOffice);

}
