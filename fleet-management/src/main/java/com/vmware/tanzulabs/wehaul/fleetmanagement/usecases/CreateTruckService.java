package com.vmware.tanzulabs.wehaul.fleetmanagement.usecases;

import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.Status;
import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.Truck;
import com.vmware.tanzulabs.wehaul.fleetmanagement.usecases.in.CreateTruckUsecase;
import com.vmware.tanzulabs.wehaul.fleetmanagement.usecases.out.CreateTruckPort;
import org.springframework.stereotype.Component;

@Component
public class CreateTruckService implements CreateTruckUsecase {

    private final CreateTruckPort createTruckPort;

    public CreateTruckService( final CreateTruckPort createTruckPort ) {

        this.createTruckPort = createTruckPort;

    }

    @Override
    public Integer execute() {

        return this.createTruckPort.createNew( new Truck( null, Status.AVAILABLE ) );
    }

}
