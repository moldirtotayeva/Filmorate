package com.practice.filmorate.controller;

import com.practice.filmorate.model.User;
import com.practice.filmorate.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;

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
       //log.info("Created user with id={}", user.getId());
        return userService.createUser(user);
    }

    @PutMapping
    public  User updateUser(@RequestBody User user) {
       return userService.updateUser(user);
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.findUserById(id);
    }

}
