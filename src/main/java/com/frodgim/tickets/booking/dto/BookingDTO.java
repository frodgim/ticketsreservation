package com.frodgim.tickets.booking.dto;


import com.frodgim.tickets.booking.persistence.Booking;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class BookingDTO {
    private Long id;

    @NotEmpty(message = "fullName cannot be blank")
    private String fullName;

    @NotEmpty(message = "from cannot be blank")
    private String from;

    @NotEmpty(message = "to cannot be blank")
    private String to;

    //I've pre-assigned because the price is not important for this solution
    private float price = 20;


    private Integer seatNumber;

    @NotEmpty(message = "sectionId cannot be blank")
    private String sectionId;


    public Booking convertToEntity(){
        Booking booking = new Booking();
        booking.setId(this.id);
        booking.setFullName(this.fullName);
        booking.setFrom(this.from);
        booking.setTo(this.to);
        booking.setPrice(this.price);
        booking.setSectionId(this.sectionId);
        booking.setSeatNumber(this.seatNumber);
        return booking;
    }

    public void updateFromEntity(Booking booking){
        setId(booking.getId());
        setFullName(booking.getFullName());
        setFrom(booking.getFrom());
        setTo(booking.getTo());
        setPrice(booking.getPrice());
        setSectionId(booking.getSectionId());
    }
}
