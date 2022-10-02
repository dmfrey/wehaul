package com.vmware.tanzulabs.wehaul.fleetmanagement.endpoint;

import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.Status;
import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.Truck;
import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.TruckNotInInspectionException;
import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.TruckUnavailableForInspectionException;
import com.vmware.tanzulabs.wehaul.fleetmanagement.usecases.in.CompleteInspectionUsecase;
import com.vmware.tanzulabs.wehaul.fleetmanagement.usecases.in.CreateTruckUsecase;
import com.vmware.tanzulabs.wehaul.fleetmanagement.usecases.in.GetAllTrucksUsecase;
import com.vmware.tanzulabs.wehaul.fleetmanagement.usecases.in.StartInspectionUsecase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest( TrucksController.class )
public class TrucksControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    GetAllTrucksUsecase mockGetAllTrucksUsecase;

    @MockBean
    CreateTruckUsecase mockCreateTrruckUsecase;

    @MockBean
    StartInspectionUsecase mockStartInspectionUsecase;

    @MockBean
    CompleteInspectionUsecase mockCompleteInspectionUsecase;

    @Test
    @DisplayName( "Get All Trucks, Verify HTTP OK and Truck List Returned" )
    void whenGetAllTrucks_verifyOkAndTruckListReturned() throws Exception {

        when( this.mockGetAllTrucksUsecase.execute() ).thenReturn( List.of( new Truck( 1, Status.AVAILABLE ) ) );

        this.mockMvc.perform( MockMvcRequestBuilders.get( "/trucks" ) )
                .andDo( print() )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.[0].id" ).value( 1 ) )
                .andExpect( jsonPath( "$.[0].status").value( "AVAILABLE" ) );

        verify( this.mockGetAllTrucksUsecase ).execute();
        verifyNoMoreInteractions( this.mockGetAllTrucksUsecase );
        verifyNoInteractions( this.mockCreateTrruckUsecase, this.mockStartInspectionUsecase, this.mockCompleteInspectionUsecase );

    }

    @Test
    @DisplayName( "Create New Truck, Verify HTTP Created and Truck Resource Location Header Returned" )
    void whenCreateNewTruck_verifyCreatedAndTruckLocationReturned() throws Exception {

        when( this.mockCreateTrruckUsecase.execute() ).thenReturn( 1 );

        this.mockMvc.perform( MockMvcRequestBuilders.post( "/trucks" ) )
                .andDo( print() )
                .andExpect( status().isCreated() )
                .andExpect( header().exists( HttpHeaders.LOCATION ) );

        verify( this.mockCreateTrruckUsecase ).execute();
        verifyNoMoreInteractions( this.mockCreateTrruckUsecase );
        verifyNoInteractions( this.mockGetAllTrucksUsecase, this.mockStartInspectionUsecase, this.mockCompleteInspectionUsecase );

    }

    @Test
    @DisplayName( "Start Truck Inspection, Verify HTTP Accepted" )
    void whenStartTruckInspection_verifyAccepted() throws Exception {

        var fakeId = 1;
        this.mockMvc.perform( MockMvcRequestBuilders.put( "/trucks/{truckId}/startInspection", fakeId ) )
                .andDo( print() )
                .andExpect( status().isAccepted() );

        verify( this.mockStartInspectionUsecase ).execute( fakeId );
        verifyNoMoreInteractions( this.mockStartInspectionUsecase );
        verifyNoInteractions( this.mockGetAllTrucksUsecase, this.mockCreateTrruckUsecase, this.mockCompleteInspectionUsecase );

    }

    @Test
    @DisplayName( "Start Truck Inspection, Verify HTTP Conflict and TruckUnavailableForInspectionException thrown" )
    void whenStartTruckInspection_verifyConflictAndTruckUnavailableForInspectionExceptionReturned() throws Exception {

        var fakeId = 1;
        willThrow( new TruckUnavailableForInspectionException( fakeId ) )
                .given( this.mockStartInspectionUsecase )
                        .execute( fakeId );

        this.mockMvc.perform( MockMvcRequestBuilders.put( "/trucks/{truckId}/startInspection", fakeId ) )
                .andDo( print() )
                .andExpect( status().isConflict() );

        verify( this.mockStartInspectionUsecase ).execute( fakeId );
        verifyNoMoreInteractions( this.mockStartInspectionUsecase );
        verifyNoInteractions( this.mockGetAllTrucksUsecase, this.mockCreateTrruckUsecase, this.mockCompleteInspectionUsecase );

    }

    @Test
    @DisplayName( "Complete Truck Inspection, Verify HTTP Accepted" )
    void whenCompleteTruckInspection_verifyAccepted() throws Exception {

        var fakeId = 1;
        this.mockMvc.perform( MockMvcRequestBuilders.put( "/trucks/{truckId}/completeInspection", fakeId ) )
                .andDo( print() )
                .andExpect( status().isAccepted() );

        verify( this.mockCompleteInspectionUsecase ).execute( fakeId );
        verifyNoMoreInteractions( this.mockCompleteInspectionUsecase );
        verifyNoInteractions( this.mockGetAllTrucksUsecase, this.mockCreateTrruckUsecase, this.mockStartInspectionUsecase );

    }

    @Test
    @DisplayName( "Complete Truck Inspection, Verify HTTP Conflict and TruckNotInInspectionException thrown" )
    void whenCompleteTruckInspection_verifyConflictAndTruckNotInInspectionExceptionReturned() throws Exception {

        var fakeId = 1;
        willThrow( new TruckNotInInspectionException( fakeId ) )
                .given( this.mockCompleteInspectionUsecase )
                .execute( fakeId );

        this.mockMvc.perform( MockMvcRequestBuilders.put( "/trucks/{truckId}/completeInspection", fakeId ) )
                .andDo( print() )
                .andExpect( status().isConflict() );

        verify( this.mockCompleteInspectionUsecase ).execute( fakeId );
        verifyNoMoreInteractions( this.mockCompleteInspectionUsecase );
        verifyNoInteractions( this.mockGetAllTrucksUsecase, this.mockCreateTrruckUsecase, this.mockStartInspectionUsecase );

    }

}
