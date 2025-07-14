package com.aditya.service;

import com.aditya.Model.Booking;
import com.aditya.Model.ShowSlot;
import com.aditya.constant.BookingStatus;
import com.aditya.pojo.TimeSlot;
import com.aditya.repository.BookingRepository;
import com.aditya.repository.UserRepository;

public class WaitlistService {
	private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public WaitlistService(UserRepository userRepository,
                           BookingRepository bookingRepository) {
        this.userRepository   = userRepository;
        this.bookingRepository = bookingRepository;
    }

    /**
     * Try to move the head of the waitlist into a confirmed booking
     * when capacity becomes available after a cancellation.
     */
    public void tryPromoteUser(ShowSlot showSlot,
                               String      showName,
                               TimeSlot    slot) {

        if (showSlot.getWaitlist().isEmpty()) {
            return;                         // nothing to promote
        }

        Booking candidate = showSlot.getWaitlist().peek(); // FIFO

        // Can we accommodate the entire ticket?
        if (showSlot.getAvailableCapacity() < candidate.getNumPersons()) {
            return;                         // still not enough seats
        }

        // Promote!
        showSlot.getWaitlist().poll();                  // remove from queue
        candidate.setStatus(BookingStatus.BOOKED);      // update status
        showSlot.getConfirmedBookings().add(candidate); // add to confirmed list
        showSlot.reduceCapacity(candidate.getNumPersons());

    }
}
