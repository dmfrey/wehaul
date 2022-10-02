package com.vmware.tanzulabs.wehaul.fleetmanagement.usecases;

import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.Status;
import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.Truck;
import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.TruckUnavailableForInspectionException;
import com.vmware.tanzulabs.wehaul.fleetmanagement.usecases.out.FindTruckPort;
import com.vmware.tanzulabs.wehaul.fleetmanagement.usecases.out.StartInspectionPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.*;

public class StartInspectionServiceTests {

    StartInspectionService subject;

    FindTruckPort mockFindTruckPort;
    StartInspectionPort mockStartInspectionPort;

    @BeforeEach
    void setup() {

        this.mockFindTruckPort = mock( FindTruckPort.class );
        this.mockStartInspectionPort = mock( StartInspectionPort.class );

        this.subject = new StartInspectionService( this.mockFindTruckPort, this.mockStartInspectionPort );

    }

    @Test
    void testExecute() {

        var fakeId = 1;
        var fakeTruck = new Truck( fakeId, Status.AVAILABLE );
        when( this.mockFindTruckPort.find( fakeId ) ).thenReturn( fakeTruck );

        this.subject.execute( fakeId );

        verify( this.mockFindTruckPort ).find( fakeId );
        verify( this.mockStartInspectionPort ).start( fakeId );
        verifyNoMoreInteractions( this.mockFindTruckPort, this.mockStartInspectionPort );

    }

    @Test
    void testExecute_verifyTruckUnavailableForInspectionException() {

        var fakeId = 1;
        var fakeTruck = new Truck( fakeId, Status.UNAVAILABLE );
        when( this.mockFindTruckPort.find( fakeId ) ).thenReturn( fakeTruck );

        assertThrowsExactly(TruckUnavailableForInspectionException.class, () -> this.subject.execute( fakeId ) );

        verify( this.mockFindTruckPort ).find( fakeId );
        verifyNoMoreInteractions( this.mockFindTruckPort );
        verifyNoInteractions( this.mockStartInspectionPort );

    }

}
