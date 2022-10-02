package com.vmware.tanzulabs.wehaul.fleetmanagement.usecases.in;

import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.TruckNotInInspectionException;

public interface CompleteInspectionUsecase {

    void execute( Integer truckId ) throws TruckNotInInspectionException;

}
