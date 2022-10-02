package com.vmware.tanzulabs.wehaul.fleetmanagement.usecases;

import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.Status;
import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.Truck;
import com.vmware.tanzulabs.wehaul.fleetmanagement.usecases.out.CreateTruckPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CreateTruckServiceTests {

    CreateTruckService subject;

    CreateTruckPort mockCreateTruckPort;

    private final Integer fakeId = 1;
    private final Status fakeStatus = Status.AVAILABLE;

    @BeforeEach
    void setup() {

        this.mockCreateTruckPort = mock( CreateTruckPort.class );

        this.subject = new CreateTruckService( this.mockCreateTruckPort );

    }

    @Test
    void testExecute() {

        var fakeTruck = new Truck( null, fakeStatus );
        when( this.mockCreateTruckPort.createNew( fakeTruck ) ).thenReturn( fakeId );

        var actual = this.subject.execute();

        var expected = fakeId;

        assertThat( actual ).isEqualTo( expected );

        verify( this.mockCreateTruckPort ).createNew( fakeTruck );
        verifyNoMoreInteractions( this.mockCreateTruckPort );

    }

}
