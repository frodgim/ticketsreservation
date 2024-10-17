package com.frodgim.tickets.booking;


import com.frodgim.tickets.booking.persistence.Booking;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BookingManager bookingManager;

    @GetMapping("/{id}")
    public Booking retrieveBooking(@PathVariable long id) {

        LOGGER.debug("Entering :: retrieveBooking methods");
        return new Booking(){
            {
                setId(id);
                setSeatNumber(45);
            }
        };
    }
}
