package com.frodgim.tickets.booking.service;

import com.frodgim.tickets.booking.dto.RouteDetail;
import com.frodgim.tickets.booking.exceptions.BookingException;
import com.frodgim.tickets.booking.exceptions.BookingNotFound;
import com.frodgim.tickets.booking.exceptions.MaxCapacityExceededException;
import com.frodgim.tickets.booking.persistence.Booking;
import com.frodgim.tickets.booking.persistence.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class BookingManagerNoScale implements BookingManager {

    private int maxCapacity = 10;

    private Map<String, TrainSection> currentCapacity;


    public BookingManagerNoScale(){
        initializeCapacity();
    }


    private void initializeCapacity(){
        this.currentCapacity = new ConcurrentHashMap<>();
        this.currentCapacity.put("A", new TrainSection("A"));
        this.currentCapacity.put("B", new TrainSection("B"));
    }


    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public Booking getBooking(Long id) throws BookingNotFound {
        Optional<Booking> booking =  bookingRepository.findById(id);
        if(booking.isEmpty()){
            throw new BookingNotFound(id);
        }

        return booking.get();
    }

    @Override
    public Booking doBooking(Booking booking) throws BookingException, MaxCapacityExceededException{
        checkIfValidSection(booking.getSectionId());
        checkCapacityInSection(booking.getSectionId());
        TrainSection section = getSection(booking.getSectionId());
        addToSection(booking, section);
        return booking;
    }

    @Override
    public Booking modifySeatBooking(Long id, String newSectionId) throws BookingException, MaxCapacityExceededException, BookingNotFound {
        checkIfValidSection(newSectionId);

        Booking booking = getBooking(id);
        if(booking.getSectionId().equals(newSectionId)){
            throw new BookingException("You have already booked in the same section");
        }

        checkCapacityInSection(newSectionId);

        TrainSection currentSection = getSection(booking.getSectionId());
        removeFromSection(booking, currentSection);


        booking.setSectionId(newSectionId);
        TrainSection newSection = getSection(newSectionId);
        addToSection(booking, newSection);

        return booking;
    }

    @Override
    public void cancelBooking(Long id) throws BookingNotFound {
        Booking booking = getBooking(id);

        TrainSection currentSection = getSection(booking.getSectionId());
        removeFromSection(booking, currentSection);
        bookingRepository.delete(booking);

    }

    @Override
    public RouteDetail getRouteDetail(){
        throw new UnsupportedOperationException("Not implemented yet!");
    }


    @Override
    public int getMaxCapacity() {
        return maxCapacity;
    }

    @Override
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    private void checkIfValidSection(String sectionId) throws BookingException{
        if(!sectionId.equals("A") && !sectionId.equals("B") ){
            throw new BookingException("The only sections available are either A or B, received:" + sectionId);
        }
    }


    private void checkCapacityInSection(String sectionId) throws  MaxCapacityExceededException {
        TrainSection section = getSection(sectionId);
        if(section.getBookingList().size() >= maxCapacity){
            throw  new MaxCapacityExceededException("The maximum capacity has been exceeded on section:" + sectionId);
        }
    }

    private TrainSection getSection(String sectionId) {
        return currentCapacity.get(sectionId);
    }

    private synchronized void addToSection(Booking booking, TrainSection section) {
        int seatNumber = assignSeat(section);
        booking.setSeatNumber(seatNumber);
        booking = bookingRepository.save(booking);
        section.getBookingList().add(booking);
    }

    private synchronized void removeFromSection(Booking booking, TrainSection section) {
        section.getBookingList().removeIf(x -> x.getId().equals(booking.getId()));
    }

    private synchronized int assignSeat(TrainSection section) {
        int candidateSeat = 1;

        section.getBookingList().sort(Comparator.comparingInt(Booking::getSeatNumber));
        for(Booking booking : section.getBookingList()){
           if(candidateSeat < booking.getSeatNumber()){
               break;
           }
           else{
               candidateSeat++;
           }
        }

        return candidateSeat;

        //return section.getBookingList().stream().mapToInt(Booking::getSeatNumber).max().orElse(0) + 1;
    }

}


