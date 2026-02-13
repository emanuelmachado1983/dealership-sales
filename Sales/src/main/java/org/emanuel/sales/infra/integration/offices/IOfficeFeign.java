package org.emanuel.sales.infra.integration.offices;

import org.emanuel.sales.infra.integration.offices.dto.DeliveryScheduleFeignDto;
import org.emanuel.sales.infra.integration.offices.dto.OfficeFeignDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "offices-service", url = "${offices.service.url}")
public interface IOfficeFeign {
    @GetMapping("/api/v1/offices/{idOffice}")
    OfficeFeignDto findById(@PathVariable Long idOffice);

    @GetMapping("/api/v1/delivery-schedules-configuration?officeTo={idOfficeTo}")
    List<DeliveryScheduleFeignDto> findDeliverySchedule(@PathVariable Long idOfficeTo);


}
