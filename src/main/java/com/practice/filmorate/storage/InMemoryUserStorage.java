package com.practice.filmorate.storage;

import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.exceptions.UserAlreadyExistException;
import com.practice.filmorate.exceptions.ValidationException;
import com.practice.filmorate.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

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
        //user.setFriends();
        validate(user);
        user.setId(uniqueId++);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
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
    public void addFriend(Long id, Long friendId) {
        users.get(id).getFriends().add(friendId);
        users.get(friendId).getFriends().add(id);
    }

    @Override
    public void deleteFriend(Long id, Long friendId) {
        users.get(id).getFriends().remove(friendId);
        users.get(friendId).getFriends().remove(id);
    }

    @Override
    public Set<Long> getFriends(Long id) {
        //Set<Long> result = new HashSet<>();
        if(users.get(id).getFriends()==null){
            throw new NotFoundException("getFriends()==null");//
        }
        return users.get(id).getFriends();
    }

    @Override
    public Set<Long> findCommonFriends(Long id, Long otherId) {
        Set<Long> commonFriends = new HashSet<>();
        Set<Long> friends = users.get(id).getFriends();
        Set<Long> otherUserFriends = users.get(otherId).getFriends();
        for(Long friendId : otherUserFriends) {
            if(friends.contains(friendId)) {
                commonFriends.add(friendId);
            }
        }
        return commonFriends;
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
