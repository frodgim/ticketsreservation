package com.frodgim.tickets.booking.service;

import com.frodgim.tickets.booking.persistence.Booking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrainSection {

    private final String id;

    private final List<Booking> bookingList;

    public TrainSection(String id){
        this.id = id;
        this.bookingList = Collections.synchronizedList(new ArrayList<>());
    }

    public String getId() {
        return id;
    }

    public List<Booking> getBookingList() {
        return bookingList;
    }
}