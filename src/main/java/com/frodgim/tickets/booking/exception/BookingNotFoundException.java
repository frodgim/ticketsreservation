package com.frodgim.tickets.booking.exception;

public class BookingNotFoundException extends  BookingException {

    public BookingNotFoundException(Long id){
        super("Booking not found =>  id=" + id);
    }
}
