package com.frodgim.tickets.booking;


import com.frodgim.tickets.booking.dto.RouteDetailDTO;
import com.frodgim.tickets.booking.exceptions.BookingException;
import com.frodgim.tickets.booking.service.BookingManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/route")
public class RouteController {

    @Autowired
    private BookingManager bookingManager;

    @GetMapping("/{sectionId}")
    public ResponseEntity<RouteDetailDTO> retrieveRoute(@PathVariable String sectionId) throws BookingException {

        RouteDetailDTO routeDetail = bookingManager.getRouteDetail(sectionId);

        return ResponseEntity.ok(routeDetail);
    }
}
