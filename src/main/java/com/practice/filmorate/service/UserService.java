package com.practice.filmorate.service;

import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.model.User;
import com.practice.filmorate.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Set;

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

    public User findUserById(Long id) {
        return userStorage.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public void addFriend(Long id, Long friendId) {
        if (userStorage.findById(id).isPresent() && userStorage.findById(friendId).isPresent()) {
            userStorage.addFriend(id, friendId);
        } else {
            throw new NotFoundException("User not found");
        }
    }

    public void deleteFriend(Long id, Long friendId) {
        if (userStorage.findById(id).isPresent() && userStorage.findById(friendId).isPresent()) {
            userStorage.deleteFriend(id, friendId);
        } else {
            throw new NotFoundException("User not found");
        }
    }

    public Set<Long> getFriends(Long id) {
        return userStorage.getFriends(id);
    }

    public Set<Long> findCommonFriends(Long id, Long otherId) {
        return userStorage.findCommonFriends(id, otherId);
    }

}
