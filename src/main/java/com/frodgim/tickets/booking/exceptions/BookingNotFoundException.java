package com.frodgim.tickets.booking.exceptions;

public class BookingNotFoundException extends  Exception {

    public BookingNotFoundException(Long id){
        super("Booking not found =>  id=" + id);
    }
}
