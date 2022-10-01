package com.vmware.tanzulabs.wehaul.fleetmanagement.endpoint;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest( TrucksController.class )
public class TrucksControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName( "Get All Trucks, Verify HTTP OK and Truck List Returned" )
    void whenGetAllTrucks_verifyOkAndTruckListReturned() throws Exception {

        this.mockMvc.perform( MockMvcRequestBuilders.get( "/trucks" ) )
                .andDo( print() )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.trucks" ).exists() );

    }

    @Test
    @DisplayName( "Create New Truck, Verify HTTP Created and Truck Resource Location Header Returned" )
    void whenCreateNewTruck_verifyCreatedAndTruckLocationReturned() throws Exception {

        this.mockMvc.perform( MockMvcRequestBuilders.post( "/trucks" ) )
                .andDo( print() )
                .andExpect( status().isCreated() )
                .andExpect( header().exists( HttpHeaders.LOCATION ) );

    }

    @Test
    @DisplayName( "Start Truck Inspection, Verify HTTP Accepted" )
    void whenStartTruckInspection_verifyAccepted() throws Exception {

        this.mockMvc.perform( MockMvcRequestBuilders.put( "/trucks/{truckId}/startInspection", 1 ) )
                .andDo( print() )
                .andExpect( status().isAccepted() );

    }

    @Test
    @DisplayName( "Start Truck Inspection, Verify HTTP Conflict and TruckUnavailableForInspectionException thrown" )
    void whenStartTruckInspection_verifyConflictAndTruckUnavailableForInspectionExceptionReturned() throws Exception {

        this.mockMvc.perform( MockMvcRequestBuilders.put( "/trucks/{truckId}/startInspection", 1 ) )
                .andDo( print() )
                .andExpect( status().isConflict() );

    }

    @Test
    @DisplayName( "Complete Truck Inspection, Verify HTTP Accepted" )
    void whenCompleteTruckInspection_verifyAccepted() throws Exception {

        this.mockMvc.perform( MockMvcRequestBuilders.put( "/trucks/{truckId}/completeInspection", 1 ) )
                .andDo( print() )
                .andExpect( status().isAccepted() );

    }

    @Test
    @DisplayName( "Complete Truck Inspection, Verify HTTP Conflict and TruckNotInInspectionException thrown" )
    void whenCompleteTruckInspection_verifyConflictAndTruckNotInInspectionExceptionReturned() throws Exception {

        this.mockMvc.perform( MockMvcRequestBuilders.put( "/trucks/{truckId}/completeInspection", 1 ) )
                .andDo( print() )
                .andExpect( status().isConflict() );

    }

}
