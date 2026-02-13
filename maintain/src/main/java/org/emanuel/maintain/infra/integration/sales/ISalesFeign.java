package org.emanuel.maintain.infra.integration.sales;

import org.emanuel.maintain.infra.integration.sales.dto.SaleFeignGetDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "sales-service", url = "${sales.service.url}")
public interface ISalesFeign {
    @GetMapping("/api/v1/sales?vehicleId={vehicleId}")
    List<SaleFeignGetDto> findAll(@RequestParam Long vehicleId);
}
