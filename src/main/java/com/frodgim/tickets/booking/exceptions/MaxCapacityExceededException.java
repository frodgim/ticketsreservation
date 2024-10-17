package com.frodgim.tickets.booking.exceptions;

public class MaxCapacityExceededException extends  Exception {

    public MaxCapacityExceededException(String message){
        super(message);
    }
}
