package com.vmware.tanzulabs.wehaul.fleetmanagement.usecases;

import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.Truck;
import com.vmware.tanzulabs.wehaul.fleetmanagement.usecases.in.LookupTruckUsecase;
import com.vmware.tanzulabs.wehaul.fleetmanagement.usecases.out.FindTruckPort;
import org.springframework.stereotype.Component;

@Component
public class LookupTruckService implements LookupTruckUsecase {

    private final FindTruckPort findTruckPort;

    public LookupTruckService( final FindTruckPort findTruckPort ) {

        this.findTruckPort = findTruckPort;

    }

    @Override
    public Truck execute( final Integer truckId ) {

        return this.findTruckPort.find( truckId );
    }

}
