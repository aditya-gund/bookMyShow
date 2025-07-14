package com.aditya.Model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ShowSlot {
	private int totalCapacity;
    private int availableCapacity;
    private List<Booking> confirmedBookings;
    private Queue<Booking> waitlist;

    public ShowSlot(int totalCapacity) {
        this.totalCapacity = totalCapacity;
        this.availableCapacity = totalCapacity;
        this.confirmedBookings = new ArrayList<>();
        this.waitlist = new LinkedList<>();
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public int getAvailableCapacity() {
        return availableCapacity;
    }

    public List<Booking> getConfirmedBookings() {
        return confirmedBookings;
    }

    public Queue<Booking> getWaitlist() {
        return waitlist;
    }

    public void reduceCapacity(int persons) {
        this.availableCapacity -= persons;
    }

    public void increaseCapacity(int persons) {
        this.availableCapacity += persons;
    }
}
