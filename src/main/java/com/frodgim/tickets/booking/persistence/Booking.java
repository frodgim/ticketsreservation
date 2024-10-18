package com.frodgim.tickets.booking.persistence;


import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Booking {
    private Long id;

    private String fullName;

    private String from;

    private String to;

    //I've pre-assigned because the price is not important for this solution
    private float price = 20;

    private Integer seatNumber;

    private String sectionId;



    @Override
    public String toString() {
        return String.format("Booking [id=%s, fullname=%s, seatNumber=%s, section=%s]", id, fullName,seatNumber, sectionId);
    }
}
