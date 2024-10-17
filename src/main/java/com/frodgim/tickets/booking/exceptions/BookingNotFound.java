package com.frodgim.tickets.booking.exceptions;

public class BookingNotFound extends  Exception {

    public BookingNotFound(Long id){
        super("Booking not found =>  id=" + id);
    }
}
