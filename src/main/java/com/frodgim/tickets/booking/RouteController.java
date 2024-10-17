package com.frodgim.tickets.booking;


import com.frodgim.tickets.booking.dto.RouteDetail;
import com.frodgim.tickets.booking.persistence.Booking;
import com.frodgim.tickets.booking.service.BookingManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/route")
public class RouteController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BookingManager bookingManager;

    @GetMapping("/{id}")
    public ResponseEntity<RouteDetail> retrieveRoute() {

        RouteDetail routeDetail = bookingManager.getRouteDetail();

        return ResponseEntity.ok(routeDetail);
    }
}
