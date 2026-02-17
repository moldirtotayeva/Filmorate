package com.practice.filmorate.storage;

import com.practice.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserStorage {
    Collection<User> findAllUsers();
    User create(User user);
    Optional<User> findById(int id);
    User update(User user);
    void validate(User user);
}
