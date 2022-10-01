package com.vmware.tanzulabs.wehaul.reservations.domain;

public class ReservationAlreadyInProgressException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Reservation [%s] is already in progress";

    private Integer reservationId;

    public ReservationAlreadyInProgressException( Integer reservationId ) {
        super( String.format( MESSAGE_TEMPLATE, reservationId ));

        this.reservationId = reservationId;

    }

    public ReservationAlreadyInProgressException( Integer reservationId, String message ) {
        super( message + " : " + String.format( MESSAGE_TEMPLATE, reservationId ) );

        this.reservationId = reservationId;

    }

    public ReservationAlreadyInProgressException( Integer reservationId, String message, Throwable cause ) {
        super( message + " : " + String.format( MESSAGE_TEMPLATE, reservationId ), cause );

        this.reservationId = reservationId;

    }

    public Integer getReservationId() {

        return reservationId;
    }

}
