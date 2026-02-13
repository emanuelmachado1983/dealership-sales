package org.emanuel.vehicles.infra.grpc.office;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.emanuel.contracts.proto.offices.IdRequest;
import org.emanuel.contracts.proto.offices.OfficeServiceGrpc;
import org.emanuel.vehicles.application.integration.offices.IOfficeService;
import org.emanuel.vehicles.domain.exceptions.VehicleBadRequestException;
import org.springframework.stereotype.Service;

@Service
public class OfficeGrcpClient implements IOfficeService {
    @GrpcClient("office")
    private OfficeServiceGrpc.OfficeServiceBlockingStub officeStub;

    @Override
    public Boolean existsById(Long id) {
        try {
            var request = IdRequest.newBuilder().setId(id).build();
            var response = officeStub.getById(request);
            return response != null;
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                return false;
            }
            throw new VehicleBadRequestException("There was an error searching for office " + id);
        }
    }
}
