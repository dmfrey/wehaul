package com.vmware.tanzulabs.wehaul.fleetmanagement.endpoint;

import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.TruckNotInInspectionException;
import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.TruckUnavailableForInspectionException;
import com.vmware.tanzulabs.wehaul.fleetmanagement.usecases.in.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
class TrucksController {

    private final GetAllTrucksUsecase getAllTrucksUsecase;
    private final LookupTruckUsecase lookupTruckUsecase;
    private final CreateTruckUsecase createTruckUsecase;
    private final StartInspectionUsecase startInspectionUsecase;
    private final CompleteInspectionUsecase completeInspectionUsecase;

    TrucksController(
            final GetAllTrucksUsecase getAllTrucksUsecase,
            final LookupTruckUsecase lookupTruckUsecase,
            final CreateTruckUsecase createTruckUsecase,
            final StartInspectionUsecase startInspectionUsecase,
            final CompleteInspectionUsecase completeInspectionUsecase
    ) {

        this.getAllTrucksUsecase = getAllTrucksUsecase;
        this.lookupTruckUsecase = lookupTruckUsecase;
        this.createTruckUsecase = createTruckUsecase;
        this.startInspectionUsecase = startInspectionUsecase;
        this.completeInspectionUsecase = completeInspectionUsecase;

    }

    @GetMapping( "/trucks" )
    @CrossOrigin
    List<TruckResponse> getAllTrucks() {

        return this.getAllTrucksUsecase.execute().stream()
                .map( truck -> new TruckResponse( truck.id(), truck.status().name() ) )
                .toList();
    }

    @GetMapping( "/trucks/{truckId}" )
    @CrossOrigin
    TruckResponse getTrucks( @PathVariable Integer truckId ) {

        var found = this.lookupTruckUsecase.execute( truckId );

        return new TruckResponse( found.id(), found.status().name() );
    }

    record TruckResponse( Integer id, String status ) { }

    @PostMapping( "/trucks" )
    @CrossOrigin
    ResponseEntity<?> createNewTrucks() {

        var created = this.createTruckUsecase.execute();

        return ResponseEntity
                .created(
                        ServletUriComponentsBuilder.fromCurrentRequest()
                                .path( "/{truckId}" ).buildAndExpand( created )
                                .toUri()
                )
                .build();
    }

    @PutMapping( "/trucks/{truckId}/startInspection" )
    @CrossOrigin
    ResponseEntity<?> startInspection( @PathVariable Integer truckId ) {

        try {

            this.startInspectionUsecase.execute( truckId );

            return ResponseEntity.accepted()
                    .build();

        } catch( TruckUnavailableForInspectionException e ) {

            throw new ResponseStatusException( HttpStatus.CONFLICT, e.getMessage(), e );
        }

    }

    @PutMapping( "/trucks/{truckId}/completeInspection" )
    @CrossOrigin
    ResponseEntity<?> completeInspection( @PathVariable Integer truckId ) {

        try {

            this.completeInspectionUsecase.execute( truckId );

            return ResponseEntity.accepted()
                    .build();

        } catch( TruckNotInInspectionException e ) {

            throw new ResponseStatusException( HttpStatus.CONFLICT, e.getMessage(), e );
        }

    }

}
