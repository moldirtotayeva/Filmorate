package com.practice.filmorate.service;

import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.model.User;
import com.practice.filmorate.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public Collection<User> findAllUsers() {
        return userStorage.findAllUsers();
    }

    public User createUser(User user) {
        return userStorage.create(user);
    }

    public User updateUser(User user) {
        return userStorage.update(user);
    }

    public User findUserById(int id) {
        return userStorage.findById(id).orElseThrow(()-> new NotFoundException("User not found"));
    }


}
