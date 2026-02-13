package org.emanuel.customers.infra.grpc.customer;

import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.emanuel.customers.application.ICustomerService;

@GrpcService
@RequiredArgsConstructor
public class CustomerGrpcService extends CustomerServiceGrpc.CustomerServiceImplBase {
    private final ICustomerService customerService;

    @Override
    public void getById(
            IdRequest request,
            io.grpc.stub.StreamObserver<CustomerMinimal> responseObserver) {

        var customer = customerService.getCustomerById(request.getId());
        CustomerMinimal response = CustomerMinimal.newBuilder()
                .setId(request.getId())
                .setName(customer.getName())
                .setDni(customer.getDni())
                .setEmail(customer.getEmail())
                .setPhone(customer.getPhone())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
