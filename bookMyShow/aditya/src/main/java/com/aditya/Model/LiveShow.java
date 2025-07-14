package com.aditya.Model;

import java.util.HashMap;
import java.util.Map;

import com.aditya.constant.Genre;
import com.aditya.pojo.TimeSlot;

public class LiveShow {
	private String name;
    private Genre genre;
    private Map<TimeSlot, ShowSlot> showSlots;
    
    public LiveShow(String name, Genre genre) {
        this.name = name;
        this.genre = genre;
        this.showSlots = new HashMap<>();
    }
    public String getName() {
        return name;
    }

    public Genre getGenre() {
        return genre;
    }

    public Map<TimeSlot, ShowSlot> getShowSlots() {
        return showSlots;
    }

    public void addSlot(TimeSlot slot, ShowSlot showSlot) {
        this.showSlots.put(slot, showSlot);
    }

    @Override
    public String toString() {
        return name + " -> " + genre;
    }
}
