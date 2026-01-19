package com.practice.filmorate.controller;

import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.exceptions.UserAlreadyExistException;
import com.practice.filmorate.exceptions.ValidationException;
import com.practice.filmorate.model.User;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
@RestController
@RequestMapping("/users")
public class UserController {
    private Map<Long, User> users = new HashMap<>();
    private static long uniqueId = 1;

    public static Long getUniqueId() {
        return uniqueId++;
    }

    @GetMapping
    public Collection<User> findAll() {
        log.info("Found {} users", users.size());
        return users.values();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        if (users.containsKey(user.getId())) {
            throw new UserAlreadyExistException("User with id " + user.getId() + " already exists");
        }
        validate(user);
        user.setId(getUniqueId());
        users.put(user.getId(), user);
        log.info("Created user with id={}", user.getId());
        return user;
    }


    @PutMapping
    public  User updateUser(@RequestBody User user) {
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException("User with id " + user.getId() + " does not exist");
        }
        validate(user);
        users.put(user.getId(), user);
        log.info("Updated user with id={}", user.getId());
        return user;
    }

    private void validate(User user) {
        if (user.getEmail()==null || user.getEmail().isBlank() || !(user.getEmail().contains("@"))){
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }
        if (user.getLogin()==null || user.getLogin().isBlank()){
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }
        if (user.getName()==null || user.getName().isBlank()){
            user.setName(user.getLogin());
        }
        if(user.getBirthday()!=null && user.getBirthday().isAfter(LocalDate.now())){
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
    }
}
