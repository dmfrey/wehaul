package com.vmware.tanzulabs.wehaul.fleetmanagement.persistence;

import com.vmware.tanzulabs.wehaul.fleetmanagement.domain.Status;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table( name = "truck" )
class TruckEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
    private Integer id;

    @Column( name = "status" )
    @Enumerated( EnumType.STRING )
    private Status status;

    public Integer getId() {

        return id;
    }

    public void setId( Integer id ) {

        this.id = id;

    }

    public Status getStatus() {

        return status;
    }

    public void setStatus( Status status ) {

        this.status = status;

    }

    @Override
    public boolean equals( Object o ) {
        if( this == o ) return true;
        if( o == null || getClass() != o.getClass() ) return false;

        TruckEntity that = (TruckEntity) o;

        return id.equals( that.id );
    }

    @Override
    public int hashCode() {

        return Objects.hash( id );
    }

    @Override
    public String toString() {
        return "TruckEntity{" +
                "id=" + id +
                ", status=" + status +
                '}';
    }

}
