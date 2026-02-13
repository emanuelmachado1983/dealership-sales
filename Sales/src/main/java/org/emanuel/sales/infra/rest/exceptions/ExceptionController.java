package org.emanuel.sales.infra.rest.exceptions;

import org.emanuel.sales.domain.exceptions.EmployeeNotExistException;
import org.emanuel.sales.domain.exceptions.SaleBadRequestException;
import org.emanuel.sales.domain.exceptions.SaleNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    private final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(EmployeeNotExistException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public ErrorMessageDto employeeNotExistException(Exception e) {
        return getMessage(e.getMessage());
    }

    @ExceptionHandler(SaleNotExistException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public ErrorMessageDto saleNotExistException(Exception e) {
        return getMessage(e.getMessage());
    }

    @ExceptionHandler(SaleBadRequestException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public ErrorMessageDto saleBadRequestException(Exception e) {
        return getMessage(e.getMessage());
    }

    private ErrorMessageDto getMessage(String message) {
        logger.error(message);
        return new ErrorMessageDto(message);
    }
}
