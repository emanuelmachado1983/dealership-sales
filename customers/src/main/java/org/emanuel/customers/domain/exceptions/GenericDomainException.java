package org.emanuel.customers.domain.exceptions;

public class GenericDomainException extends RuntimeException {
    public GenericDomainException(String message) {
        super(message);
    }

    public GenericDomainException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenericDomainException(Throwable cause) {
        super(cause);
    }
    
}
