package com.agrosoft.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.agrosoft.model.User;

@Repository
public class UserRepository {
    
    private final List<User> users = new ArrayList<>();

    public void save(User user) {
        users.add(user);
    }

    public User findByUsernameAndPassword(String username, String password) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}
