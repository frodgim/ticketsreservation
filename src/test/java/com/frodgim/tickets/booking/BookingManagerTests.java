package com.frodgim.tickets.booking;

import com.frodgim.tickets.booking.exceptions.BookingException;
import com.frodgim.tickets.booking.exceptions.BookingNotFound;
import com.frodgim.tickets.booking.exceptions.MaxCapacityExceededException;
import com.frodgim.tickets.booking.persistence.Booking;
import com.frodgim.tickets.booking.service.BookingManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookingManagerTests {
    @Autowired
    private BookingManager bookingManager;


    private Booking getSampleBooking(Long id, String fullName, String sectionId ){
        return new Booking(){
            {
                setId(id);

                if(fullName != null) {
                    setFullName(fullName);
                }
                else{
                    setFullName("");
                }

                setSectionId(sectionId);
            }
        };

    }

    @Test
    public void testGetBooking_NotFound() {
        //Arrange

        //Action

        //Assert
        assertThrowsExactly(BookingNotFound.class, () -> bookingManager.getBooking(1L));
    }

    @Test
    public void testGetBooking_Success() throws BookingException, MaxCapacityExceededException {
        //Arrange
        Booking bookingSample = getSampleBooking(1L, "Paco Rodriguez", "A");
        //Action
        Booking booking = bookingManager.doBooking(bookingSample);
        //Assert
        assertEquals(booking.getId(), bookingSample.getId());
    }
}
