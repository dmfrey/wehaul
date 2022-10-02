package com.vmware.tanzulabs.wehaul.fleetmanagement.usecases.out;

import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.Truck;

import java.util.List;

public interface GetAllTrucksPort {

    List<Truck> getAll();

}
