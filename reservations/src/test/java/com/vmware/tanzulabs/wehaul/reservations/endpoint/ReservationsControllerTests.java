package com.vmware.tanzulabs.wehaul.reservations.endpoint;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest( ReservationsController.class )
public class ReservationsControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName( "Get All Reservations, Verify HTTP OK and Reservation List Returned" )
    void whenGetAllReservations_verifyOkAndReservationListReturned() throws Exception {

        this.mockMvc.perform( MockMvcRequestBuilders.get( "/reservations" ) )
                .andDo( print() )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.trucks" ).exists() );

    }

    @Test
    @DisplayName( "Create New Reservation, Verify HTTP Created and Reservation Resource Location Header Returned" )
    void whenCreateNewReservation_verifyCreatedAndReservationLocationReturned() throws Exception {

        this.mockMvc.perform( MockMvcRequestBuilders.post( "/reservations" ) )
                .andDo( print() )
                .andExpect( status().isCreated() )
                .andExpect( header().exists( HttpHeaders.LOCATION ) );

    }

    @Test
    @DisplayName( "Confirm Reservation Pickup, Verify HTTP Accepted" )
    void whenConfirmReservationPickup_verifyAccepted() throws Exception {

        this.mockMvc.perform( MockMvcRequestBuilders.put( "/reservations/{reservationId}/pickup", 1 ) )
                .andDo( print() )
                .andExpect( status().isAccepted() );

    }

    @Test
    @DisplayName( "Confirm Reservation Pickup, Verify HTTP Conflict and ReservationAlreadyInProgressException thrown" )
    void whenConfirmReservationPickup_verifyConflictAndReservationAlreadyInProgressExceptionReturned() throws Exception {

        this.mockMvc.perform( MockMvcRequestBuilders.put( "/reservations/{reservationId}/pickup", 1 ) )
                .andDo( print() )
                .andExpect( status().isConflict() );

    }

    @Test
    @DisplayName( "Confirm Reservation Drop-off, Verify HTTP Accepted" )
    void whenConfirmReservationDropoff_verifyAccepted() throws Exception {

        this.mockMvc.perform( MockMvcRequestBuilders.put( "/reservations/{reservationId}/dropoff", 1 ) )
                .andDo( print() )
                .andExpect( status().isAccepted() );

    }

}
