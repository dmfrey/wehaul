package com.vmware.tanzulabs.wehaul.fleetmanagement.usecases;

import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.Status;
import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.TruckNotInInspectionException;
import com.vmware.tanzulabs.wehaul.fleetmanagement.usecases.in.CompleteInspectionUsecase;
import com.vmware.tanzulabs.wehaul.fleetmanagement.usecases.out.CompleteInspectionPort;
import com.vmware.tanzulabs.wehaul.fleetmanagement.usecases.out.FindTruckPort;
import org.springframework.stereotype.Component;

@Component
class CompleteInspectionService implements CompleteInspectionUsecase {

    private final FindTruckPort findTruckPort;
    private final CompleteInspectionPort completeInspectionPort;

    CompleteInspectionService(
            final FindTruckPort findTruckPort,
            final CompleteInspectionPort completeInspectionPort
    ) {

        this.findTruckPort = findTruckPort;
        this.completeInspectionPort = completeInspectionPort;

    }

    @Override
    public void execute( final Integer truckId ) throws TruckNotInInspectionException {

        var found = this.findTruckPort.find( truckId );
        if( found.status().equals( Status.AVAILABLE ) ) {

            throw new TruckNotInInspectionException( truckId );
        }

        this.completeInspectionPort.complete( truckId );

    }

}
