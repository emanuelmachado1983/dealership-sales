package org.emanuel.offices.infra.grpc.office;

import net.devh.boot.grpc.server.service.GrpcService;
import org.emanuel.contracts.proto.offices.IdRequest;
import org.emanuel.contracts.proto.offices.OfficeMinimal;
import org.emanuel.contracts.proto.offices.OfficeServiceGrpc;
import org.emanuel.contracts.proto.offices.TypeOffice;
import org.emanuel.offices.application.IOfficeService;

import java.time.format.DateTimeFormatter;

@GrpcService
public class OfficeGrpcService
        extends OfficeServiceGrpc.OfficeServiceImplBase {

    private final IOfficeService officeService;

    public OfficeGrpcService(IOfficeService officeService) {
        this.officeService = officeService;
    }

    @Override
    public void getById(
            IdRequest request,
            io.grpc.stub.StreamObserver<OfficeMinimal> responseObserver) {

        var office = officeService.getOfficeById(request.getId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        OfficeMinimal response = OfficeMinimal.newBuilder()
                .setId(request.getId())
                .setIdCountry(office.getIdCountry())
                .setIdProvince(office.getIdProvince())
                .setIdLocality(office.getIdLocality())
                .setAddress(office.getAddress())
                .setName(office.getName())
                .setOpeningDate(office.getOpeningDate().format(formatter))
                .setTypeOffice(
                        TypeOffice.newBuilder()
                                .setId(office.getTypeOffice().getId())
                                .setName(office.getTypeOffice().getName())
                                .build()
                )
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }
}