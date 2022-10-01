package com.vmware.tanzulabs.wehaul.reservations.endpoint;

import com.vmware.tanzulabs.wehaul.reservations.domain.Reservation;
import com.vmware.tanzulabs.wehaul.reservations.domain.ReservationAlreadyInProgressException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ReservationsController {

    @GetMapping( "/reservations" )
    @CrossOrigin
    List<Reservation> getAllReservations() {

        throw new UnsupportedOperationException();
    }

    @PostMapping( "/reservations" )
    @CrossOrigin
    ResponseEntity<?> createNewReservation() {

        throw new UnsupportedOperationException();
    }

    @PutMapping( "/reservations/{reservationId}/pickup" )
    @CrossOrigin
    ResponseEntity<?> pickup( @PathVariable Integer reservationId ) {

        try {

            throw new UnsupportedOperationException();

        } catch( ReservationAlreadyInProgressException e ) {

            throw new ResponseStatusException( HttpStatus.CONFLICT, e.getMessage(), e );
        }

    }

    @PutMapping( "/reservations/{reservationId}/dropoff" )
    @CrossOrigin
    ResponseEntity<?> dropoff( @PathVariable Integer reservationId ) {

        throw new UnsupportedOperationException();
    }

}
