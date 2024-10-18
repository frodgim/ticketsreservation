package com.frodgim.tickets.booking.exceptions;

public class MaxCapacityExceededException extends  BookingException {

    public MaxCapacityExceededException(String message){
        super(message);
    }
}
