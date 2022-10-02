package com.vmware.tanzulabs.wehaul.fleetmanagement.usecases;

import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.Truck;
import com.vmware.tanzulabs.wehaul.fleetmanagement.usecases.in.GetAllTrucksUsecase;
import com.vmware.tanzulabs.wehaul.fleetmanagement.usecases.out.GetAllTrucksPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class GetAllTrucksService implements GetAllTrucksUsecase {

    private final GetAllTrucksPort getAllTrucksPort;

    GetAllTrucksService( final GetAllTrucksPort getAllTrucksPort ) {

        this.getAllTrucksPort = getAllTrucksPort;

    }

    @Override
    public List<Truck> execute() {

        return this.getAllTrucksPort
                .getAll();
    }

}
