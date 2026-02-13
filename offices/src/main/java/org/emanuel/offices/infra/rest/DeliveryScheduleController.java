package org.emanuel.offices.infra.rest;

import org.emanuel.offices.application.IDeliveryScheduleService;
import org.emanuel.offices.infra.rest.dto.DeliveryScheduleGetDto;
import org.emanuel.offices.infra.rest.dto.DeliverySchedulePostDto;
import org.emanuel.offices.infra.rest.mapper.DeliveryScheduleMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/delivery-schedules-configuration")
public class DeliveryScheduleController {
    private final IDeliveryScheduleService deliveryScheduleService;
    private final DeliveryScheduleMapper deliveryScheduleMapper;

    public DeliveryScheduleController(IDeliveryScheduleService deliveryScheduleService, DeliveryScheduleMapper deliveryScheduleMapper) {
        this.deliveryScheduleService = deliveryScheduleService;
        this.deliveryScheduleMapper = deliveryScheduleMapper;
    }

    @GetMapping()
    public ResponseEntity<List<DeliveryScheduleGetDto>> getDeliverySchedules(@RequestParam(required = false) Long officeTo) {
        if (officeTo == null) {
            return getAllDeliverySchedules();
        } else {
            return getDeliverySchedulesByOfficeTo(officeTo);
        }
    }

    @PostMapping
    public ResponseEntity<Void> addDeliverySchedule(
            @RequestBody DeliverySchedulePostDto post) {

        deliveryScheduleService.addDeliverySchedule(
                post.getOfficeFrom(),
                post.getOfficeTo(),
                post.getDays());
        return ResponseEntity.status(201).build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeliverySchedule(@PathVariable Long id) {
        deliveryScheduleService.deleteDeliverySchedule(id);
        return ResponseEntity.noContent().build();
    }


    private ResponseEntity<List<DeliveryScheduleGetDto>> getAllDeliverySchedules() {
        var deliverySchedules = deliveryScheduleService.getAllDeliverySchedules().stream().map(deliveryScheduleMapper::toDto).toList();
        if (deliverySchedules.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(deliverySchedules);
    }

    private ResponseEntity<List<DeliveryScheduleGetDto>> getDeliverySchedulesByOfficeTo(@RequestParam Long officeTo) {
        var deliverySchedules = deliveryScheduleService.getAllDeliverySchedules(officeTo).stream().map(deliveryScheduleMapper::toDto).toList();
        if (deliverySchedules.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(deliverySchedules);
    }
}
