package com.vmware.tanzulabs.wehaul.fleetmanagement.usecases.in;

import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.Truck;

public interface LookupTruckUsecase {

    Truck execute(Integer truckId );

}
