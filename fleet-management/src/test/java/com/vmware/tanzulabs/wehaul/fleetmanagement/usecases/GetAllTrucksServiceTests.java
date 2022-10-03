package com.vmware.tanzulabs.wehaul.fleetmanagement.usecases;

import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.Status;
import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.Truck;
import com.vmware.tanzulabs.wehaul.fleetmanagement.usecases.out.GetAllTrucksPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class GetAllTrucksServiceTests {

    GetAllTrucksService subject;

    GetAllTrucksPort mockGetAllTrucksPort;

    private final Integer fakeId = 1;
    private final Status fakeStatus = Status.AVAILABLE;

    @BeforeEach
    void setup() {

        this.mockGetAllTrucksPort = mock( GetAllTrucksPort.class );

        this.subject = new GetAllTrucksService( this.mockGetAllTrucksPort );

    }

    @Test
    void testExecute() {

        when( this.mockGetAllTrucksPort.getAll() ).thenReturn( List.of( new Truck( fakeId, fakeStatus ) ) );

        var actual = this.subject.execute();

        var expected = new Truck( fakeId, fakeStatus );

        assertThat( actual )
                .hasSize( 1 )
                .containsExactly( expected );

        verify( this.mockGetAllTrucksPort ).getAll();
        verifyNoMoreInteractions( this.mockGetAllTrucksPort );

    }

}
