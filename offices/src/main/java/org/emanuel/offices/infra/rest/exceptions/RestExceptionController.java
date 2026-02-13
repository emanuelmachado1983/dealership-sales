package org.emanuel.offices.infra.rest.exceptions;

import org.emanuel.offices.domain.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionController {
    private final Logger logger = LoggerFactory.getLogger(RestExceptionController.class);

    @ExceptionHandler(OfficeNotExistsDomainException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessageDto officeNotExistsException(Exception e) {
        return getMessage(e.getMessage());
    }

    @ExceptionHandler(OfficeBadRequestDomainException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessageDto officeBadRequestException(Exception e) {
        return getMessage(e.getMessage());
    }

    @ExceptionHandler(OfficeLocalityNotExistsDomainException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessageDto officeLocalityNotExistsException(Exception e) {
        return getMessage(e.getMessage());
    }

    @ExceptionHandler(TypeOfficeNotExistsDomainException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessageDto typeOfficeNotExistsException(Exception e) {
        return getMessage(e.getMessage());
    }

    @ExceptionHandler(DeliveryScheduleNotExistsDomainException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessageDto deliveryScheduleNotExistsException(Exception e) {
        return getMessage(e.getMessage());
    }

    @ExceptionHandler(DeliveryScheduleBadRequestDomainException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessageDto deliveryScheduleBadRequestException(Exception e) {
        return getMessage(e.getMessage());
    }


    @ExceptionHandler(CountryNotExistDomainException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public ErrorMessageDto countryNotExistException(Exception e) {
        return getMessage(e.getMessage());
    }

    @ExceptionHandler(ProvinceNotExistDomainException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public ErrorMessageDto provinceNotExistException(Exception e) {
        return getMessage(e.getMessage());
    }

    @ExceptionHandler(LocalityNotExistDomainException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public ErrorMessageDto localityNotExistException(Exception e) {
        return getMessage(e.getMessage());
    }


    private ErrorMessageDto getMessage(String message) {
        logger.error(message);
        return new ErrorMessageDto(message);
    }
}
