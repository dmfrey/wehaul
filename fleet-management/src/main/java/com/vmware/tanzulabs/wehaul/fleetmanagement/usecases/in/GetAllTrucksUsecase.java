package com.vmware.tanzulabs.wehaul.fleetmanagement.usecases.in;

import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.Truck;

import java.util.List;

public interface GetAllTrucksUsecase {

    List<Truck> execute();

}
