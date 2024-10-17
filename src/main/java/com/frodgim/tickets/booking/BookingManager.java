package com.frodgim.tickets.booking;

import com.frodgim.tickets.booking.exceptions.BookingException;
import com.frodgim.tickets.booking.exceptions.BookingNotFound;
import com.frodgim.tickets.booking.exceptions.MaxCapacityExceededException;
import com.frodgim.tickets.booking.persistence.Booking;
import com.frodgim.tickets.booking.persistence.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookingManager {

    private static final int MAX_CAPACITY = 10;

    @Autowired
    private BookingRepository bookingRepository;

    public Optional<Booking> getBooking(Long id) throws BookingNotFound {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    public void doBooking(Booking booking) throws BookingException, MaxCapacityExceededException{

    }


}
