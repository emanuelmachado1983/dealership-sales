package org.emanuel.vehicles.infra.integration.offices;

//TODO: must delete this class because I use grpc now. I will leave this here for now.

import feign.FeignException;
import org.emanuel.vehicles.application.integration.offices.IOfficeService;
import org.springframework.stereotype.Service;

@Service
public class OfficeServiceImpl /*implements IOfficeService*/ {
    private final IOfficeFeign officeFeign;

    public OfficeServiceImpl(IOfficeFeign officeFeign) {
        this.officeFeign = officeFeign;
    }

    //@Override
    public Boolean existsById(Long idOffice) {
        try {
            officeFeign.findById(idOffice);
            return true;
        } catch (FeignException.NotFound e) {
            return false;
        }
    }
}
