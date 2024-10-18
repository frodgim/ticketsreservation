package com.frodgim.tickets.booking.exception;

public class MaxCapacityExceededException extends  BookingException {

    public MaxCapacityExceededException(String message){
        super(message);
    }
}
