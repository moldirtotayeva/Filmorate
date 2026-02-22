package com.practice.filmorate.storage;

import com.practice.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface UserStorage {
    Collection<User> findAllUsers();
    User create(User user);
    Optional<User> findById(Long id);
    User update(User user);
    void addFriend(Long id, Long friendId);
    void deleteFriend(Long id, Long friendId);
    Set<User> getFriends(Long id);
    Set<User> findCommonFriends(Long id, Long friendId);
    void validate(User user);
}
