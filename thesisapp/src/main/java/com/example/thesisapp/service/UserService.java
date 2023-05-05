package com.example.thesisapp.service;

import org.springframework.stereotype.Service;

import com.example.thesisapp.model.User;

@Service
public interface UserService {
	public void saveUser(User user);
    public boolean isUserPresent(User user);
    public User getUserByUsername(String username);
}
