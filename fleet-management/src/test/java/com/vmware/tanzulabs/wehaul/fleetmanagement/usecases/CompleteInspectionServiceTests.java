package com.vmware.tanzulabs.wehaul.fleetmanagement.usecases;

import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.Status;
import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.Truck;
import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.TruckNotInInspectionException;
import com.vmware.tanzulabs.wehaul.fleetmanagement.usecases.out.CompleteInspectionPort;
import com.vmware.tanzulabs.wehaul.fleetmanagement.usecases.out.FindTruckPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.*;

public class CompleteInspectionServiceTests {

    CompleteInspectionService subject;

    FindTruckPort mockFindTruckPort;
    CompleteInspectionPort mockCompleteInspectionPort;

    @BeforeEach
    void setup() {

        this.mockFindTruckPort = mock( FindTruckPort.class );
        this.mockCompleteInspectionPort = mock( CompleteInspectionPort.class );

        this.subject = new CompleteInspectionService( this.mockFindTruckPort, this.mockCompleteInspectionPort );

    }

    @Test
    void testExecute() {

        var fakeId = 1;
        var fakeTruck = new Truck( fakeId, Status.UNAVAILABLE );
        when( this.mockFindTruckPort.find( fakeId ) ).thenReturn( fakeTruck );

        this.subject.execute( fakeId );

        verify( this.mockFindTruckPort ).find( fakeId );
        verify( this.mockCompleteInspectionPort ).complete( fakeId );
        verifyNoMoreInteractions( this.mockFindTruckPort, this.mockCompleteInspectionPort );

    }

    @Test
    void testExecute_verifyTruckNotInInspectionException() {

        var fakeId = 1;
        var fakeTruck = new Truck( fakeId, Status.AVAILABLE );
        when( this.mockFindTruckPort.find( fakeId ) ).thenReturn( fakeTruck );

        assertThrowsExactly( TruckNotInInspectionException.class, () -> this.subject.execute( fakeId ) );

        verify( this.mockFindTruckPort ).find( fakeId );
        verifyNoMoreInteractions( this.mockFindTruckPort );
        verifyNoInteractions( this.mockCompleteInspectionPort );

    }

}
