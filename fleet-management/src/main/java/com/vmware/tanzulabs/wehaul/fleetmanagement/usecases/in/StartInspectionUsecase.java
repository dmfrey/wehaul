package com.vmware.tanzulabs.wehaul.fleetmanagement.usecases.in;

import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.TruckUnavailableForInspectionException;

public interface StartInspectionUsecase {

    void execute( Integer truckId ) throws TruckUnavailableForInspectionException;

}
