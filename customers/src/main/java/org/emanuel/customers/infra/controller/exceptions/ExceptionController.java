package org.emanuel.customers.infra.controller.exceptions;

import org.emanuel.customers.domain.exceptions.CustomerNotExistDomainException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    private final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(CustomerNotExistDomainException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public ErrorMessageDto customerNotExistException(Exception e) {
        return getMessage(e.getMessage());
    }

    private ErrorMessageDto getMessage(String message) {
        logger.error(message);
        return new ErrorMessageDto(message);
    }
}
