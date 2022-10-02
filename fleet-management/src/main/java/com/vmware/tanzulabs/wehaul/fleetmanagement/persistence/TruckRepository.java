package com.vmware.tanzulabs.wehaul.fleetmanagement.persistence;

import org.springframework.data.repository.CrudRepository;

interface TruckRepository extends CrudRepository<TruckEntity, Integer> {
}
