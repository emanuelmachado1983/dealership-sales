package org.emanuel.sales.domain.exceptions;

public class EmployeeNotExistException extends EmployeeException {
    public EmployeeNotExistException(String message) {
        super(message);
    }
}
