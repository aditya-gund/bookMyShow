package com.aditya.repository;

import java.util.HashMap;
import java.util.Map;

import com.aditya.Model.User;

public class UserRepository {
	private final Map<String, User> userMap = new HashMap<>();

    public void save(User user) {
        userMap.put(user.getName(), user);
    }

    public User findByName(String name) {
        return userMap.get(name);
    }

    public boolean exists(String name) {
        return userMap.containsKey(name);
    }
}
