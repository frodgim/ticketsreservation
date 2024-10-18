package com.frodgim.tickets.booking.exceptions;

public class BookingNotFoundException extends  BookingException {

    public BookingNotFoundException(Long id){
        super("Booking not found =>  id=" + id);
    }
}
