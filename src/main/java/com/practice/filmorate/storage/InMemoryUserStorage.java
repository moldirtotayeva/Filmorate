package com.practice.filmorate.storage;

import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.exceptions.UserAlreadyExistException;
import com.practice.filmorate.exceptions.ValidationException;
import com.practice.filmorate.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class InMemoryUserStorage implements UserStorage {
    private Map<Long, User> users = new HashMap<>();
    private static long uniqueId = 1;

    @Override
    public Collection<User> findAllUsers() {
        return users.values();
    }

    @Override
    public User create(User user) {
        if (users.containsKey(user.getId())) {
            throw new UserAlreadyExistException("User with id " + user.getId() + " already exists");
        }
        validate(user);
        user.setId(uniqueId++);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(int id) {
        return users.values().stream().filter(user -> user.getId() == id).findFirst();
    }

    @Override
    public User update(User user) {
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException("User with id " + user.getId() + " does not exist");
        }
        validate(user);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void validate(User user) {
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
