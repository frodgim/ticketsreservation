package com.frodgim.tickets.booking;


import com.frodgim.tickets.booking.exceptions.BookingException;
import com.frodgim.tickets.booking.exceptions.BookingNotFound;
import com.frodgim.tickets.booking.exceptions.MaxCapacityExceededException;
import com.frodgim.tickets.booking.persistence.Booking;
import com.frodgim.tickets.booking.service.BookingManagerInMemory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingManagerInMemory bookingManager;

    @GetMapping("/{id}")
    public ResponseEntity<Booking> retrieveBooking(@PathVariable Long id) throws BookingNotFound {
        try {
            Booking booking = bookingManager.getBooking(id);
            return ResponseEntity.ok(booking);
        }
        catch (BookingNotFound e){
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping()
    public ResponseEntity<Booking> postBooking(@Valid @RequestBody Booking booking) throws BookingException, MaxCapacityExceededException {
        Booking processedBooking = bookingManager.doBooking(booking);

        return ResponseEntity.ok(processedBooking);

    }

    @PutMapping("/modify/{id}/{sectionId}")
    public ResponseEntity<Booking> putBooking(@PathVariable Long id, @PathVariable String sectionId) throws BookingNotFound, BookingException, MaxCapacityExceededException  {
        try {
            Booking processedBooking = bookingManager.modifySeatBooking(id,sectionId);

            return ResponseEntity.ok(processedBooking);

        }
        catch (BookingNotFound e){
            return ResponseEntity.notFound().build();
        }


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> cancelBooking(@PathVariable Long id) throws  BookingNotFound {
        try {
            bookingManager.cancelBooking(id);

            return ResponseEntity.noContent().build();
        }
        catch (BookingNotFound e){
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/capacity")
    public ResponseEntity<Booking> postBooking(@RequestBody Map<String, String> payload) throws BookingException, MaxCapacityExceededException {
        if(payload == null || !payload.containsKey("capacity") || payload.get("capacity") == null || payload.get("capacity").isEmpty()){
            ResponseEntity.badRequest();
        }

        int capacity = Integer.parseInt(payload.get("capacity"));
        bookingManager.setMaxCapacity(capacity);

        return ResponseEntity.noContent().build();

    }
}
