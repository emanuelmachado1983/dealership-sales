package org.emanuel.customers.infra.controller.exceptions;

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
