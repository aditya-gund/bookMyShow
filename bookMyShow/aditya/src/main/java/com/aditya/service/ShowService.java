package com.aditya.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.aditya.Model.LiveShow;
import com.aditya.Model.ShowSlot;
import com.aditya.constant.Genre;
import com.aditya.constant.TimeSlot;
import com.aditya.repository.LiveShowRepository;

public class ShowService {
	 private final LiveShowRepository liveShowRepository;

	    public ShowService(LiveShowRepository liveShowRepository) {
	        this.liveShowRepository = liveShowRepository;
	    }

	    public String registerShow(String showName, Genre genre) {
	        if (liveShowRepository.exists(showName)) {
	            return "Show already exists.";
	        }
	        LiveShow show = new LiveShow(showName, genre);
	        liveShowRepository.save(show);
	        return showName + " show is registered !!";
	    }

	    public String onboardShowSlots(String showName, Map<TimeSlot, Integer> slotCapacityMap) {
	        LiveShow show = liveShowRepository.findByName(showName);
	        if (show == null) {
	            return "Show not found.";
	        }

	        for (Map.Entry<TimeSlot, Integer> entry : slotCapacityMap.entrySet()) {
	            TimeSlot slot = entry.getKey();
	            int capacity = entry.getValue();

	            // Validate 1-hour slot (already enforced by TimeSlot class)
	            if (show.getShowSlots().containsKey(slot)) {
	                return "Overlapping slot already declared for " + showName + " at " + slot;
	            }

	            show.addSlot(slot, new ShowSlot(capacity));
	        }

	        return "Done!";
	    }

	    public List<String> getAvailableShowsByGenre(Genre genre) {
	        List<String> result = new ArrayList<>();

	        for (LiveShow show : liveShowRepository.findAll().values()) {
	            if (show.getGenre() != genre) continue;

	            for (Map.Entry<TimeSlot, ShowSlot> entry : show.getShowSlots().entrySet()) {
	                TimeSlot slot = entry.getKey();
	                ShowSlot showSlot = entry.getValue();

	                if (showSlot.getAvailableCapacity() > 0) {
	                    result.add(show.getName() + ": (" + slot + ") " + showSlot.getAvailableCapacity());
	                }
	            }
	        }

	        // Default sorting by start time (already a requirement)
	        result.sort(Comparator.comparing(s -> s.split(": ")[1]));

	        return result;
	    }
}
