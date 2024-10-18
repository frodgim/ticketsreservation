package com.frodgim.tickets.booking;

import com.frodgim.tickets.booking.dto.RouteDetailDTO;
import com.frodgim.tickets.booking.exceptions.BookingException;
import com.frodgim.tickets.booking.exceptions.BookingNotFoundException;
import com.frodgim.tickets.booking.exceptions.MaxCapacityExceededException;
import com.frodgim.tickets.booking.persistence.Booking;
import com.frodgim.tickets.booking.service.BookingManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookingManagerTests {
    @Autowired
    private BookingManager bookingManager;

    @BeforeEach
    public void clearManager(){
        bookingManager.clear();
    }

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
        BookingNotFoundException exception = assertThrows(BookingNotFoundException.class,
                    () -> bookingManager.getBooking(1L));
    }

    @Test
    public void testGetBooking_Success() throws BookingException, MaxCapacityExceededException {
        //Arrange
        Booking bookingSample = getSampleBooking(1L, "Paco Rodriguez", "A");
        //Action
        Booking booking = bookingManager.doBooking(bookingSample);
        //Assert
        assertEquals(bookingSample.getId(), booking.getId());
    }

    @Test
    public void testDoBooking_Success() throws BookingException, MaxCapacityExceededException {
        //Arrange
        Booking bookingSample = getSampleBooking(1L, "Paco Rodriguez", "A");
        //Action
        bookingManager.setMaxCapacity(10);
        Booking booking = bookingManager.doBooking(bookingSample);
        //Assert
        assertEquals(bookingSample.getId(), booking.getId());
    }

    @Test
    public void testDoBooking_InvalidSection() throws BookingException, MaxCapacityExceededException {
        //Arrange
        Booking bookingSample = getSampleBooking(1L, "Paco Rodriguez", "Z");
        //Action

        //Assert
        BookingException exception = assertThrows(BookingException.class,
                () -> bookingManager.doBooking(bookingSample));
    }

    @Test
    public void testDoBooking_MaxCapacityExceeded() throws BookingException, MaxCapacityExceededException {
        //Arrange
        Booking bookingSample = getSampleBooking(1L, "Paco Rodriguez", "A");
        //Action
        bookingManager.setMaxCapacity(1);
        Booking booking = bookingManager.doBooking(bookingSample);
        //Assert
        MaxCapacityExceededException exception = assertThrows(MaxCapacityExceededException.class,
                () -> bookingManager.doBooking(bookingSample));
    }

    @Test
    public void testmodifySeat_SameSection() throws BookingException, MaxCapacityExceededException {
        //Arrange
        Booking bookingSample = getSampleBooking(1L, "Paco Rodriguez", "A");
        //Action
        Booking booking = bookingManager.doBooking(bookingSample);

        //Assert
        BookingException exception = assertThrows(BookingException.class,
                () -> bookingManager.modifySeatBooking(booking.getId(), "A"));

        assertEquals("You have already booked in the same section", exception.getMessage());
    }

    @Test
    public void testmodifySeat_SucceedSection() throws BookingException, MaxCapacityExceededException, BookingNotFoundException {
        //Arrange
        Booking bookingSample = getSampleBooking(1L, "Paco Rodriguez", "A");
        //Action
        Booking booking = bookingManager.doBooking(bookingSample);
        bookingManager.modifySeatBooking(booking.getId(), "B");

        booking = bookingManager.getBooking(booking.getId());
        //Assert


        assertEquals("B",booking.getSectionId());
    }

    @Test
    public void testcancelBooking_Succeed() throws BookingException, MaxCapacityExceededException, BookingNotFoundException {
        //Arrange
        Booking bookingSample = getSampleBooking(1L, "Paco Rodriguez", "A");
        //Action
        Booking booking = bookingManager.doBooking(bookingSample);

        bookingManager.cancelBooking(booking.getId());
        //Assert


        //Assert
        BookingNotFoundException exception = assertThrows(BookingNotFoundException.class,
                () -> bookingManager.getBooking(booking.getId()));

    }

    @Test
    public void testgetRouteDetails_Succeed() throws BookingException, MaxCapacityExceededException, BookingNotFoundException {
        //Arrange
        Booking bookingSample = getSampleBooking(1L, "Paco Rodriguez", "A");
        //Action
        Booking booking = bookingManager.doBooking(bookingSample);

        RouteDetailDTO routeDetail = bookingManager.getRouteDetail("A");
        //Assert
        assertEquals("A", routeDetail.getSectionId());

        assertEquals(1, routeDetail.getBookingDetailList().size());

    }
}
