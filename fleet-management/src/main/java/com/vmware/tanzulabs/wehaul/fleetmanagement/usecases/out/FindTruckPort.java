package com.vmware.tanzulabs.wehaul.fleetmanagement.usecases.out;

import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.Truck;

public interface FindTruckPort {

    Truck find( Integer truckId );

}
