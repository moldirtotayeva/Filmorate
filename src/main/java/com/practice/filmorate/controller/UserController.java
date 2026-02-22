package com.practice.filmorate.controller;

import com.practice.filmorate.model.User;
import com.practice.filmorate.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;

@Slf4j
@Getter
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public Collection<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        log.info("Created user with id={}", user.getId());
        return userService.createUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        log.info("Updated user with id={}", user.getId());
        return userService.updateUser(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        log.info("Fetching user with id={}", id);
        return userService.findUserById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("Adding friend with id={}", id);
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("Deleting friend with id={}", id);
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Set<User> getFriends(@PathVariable Long id) {
        log.info("Fetching friends with id={}", id);
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Set<User> getCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        log.info("Fetching common friends with id={}", id);
        return userService.findCommonFriends(id, otherId);
    }

}
