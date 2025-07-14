package com.aditya.service;

import com.aditya.Model.Booking;
import com.aditya.Model.LiveShow;
import com.aditya.Model.ShowSlot;
import com.aditya.Model.User;
import com.aditya.Utils.BookingIdGenerator;
import com.aditya.constant.BookingStatus;
import com.aditya.pojo.TimeSlot;
import com.aditya.repository.BookingRepository;
import com.aditya.repository.LiveShowRepository;
import com.aditya.repository.UserRepository;

public class BookingService {
	private final LiveShowRepository liveShowRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final WaitlistService waitlistService;

    public BookingService(LiveShowRepository liveShowRepository,
                          UserRepository userRepository,
                          BookingRepository bookingRepository,
                          WaitlistService waitlistService) {
        this.liveShowRepository = liveShowRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.waitlistService = waitlistService;
    }

    public String bookTicket(String userName, String showName, TimeSlot slot, int numPersons) {
        // Validate show
        LiveShow show = liveShowRepository.findByName(showName);
        if (show == null) {
            return "Show not found.";
        }

        // Validate slot
        ShowSlot showSlot = show.getShowSlots().get(slot);
        if (showSlot == null) {
            return "Slot " + slot + " not found for show: " + showName;
        }

        // Check if user already exists or create
        User user = userRepository.findByName(userName);
        if (user == null) {
            user = new User(userName);
            userRepository.save(user);
        }

        // Check if user has any other booking in same slot
        for (Booking existing : user.getBookings()) {
            if (existing.getSlot().equals(slot)
                    && existing.getStatus() == BookingStatus.BOOKED) {
                return "User already has a booking in this time slot.";
            }
        }

        // Check if enough capacity
        if (showSlot.getAvailableCapacity() >= numPersons) {
            int bookingId = BookingIdGenerator.getNextId();
            Booking booking = new Booking(bookingId, userName, showName, slot, numPersons, BookingStatus.BOOKED);

            showSlot.getConfirmedBookings().add(booking);
            showSlot.reduceCapacity(numPersons);

            user.addBooking(booking);
            bookingRepository.save(booking);

            return "Booked. Booking id: " + bookingId;
        }

        // Not enough capacity → add to waitlist
        int bookingId = BookingIdGenerator.getNextId();
        Booking waitlistBooking = new Booking(bookingId, userName, showName, slot, numPersons, BookingStatus.WAITLISTED);

        showSlot.getWaitlist().add(waitlistBooking);
        user.addBooking(waitlistBooking);
        bookingRepository.save(waitlistBooking);

        return "Slot full. Added to waitlist. Booking id: " + bookingId;
    }

    public String cancelBooking(int bookingId) {
        if (!bookingRepository.exists(bookingId)) {
            return "Booking ID not found.";
        }

        Booking booking = bookingRepository.findById(bookingId);
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            return "Booking already cancelled.";
        }

        booking.setStatus(BookingStatus.CANCELLED);

        LiveShow show = liveShowRepository.findByName(booking.getShowName());
        if (show == null) {
            return "Show not found for booking.";
        }

        ShowSlot showSlot = show.getShowSlots().get(booking.getSlot());
        if (showSlot == null) {
            return "Slot not found for show.";
        }

        // Add capacity back
        showSlot.increaseCapacity(booking.getNumPersons());

        // Remove from confirmed list (optional)
        showSlot.getConfirmedBookings().remove(booking);

        // Try to promote from waitlist
        waitlistService.tryPromoteUser(showSlot, show.getName(), booking.getSlot());

        return "Booking Cancelled";
    }
}
