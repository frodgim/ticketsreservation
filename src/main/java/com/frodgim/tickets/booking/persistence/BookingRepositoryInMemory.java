package com.frodgim.tickets.booking.persistence;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BookingRepositoryInMemory implements  BookingRepository{

    private final List<Booking> bookingList = new ArrayList<>();
    private long identity = 0;
    @Override
    public Optional<Booking> findById(Long id) {
        return bookingList.stream()
                .filter(b -> b.getId().equals(id))
                .findAny();
    }

    @Override
    public Booking save(Booking booking) {
        if(booking.getId() == null){booking.setId(identity++);}
        bookingList.add(booking);
        return  booking;
    }

    @Override
    public void delete(Booking booking) {
        bookingList.removeIf(b -> b.getId().equals(booking.getId()));
    }
}
