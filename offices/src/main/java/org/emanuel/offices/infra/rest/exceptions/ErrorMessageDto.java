package org.emanuel.offices.infra.rest.exceptions;

public class ErrorMessageDto {
    private String errorMessage;

    public ErrorMessageDto(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
