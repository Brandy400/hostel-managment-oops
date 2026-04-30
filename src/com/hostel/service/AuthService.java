package com.hostel.service;

import com.hostel.model.Warden;

public class AuthService {
    private final Warden defaultWarden = new Warden("warden", "hostel123", "Chief Warden");

    public Warden login(String username, String password) {
        if (defaultWarden.getUsername().equals(username) && defaultWarden.getPassword().equals(password)) {
            return defaultWarden;
        }
        return null;
    }
}
