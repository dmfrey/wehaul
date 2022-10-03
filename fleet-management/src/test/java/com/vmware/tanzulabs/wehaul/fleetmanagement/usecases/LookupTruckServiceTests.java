package com.vmware.tanzulabs.wehaul.fleetmanagement.usecases;

import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.Status;
import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.Truck;
import com.vmware.tanzulabs.wehaul.fleetmanagement.usecases.out.FindTruckPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class LookupTruckServiceTests {

    LookupTruckService subject;

    FindTruckPort mockFindTruckPort;

    private final Integer fakeId = 1;
    private final Status fakeStatus = Status.AVAILABLE;

    @BeforeEach
    void setup() {

        this.mockFindTruckPort = mock( FindTruckPort.class );

        this.subject = new LookupTruckService( this.mockFindTruckPort );

    }

    @Test
    void testExecute() {

        when( this.mockFindTruckPort.find( fakeId ) ).thenReturn( new Truck( fakeId, fakeStatus ) );

        var actual = this.subject.execute( fakeId );

        var expected = new Truck( fakeId, fakeStatus );

        assertThat( actual ).isEqualTo( expected );

        verify( this.mockFindTruckPort ).find( fakeId );
        verifyNoMoreInteractions( this.mockFindTruckPort );

    }

}
