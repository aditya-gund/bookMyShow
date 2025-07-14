package com.aditya.constant;

import java.util.Objects;

public class TimeSlot {
	private final int startHour; // from 9 to 20

    public TimeSlot(int startHour) {
        if (startHour < 9 || startHour >= 21) {
            throw new IllegalArgumentException("Invalid slot. Must be between 9AM and 9PM.");
        }
        this.startHour = startHour;
    }

    public String getSlotTime() {
        return String.format("%02d:00-%02d:00", startHour, startHour + 1);
    }

    public int getStartHour() {
        return startHour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeSlot)) return false;
        TimeSlot that = (TimeSlot) o;
        return startHour == that.startHour;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startHour);
    }

    @Override
    public String toString() {
        return getSlotTime();
    }

}
