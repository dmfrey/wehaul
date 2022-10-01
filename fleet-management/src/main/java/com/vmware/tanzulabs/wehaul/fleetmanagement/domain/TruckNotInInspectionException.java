package com.vmware.tanzulabs.wehaul.fleetmanagement.domain;

public class TruckNotInInspectionException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Truck [%s] is not currently in inspection";

    private Integer truckId;

    public TruckNotInInspectionException( Integer truckId ) {
        super( String.format( MESSAGE_TEMPLATE, truckId ));

        this.truckId = truckId;

    }

    public TruckNotInInspectionException( Integer truckId, String message ) {
        super( message + " : " + String.format( MESSAGE_TEMPLATE, truckId ) );

        this.truckId = truckId;

    }

    public TruckNotInInspectionException( Integer truckId, String message, Throwable cause ) {
        super( message + " : " + String.format( MESSAGE_TEMPLATE, truckId ), cause );

        this.truckId = truckId;

    }

    public Integer getTruckId() {

        return truckId;
    }

}
