package com.frodgim.tickets.booking.dto;

import java.util.ArrayList;
import java.util.List;

public class RouteDetail {
    private String sectionId;

    private List<BookingDetail> bookingDetailList;

    public RouteDetail(String sectionId){
        this.sectionId = sectionId;
        bookingDetailList = new ArrayList<BookingDetail>();
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public List<BookingDetail> getBookingDetailList() {
        return bookingDetailList;
    }

    public void setBookingDetailList(List<BookingDetail> bookingDetailList) {
        this.bookingDetailList = bookingDetailList;
    }
}
