package com.frodgim.tickets.booking;


import com.frodgim.tickets.booking.exceptions.BookingException;
import com.frodgim.tickets.booking.exceptions.BookingNotFound;
import com.frodgim.tickets.booking.exceptions.MaxCapacityExceededException;
import com.frodgim.tickets.booking.persistence.Booking;
import com.frodgim.tickets.booking.service.BookingManagerNoScale;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BookingManagerNoScale bookingManagerNoScale;

    @GetMapping("/{id}")
    public ResponseEntity<Booking> retrieveBooking(@PathVariable Long id) throws BookingNotFound {

        LOGGER.debug("Entering :: retrieveBooking methods");
        Booking booking = bookingManagerNoScale.getBooking(id);

        return ResponseEntity.ok(booking);
    }

    @PostMapping()
    public ResponseEntity<Booking> postBooking(@Valid @RequestBody Booking booking) throws BookingException, MaxCapacityExceededException {
        Booking processedBooking = bookingManagerNoScale.doBooking(booking);

        return ResponseEntity.ok(processedBooking);

    }

    @PutMapping("/modify/{id}/{sectionId}")
    public ResponseEntity<Booking> putBooking(@PathVariable Long id, @PathVariable String sectionId) throws BookingNotFound, BookingException, MaxCapacityExceededException  {
        Booking processedBooking = bookingManagerNoScale.modifySeatBooking(id,sectionId);

        return ResponseEntity.ok(processedBooking);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> cancelBooking(@PathVariable Long id) throws  BookingNotFound {
        bookingManagerNoScale.cancelBooking(id);

        return ResponseEntity.noContent().build();
    }
}
