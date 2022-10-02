package com.vmware.tanzulabs.wehaul.fleetmanagement.persistence;

import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.Status;
import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.Truck;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import( TrucksPersistenceAdapter.class )
class TruckPersistenceAdapterTests {

    @Autowired
    TrucksPersistenceAdapter subject;

    @Autowired
    TruckRepository truckRepository;

    @Test
    void testGetAll() {

        var entity = new TruckEntity();
        entity.setStatus( Status.AVAILABLE );

        var created = this.truckRepository.save( entity );

        List<Truck> actual = this.subject.getAll();

        var expected = new Truck( created.getId(), created.getStatus() );

        assertThat( actual )
                .hasSize( 1 )
                .containsExactly( expected );

    }

    @Test
    void testCreateNew() {

        var actual = this.subject.createNew( new Truck( null, Status.AVAILABLE ) );

        var expected = this.truckRepository.findById( actual ).get();
        assertThat( actual ).isEqualTo( expected.getId() );

    }

    @Test
    void testFind() {

        var entity = new TruckEntity();
        entity.setStatus( Status.AVAILABLE );

        var created = this.truckRepository.save( entity );

        var actual = this.subject.find( created.getId() );

        var expected = new Truck( created.getId(), Status.AVAILABLE );

        assertThat( actual ).isEqualTo( expected );

    }

    @Test
    void testStart() {

        var entity = new TruckEntity();
        entity.setStatus( Status.AVAILABLE );

        var created = this.truckRepository.save( entity );

        this.subject.start( created.getId() );

        var updated = this.truckRepository.findById( created.getId() ).get();

        var expected = new TruckEntity();
        expected.setId( created.getId() );
        expected.setStatus( Status.UNAVAILABLE );

        assertThat( updated ).isEqualTo( expected );

    }

    @Test
    void testComplete() {

        var entity = new TruckEntity();
        entity.setStatus( Status.UNAVAILABLE );

        var created = this.truckRepository.save( entity );

        this.subject.complete( created.getId() );

        var updated = this.truckRepository.findById( created.getId() ).get();

        var expected = new TruckEntity();
        expected.setId( created.getId() );
        expected.setStatus( Status.AVAILABLE );

        assertThat( updated ).isEqualTo( expected );

    }

}
