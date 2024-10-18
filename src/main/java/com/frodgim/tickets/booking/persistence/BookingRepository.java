package com.frodgim.tickets.booking.persistence;

import java.util.Optional;

// I have not used JpaRepository<Booking, Long> is a requirement of this assessment test
public interface BookingRepository{

    Optional<Booking> findById(Long id);
    Booking save(Booking booking);
    void delete(Booking booking);
}
