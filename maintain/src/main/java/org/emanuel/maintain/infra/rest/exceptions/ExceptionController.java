package org.emanuel.maintain.infra.rest.exceptions;

import org.emanuel.maintain.domain.exceptions.MechanicalRepairBadRequestException;
import org.emanuel.maintain.domain.exceptions.MechanicalRepairNotExistException;
import org.emanuel.maintain.domain.exceptions.VehicleBadRequestException;
import org.emanuel.maintain.domain.exceptions.VehicleNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    private final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(MechanicalRepairNotExistException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessageDto mechanicalRepairNotExistException(Exception e) {
        return getMessage(e.getMessage());
    }


    @ExceptionHandler(MechanicalRepairBadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessageDto mechanicalRepairBadRequestException(Exception e) {
        return getMessage(e.getMessage());
    }

    @ExceptionHandler(VehicleNotExistException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessageDto vehicleNotExistException(Exception e) {
        return getMessage(e.getMessage());
    }

    @ExceptionHandler(VehicleBadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessageDto vehicleBadRequestException(Exception e) {
        return getMessage(e.getMessage());
    }


    private ErrorMessageDto getMessage(String message) {
        logger.error(message);
        return new ErrorMessageDto(message);
    }
}
