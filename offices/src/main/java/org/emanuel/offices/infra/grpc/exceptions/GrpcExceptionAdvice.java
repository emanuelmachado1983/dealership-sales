package org.emanuel.offices.infra.grpc.exceptions;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;
import org.emanuel.offices.domain.exceptions.OfficeNotExistsDomainException;

@GrpcAdvice
public class GrpcExceptionAdvice {
    @GrpcExceptionHandler(OfficeNotExistsDomainException.class)
    public StatusRuntimeException handleOfficeNotFound(OfficeNotExistsDomainException ex) {
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
