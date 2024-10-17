package com.frodgim.tickets.booking.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// I have not used JpaRepository<Booking, Long> because for this example we're not going to use any database
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {


}
