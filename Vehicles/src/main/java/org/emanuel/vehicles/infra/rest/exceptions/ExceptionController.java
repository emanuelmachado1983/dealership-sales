package org.emanuel.vehicles.infra.rest.exceptions;

import org.emanuel.vehicles.domain.exceptions.ModelVehicleNotExistException;
import org.emanuel.vehicles.domain.exceptions.TypeVehicleNotExistException;
import org.emanuel.vehicles.domain.exceptions.VehicleBadRequestException;
import org.emanuel.vehicles.domain.exceptions.VehicleNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    private final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(VehicleNotExistException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessageDto vehicleNotExistException(Exception e) {
        return getMessage(e.getMessage());
    }

    @ExceptionHandler(VehicleBadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessageDto vehicleBadRequestException(Exception e) {
        return getMessage(e.getMessage());
    }

    @ExceptionHandler(ModelVehicleNotExistException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessageDto modelVehicleNotExistException(Exception e) {
        return getMessage(e.getMessage());
    }

    @ExceptionHandler(TypeVehicleNotExistException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessageDto typeVehicleNotExistException(Exception e) {
        return getMessage(e.getMessage());
    }

    private ErrorMessageDto getMessage(String message) {
        logger.error(message);
        return new ErrorMessageDto(message);
    }
}
