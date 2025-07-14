package com.aditya.driver;

import com.aditya.constant.BookingStatus;
import com.aditya.constant.Genre;
import com.aditya.pojo.TimeSlot;
import com.aditya.Model.Booking;
import com.aditya.constant.*;
import com.aditya.repository.BookingRepository;
import com.aditya.repository.LiveShowRepository;
import com.aditya.repository.UserRepository;
import com.aditya.service.BookingService;
import com.aditya.service.ShowService;
import com.aditya.service.WaitlistService;

import java.util.*;

public class Driver {

    public static void main(String[] args) {
        System.out.println("Live Show Booking System Started...\n");

        // Setup Repositories
        LiveShowRepository showRepo = new LiveShowRepository();
        UserRepository userRepo = new UserRepository();
        BookingRepository bookingRepo = new BookingRepository();

        // Setup Services
        WaitlistService waitlistService = new WaitlistService(userRepo, bookingRepo);
        ShowService showService = new ShowService(showRepo);
        BookingService bookingService = new BookingService(showRepo, userRepo, bookingRepo, waitlistService);

        // 1. Register Shows
        System.out.println(showService.registerShow("TMKOC", Genre.COMEDY));
        System.out.println(showService.registerShow("The Sonu Nigam Live Event", Genre.SINGING));
        System.out.println(showService.registerShow("The Arijit Singh Live Event", Genre.SINGING));

        // 2. Onboard Show Slots
        Map<TimeSlot, Integer> tmkocSlots = new HashMap<>();
        tmkocSlots.put(new TimeSlot(9), 3);
        tmkocSlots.put(new TimeSlot(12), 2);
        tmkocSlots.put(new TimeSlot(15), 5);
        System.out.println(showService.onboardShowSlots("TMKOC", tmkocSlots));

        Map<TimeSlot, Integer> sonuSlots = new HashMap<>();
        sonuSlots.put(new TimeSlot(10), 3);
        sonuSlots.put(new TimeSlot(13), 2);
        sonuSlots.put(new TimeSlot(17), 1);
        System.out.println(showService.onboardShowSlots("The Sonu Nigam Live Event", sonuSlots));

        Map<TimeSlot, Integer> arijitSlots = new HashMap<>();
        arijitSlots.put(new TimeSlot(11), 3);
        arijitSlots.put(new TimeSlot(14), 2);
        System.out.println(showService.onboardShowSlots("The Arijit Singh Live Event", arijitSlots));

        // 3. Show Availability by Genre
        printAvailableShows("Comedy", showService.getAvailableShowsByGenre(Genre.COMEDY));

        // 4. Book a Ticket
        String bookingResp1 = bookingService.bookTicket("UserA", "TMKOC", new TimeSlot(12), 2);
        System.out.println(bookingResp1);

        // 5. Show Availability after Booking
        printAvailableShows("Comedy", showService.getAvailableShowsByGenre(Genre.COMEDY));

        // 6. Cancel a Booking
        int bookingId1 = extractBookingId(bookingResp1);
        System.out.println(bookingService.cancelBooking(bookingId1));

        // 7. Show Availability after Cancel
        printAvailableShows("Comedy", showService.getAvailableShowsByGenre(Genre.COMEDY));

        // 8. Book another ticket (after cancel)
        String bookingResp2 = bookingService.bookTicket("UserB", "TMKOC", new TimeSlot(12), 1);
        System.out.println(bookingResp2);
     // 9. Waitlist scenario
        String waitlistResp = bookingService.bookTicket("UserC", "TMKOC", new TimeSlot(12), 3);
        System.out.println(waitlistResp); // Should be waitlisted

        // 10. Cancel booking to trigger waitlist promotion
        int bookingId2 = extractBookingId(bookingResp2);
        System.out.println(bookingService.cancelBooking(bookingId2));

        // 11. Print final booking status of all users
        System.out.println("\nBooking Records:");
        for (Booking b : bookingRepo.findAll().values()) {
            System.out.println("BookingId: " + b.getBookingId() + ", User: " + b.getUserName()
                    + ", Show: " + b.getShowName() + ", Slot: " + b.getSlot()
                    + ", Persons: " + b.getNumPersons() + ", Status: " + b.getStatus());
        }

        System.out.println("\nSucessfully completed.");
    }

    private static void printAvailableShows(String genreName, List<String> available) {
        System.out.println("\nAvailable Shows for Genre: " + genreName);
        for (String s : available) {
            System.out.println(s);
        }
    }

    private static int extractBookingId(String message) {
        try {
            return Integer.parseInt(message.replaceAll("\\D+", ""));
        } catch (Exception e) {
            return -1;
        }
    }
}
