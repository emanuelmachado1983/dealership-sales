package org.emanuel.offices.domain.exceptions;

public class CountryNotExistDomainException extends CountryDomainException {
    public CountryNotExistDomainException(String message) {
        super(message);
    }
}
