package org.emanuel.sales.domain.exceptions;

public class SaleNotExistException extends SaleException {
    public SaleNotExistException(String message) {
        super(message);
    }
}
