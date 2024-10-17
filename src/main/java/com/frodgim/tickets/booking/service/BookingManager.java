package com.frodgim.tickets.booking.service;

import com.frodgim.tickets.booking.dto.RouteDetailDTO;
import com.frodgim.tickets.booking.exception.BookingException;
import com.frodgim.tickets.booking.exception.BookingNotFoundException;
import com.frodgim.tickets.booking.exception.MaxCapacityExceededException;
import com.frodgim.tickets.booking.persistence.Booking;

public interface BookingManager {
    Booking getBooking(Long id) throws BookingNotFoundException;

    Booking doBooking(Booking booking) throws BookingException, MaxCapacityExceededException;

    Booking modifySeatBooking(Long id, String newSectionId) throws BookingException, MaxCapacityExceededException, BookingNotFoundException;

    void cancelBooking(Long id) throws BookingNotFoundException;

    RouteDetailDTO getRouteDetail(String sectionId) throws BookingException;

    void setMaxCapacity(int maxCapacity);

    void clear();
}
