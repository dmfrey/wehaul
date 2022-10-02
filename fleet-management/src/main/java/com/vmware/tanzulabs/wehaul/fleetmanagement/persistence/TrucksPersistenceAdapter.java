package com.vmware.tanzulabs.wehaul.fleetmanagement.persistence;

import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.Status;
import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.Truck;
import com.vmware.tanzulabs.wehaul.fleetmanagement.usecases.out.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

@Component
@Transactional
class TrucksPersistenceAdapter implements
        GetAllTrucksPort, CreateTruckPort, FindTruckPort, StartInspectionPort, CompleteInspectionPort {

    private final TruckRepository truckRepository;

    TrucksPersistenceAdapter( final TruckRepository truckRepository ) {

        this.truckRepository = truckRepository;

    }

    @Override
    public List<Truck> getAll() {

        return StreamSupport.stream( this.truckRepository.findAll().spliterator(), true )
                .map( entity -> new Truck( entity.getId(), entity.getStatus() ) )
                .toList();
    }

    @Override
    public Integer createNew( final Truck truck ) {

        var entity = new TruckEntity();
        entity.setStatus( truck.status() );

        var created = this.truckRepository.save( entity );

        return created.getId();
    }

    @Override
    public Truck find( final Integer truckId ) {

        var foundOptional = this.truckRepository.findById( truckId );
        if( foundOptional.isPresent() ) {

            var found = foundOptional.get();
            return new Truck( found.getId(), found.getStatus() );

        } else {

            throw new IllegalArgumentException( String.format( "Truck [%s] not found!", truckId ) );
        }

    }

    @Override
    public void start( final Integer truckId ) {

        var foundOptional = this.truckRepository.findById( truckId );
        if( foundOptional.isPresent() ) {

            var found = foundOptional.get();
            found.setStatus( Status.UNAVAILABLE );
            this.truckRepository.save( found );

        } else {

            throw new IllegalArgumentException( String.format( "Truck [%s] not found!", truckId ) );
        }

    }

    @Override
    public void complete( final Integer truckId ) {

        var foundOptional = this.truckRepository.findById( truckId );
        if( foundOptional.isPresent() ) {

            var found = foundOptional.get();
            found.setStatus( Status.AVAILABLE );
            this.truckRepository.save( found );

        } else {

            throw new IllegalArgumentException( String.format( "Truck [%s] not found!", truckId ) );
        }

    }

}
