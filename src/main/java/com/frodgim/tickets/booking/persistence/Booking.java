package com.frodgim.tickets.booking.persistence;




import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@NoArgsConstructor
public class Booking {


    private Long id;

    @NonNull
    private String fullName;

    //Only contemplating from
    @NonNull
    private String from;

    @NonNull
    private String to;

    //I've pre-assigned because the price is not important for this solution
    private float price = 20;


    private Integer seatNumber;

    @NonNull
    private String sectionId;



    @Override
    public String toString() {
        return String.format("Booking [id=%s, fullname=%s, seatNumber=%s, section=%s]", id, fullName,seatNumber, sectionId);
    }
}
