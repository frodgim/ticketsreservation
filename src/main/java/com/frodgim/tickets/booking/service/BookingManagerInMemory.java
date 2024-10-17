package com.frodgim.tickets.booking.service;

import com.frodgim.tickets.booking.dto.BookingDetailDTO;
import com.frodgim.tickets.booking.dto.RouteDetailDTO;
import com.frodgim.tickets.booking.exception.BookingException;
import com.frodgim.tickets.booking.exception.BookingNotFoundException;
import com.frodgim.tickets.booking.exception.MaxCapacityExceededException;
import com.frodgim.tickets.booking.persistence.Booking;
import com.frodgim.tickets.booking.persistence.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class BookingManagerInMemory implements BookingManager {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private int maxCapacity = 10;
    private Map<String, TrainSection> currentCapacity;
    public BookingManagerInMemory(){
        initializeCapacity();
    }

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public Booking getBooking(Long id) throws BookingNotFoundException {
        Optional<Booking> booking =  bookingRepository.findById(id);
        if(booking.isEmpty()){
            LOGGER.error("Booking is not found, id:{}", id);
            throw new BookingNotFoundException(id);
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
    public Booking modifySeatBooking(Long id, String newSectionId) throws BookingException, MaxCapacityExceededException, BookingNotFoundException {
        checkIfValidSection(newSectionId);

        Booking booking = getBooking(id);
        if(booking.getSectionId().equals(newSectionId)){
            LOGGER.error("You have already booked in the same section");
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
    public void cancelBooking(Long id) throws BookingNotFoundException {
        Booking booking = getBooking(id);

        TrainSection currentSection = getSection(booking.getSectionId());
        removeFromSection(booking, currentSection);
        bookingRepository.delete(booking);

    }

    @Override
    public RouteDetailDTO getRouteDetail(String sectionId) throws BookingException {
        checkIfValidSection(sectionId);
        RouteDetailDTO detail = new RouteDetailDTO(sectionId);


        TrainSection section = getSection(sectionId);

        for(Booking booking: section.getBookingList()){

            BookingDetailDTO bookingDetail = new BookingDetailDTO(){
                {
                    setFullName(booking.getFullName());
                    setSeatNumber(booking.getSeatNumber());
                }
            };
            detail.getBookingDetailList().add(bookingDetail);
        }


        return  detail;
    }

    @Override
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    @Override
    public void clear() {
        initializeCapacity();
    }

    private void initializeCapacity(){
        this.currentCapacity = new ConcurrentHashMap<>();
        this.currentCapacity.put("A", new TrainSection("A"));
        this.currentCapacity.put("B", new TrainSection("B"));
    }

    private void checkIfValidSection(String sectionId) throws BookingException{
        if(!"A".equals(sectionId) && !"B".equals(sectionId) ){
            LOGGER.error("The only sections available are either A or B, received:{}", sectionId);
            throw new BookingException("The only sections available are either A or B, received:" + sectionId);
        }
    }

    private void checkCapacityInSection(String sectionId) throws  MaxCapacityExceededException {
        TrainSection section = getSection(sectionId);
        if(section.getBookingList().size() >= maxCapacity){
            LOGGER.error("The maximum capacity has been exceeded on section:{}", sectionId);
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
    }

}


