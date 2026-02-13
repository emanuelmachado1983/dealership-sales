package org.emanuel.customers.domain.exceptions;

public class CustomerNotExistDomainException extends CustomerDomainException {
    public CustomerNotExistDomainException(String message) {
        super(message);
    }
}
