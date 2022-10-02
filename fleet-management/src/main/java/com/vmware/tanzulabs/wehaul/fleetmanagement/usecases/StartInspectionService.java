package com.vmware.tanzulabs.wehaul.fleetmanagement.usecases;

import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.Status;
import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.TruckUnavailableForInspectionException;
import com.vmware.tanzulabs.wehaul.fleetmanagement.usecases.in.StartInspectionUsecase;
import com.vmware.tanzulabs.wehaul.fleetmanagement.usecases.out.FindTruckPort;
import com.vmware.tanzulabs.wehaul.fleetmanagement.usecases.out.StartInspectionPort;
import org.springframework.stereotype.Component;

@Component
class StartInspectionService implements StartInspectionUsecase {

    private final FindTruckPort findTruckPort;
    private final StartInspectionPort startInspectionPort;

    StartInspectionService(
            final FindTruckPort findTruckPort,
            final StartInspectionPort startInspectionPort
    ) {

        this.findTruckPort = findTruckPort;
        this.startInspectionPort = startInspectionPort;

    }

    @Override
    public void execute( final Integer truckId ) throws TruckUnavailableForInspectionException {

        var found = this.findTruckPort.find( truckId );
        if( found.status().equals( Status.UNAVAILABLE ) ) {

            throw new TruckUnavailableForInspectionException( truckId );
        }

        this.startInspectionPort.start( truckId );

    }

}
