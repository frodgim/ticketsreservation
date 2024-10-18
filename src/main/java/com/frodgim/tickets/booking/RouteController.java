package com.frodgim.tickets.booking;


import com.frodgim.tickets.booking.dto.RouteDetail;
import com.frodgim.tickets.booking.exceptions.BookingException;
import com.frodgim.tickets.booking.service.BookingManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/route")
public class RouteController {

    @Autowired
    private BookingManager bookingManager;

    @GetMapping("/{sectionId}")
    public ResponseEntity<RouteDetail> retrieveRoute(@PathVariable String sectionId) throws BookingException {

        RouteDetail routeDetail = bookingManager.getRouteDetail(sectionId);

        return ResponseEntity.ok(routeDetail);
    }
}
