package org.emanuel.customers.infra.grpc.customer.exceptions;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;
import org.emanuel.customers.domain.exceptions.CustomerNotExistDomainException;

@GrpcAdvice
public class GrpcExceptionAdvice {
    @GrpcExceptionHandler(CustomerNotExistDomainException.class)
    public StatusRuntimeException handleOfficeNotFound(CustomerNotExistDomainException ex) {
        return Status.NOT_FOUND
                .withDescription(ex.getMessage())
                .asRuntimeException();
    }

    @GrpcExceptionHandler(Exception.class)
    public StatusRuntimeException handleGeneric(Exception ex) {
        return Status.INTERNAL
                .withDescription("Internal error")
                .asRuntimeException();
    }
}
