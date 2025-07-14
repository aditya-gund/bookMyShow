package com.aditya.Model;
import java.util.ArrayList;
import java.util.List;

public class User {
	private String name;
    private List<Booking> bookings;

    public User(String name) {
        this.name = name;
        this.bookings = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }
}
