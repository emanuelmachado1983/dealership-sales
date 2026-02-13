package org.emanuel.offices.infra.grpc.exceptions;
import io.grpc.*;
import org.emanuel.offices.domain.exceptions.OfficeNotExistsDomainException;

public class GrpcExceptionInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {

        ServerCall.Listener<ReqT> listener = next.startCall(call, headers);

        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<>(listener) {

            @Override
            public void onHalfClose() {
                try {
                    super.onHalfClose();
                } catch (OfficeNotExistsDomainException ex) {

                    call.close(
                            Status.NOT_FOUND
                                    .withDescription(ex.getMessage()),
                            new Metadata()
                    );
                } catch (Exception ex) {

                    call.close(
                            Status.INTERNAL
                                    .withDescription("Internal error"),
                            new Metadata()
                    );
                }
            }
        };
    }
}
