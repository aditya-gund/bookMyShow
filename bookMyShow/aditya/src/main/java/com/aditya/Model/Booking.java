package com.aditya.Model;

import com.aditya.constant.BookingStatus;
import com.aditya.constant.TimeSlot;

public class Booking {
	private int bookingId;
    private String userName;
    private String showName;
    private TimeSlot slot;
    private int numPersons;
    private BookingStatus status;

    public Booking(int bookingId, String userName, String showName, TimeSlot slot, int numPersons, BookingStatus status) {
        this.bookingId = bookingId;
        this.userName = userName;
        this.showName = showName;
        this.slot = slot;
        this.numPersons = numPersons;
        this.status = status;
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getUserName() {
        return userName;
    }

    public String getShowName() {
        return showName;
    }

    public TimeSlot getSlot() {
        return slot;
    }

    public int getNumPersons() {
        return numPersons;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}
