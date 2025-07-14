package com.aditya.repository;

import java.util.HashMap;
import java.util.Map;

import com.aditya.Model.Booking;

public class BookingRepository {
	private final Map<Integer, Booking> bookingMap = new HashMap<>();

    public void save(Booking booking) {
        bookingMap.put(booking.getBookingId(), booking);
    }

    public Booking findById(int id) {
        return bookingMap.get(id);
    }

    public boolean exists(int id) {
        return bookingMap.containsKey(id);
    }

    public Map<Integer, Booking> findAll() {
        return bookingMap;
    }

    public void remove(int id) {
        bookingMap.remove(id);
    }
}
