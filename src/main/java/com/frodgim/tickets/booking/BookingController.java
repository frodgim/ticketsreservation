package com.frodgim.tickets.booking;


import com.frodgim.tickets.booking.dto.BookingDTO;
import com.frodgim.tickets.booking.dto.CapacityDTO;
import com.frodgim.tickets.booking.exceptions.BookingException;
import com.frodgim.tickets.booking.exceptions.BookingNotFound;
import com.frodgim.tickets.booking.exceptions.MaxCapacityExceededException;
import com.frodgim.tickets.booking.persistence.Booking;
import com.frodgim.tickets.booking.service.BookingManagerInMemory;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingManagerInMemory bookingManager;

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> retrieveBooking(@PathVariable Long id) throws BookingNotFound {
        try {
            BookingDTO booking = new BookingDTO(){
                {
                    updateFromEntity(bookingManager.getBooking(id));
                }
            };

            return ResponseEntity.ok(booking);
        }
        catch (BookingNotFound e){
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping()
    public ResponseEntity<Booking> doBooking(@Valid @RequestBody BookingDTO booking) throws BookingException, MaxCapacityExceededException {
        Booking processedBooking = bookingManager.doBooking(booking.convertToEntity());

        return ResponseEntity.ok(processedBooking);

    }

    @PutMapping("/modify/{id}/{sectionId}")
    public ResponseEntity<BookingDTO> modifySeat(@PathVariable Long id, @PathVariable String sectionId) throws BookingNotFound, BookingException, MaxCapacityExceededException  {
        try {
            Booking processedBooking = bookingManager.modifySeatBooking(id,sectionId);

            BookingDTO booking = new BookingDTO(){
                {
                    updateFromEntity(processedBooking);
                }
            };
            return ResponseEntity.ok(booking);

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
    public ResponseEntity<Object> putMaxCapacity(@Valid @RequestBody CapacityDTO capacity) throws BookingException, MaxCapacityExceededException {

        bookingManager.setMaxCapacity(capacity.getMaxCapacity());

        return ResponseEntity.noContent().build();

    }
}
