package com.frodgim.tickets.booking.service;

import com.frodgim.tickets.booking.dto.RouteDetailDTO;
import com.frodgim.tickets.booking.exceptions.BookingException;
import com.frodgim.tickets.booking.exceptions.BookingNotFound;
import com.frodgim.tickets.booking.exceptions.MaxCapacityExceededException;
import com.frodgim.tickets.booking.persistence.Booking;

public interface BookingManager {
    Booking getBooking(Long id) throws BookingNotFound;

    Booking doBooking(Booking booking) throws BookingException, MaxCapacityExceededException;

    Booking modifySeatBooking(Long id, String newSectionId) throws BookingException, MaxCapacityExceededException, BookingNotFound;

    void cancelBooking(Long id) throws BookingNotFound;

    RouteDetailDTO getRouteDetail(String sectionId) throws BookingException;

    void setMaxCapacity(int maxCapacity);
}
