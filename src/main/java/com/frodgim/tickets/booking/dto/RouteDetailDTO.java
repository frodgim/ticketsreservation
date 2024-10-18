package com.frodgim.tickets.booking.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class RouteDetailDTO {
    private String sectionId;

    private List<BookingDetailDTO> bookingDetailList;

    public RouteDetailDTO(String sectionId){
        this.sectionId = sectionId;
        bookingDetailList = new ArrayList<BookingDetailDTO>();
    }

}
