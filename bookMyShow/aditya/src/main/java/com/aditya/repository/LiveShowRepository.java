package com.aditya.repository;

import java.util.HashMap;
import java.util.Map;

import com.aditya.Model.LiveShow;

public class LiveShowRepository {
	private final Map<String, LiveShow> liveShowMap = new HashMap<>();

    public void save(LiveShow show) {
        liveShowMap.put(show.getName(), show);
    }

    public LiveShow findByName(String name) {
        return liveShowMap.get(name);
    }

    public boolean exists(String name) {
        return liveShowMap.containsKey(name);
    }

    public Map<String, LiveShow> findAll() {
        return liveShowMap;
    }
}
