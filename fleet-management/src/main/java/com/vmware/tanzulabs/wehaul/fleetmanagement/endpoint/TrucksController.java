package com.vmware.tanzulabs.wehaul.fleetmanagement.endpoint;

import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.Truck;
import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.TruckNotInInspectionException;
import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.TruckUnavailableForInspectionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class TrucksController {

    @GetMapping( "/trucks" )
    @CrossOrigin
    List<Truck> getAllTrucks() {

        throw new UnsupportedOperationException();
    }

    @PostMapping( "/trucks" )
    @CrossOrigin
    ResponseEntity<?> createNewTrucks() {

        throw new UnsupportedOperationException();
    }

    @PutMapping( "/trucks/{truckId}/startInspection" )
    @CrossOrigin
    ResponseEntity<?> startInspection( @PathVariable Integer truckId ) {

        try {

            throw new UnsupportedOperationException();

        } catch( TruckUnavailableForInspectionException e ) {

            throw new ResponseStatusException( HttpStatus.CONFLICT, e.getMessage(), e );
        }

    }

    @PutMapping( "/trucks/{truckId}/completeInspection" )
    @CrossOrigin
    ResponseEntity<?> completeInspection( @PathVariable Integer truckId ) {

        try {

            throw new UnsupportedOperationException();

        } catch( TruckNotInInspectionException e ) {

            throw new ResponseStatusException( HttpStatus.CONFLICT, e.getMessage(), e );
        }

    }

}
