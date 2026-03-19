package com.practice.filmorate.storage.impl;

import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.model.User;
import com.practice.filmorate.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
@Primary
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public Collection<User> findAllUsers() {
        return jdbcTemplate.query("select * from users", UserDbStorage::userMapRow);
    }

    @Override
    public User create(User user) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> params = Map.of(
                "email", user.getEmail(),
                "login", user.getLogin(),
                "name", user.getName(),
                "birthday", user.getBirthday()
        );
        Long id = insert.executeAndReturnKey(params).longValue();
        user.setId(id);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = "select * from users where id=?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, UserDbStorage::userMapRow, id));
    }

    @Override
    public User update(User user) {
        if (!findById(user.getId()).isPresent()) {
            throw new NotFoundException("User not found");
        }
        String sql = "update users set email=?, login=?, name=?, birthday=? where id=?";
        jdbcTemplate.update(sql, user.getEmail(), user.getLogin(), user.getName(),
                user.getBirthday(), user.getId());
        return user;
    }

    @Override
    public void addFriend(Long id, Long friendId) {
        Optional<User> userOpt = findById(id);
        Optional<User> friendOpt = findById(friendId);

        if (userOpt.isEmpty() || friendOpt.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        jdbcTemplate.update("insert into friends(user_id, friend_id) values (?, ?)",id, friendId);
    }

    @Override
    public void deleteFriend(Long id, Long friendId) {
        if (findById(id).isEmpty() || findById(friendId).isEmpty()) {
            throw new NotFoundException("User not found");
        }
        String sql = "delete from friends where user_id=? and friend_id=?";
        int rows = jdbcTemplate.update(sql, id, friendId);
        if (rows == 0) {
            throw new NotFoundException("Friendship not found");
        }
    }

    @Override
    public Set<User> getFriends(Long id) {
        if (findById(id).isEmpty()) {
            throw new NotFoundException("User not found");
        }
        String sql = "SELECT * FROM users WHERE id IN (" +
                "SELECT friend_id FROM friends WHERE user_id = ?)";

        return new HashSet<>(
                jdbcTemplate.query(sql, UserDbStorage::userMapRow, id)
        );
    }

    @Override
    public Set<User> findCommonFriends(Long id, Long friendId) {
        return Set.of();
    }

    @Override
    public void validate(User user) {

    }

    private static User userMapRow(ResultSet rs, int rowNum) throws SQLException{
        return new User(
                rs.getLong("id"),
                rs.getString("email"),
                rs.getString("login"),
                rs.getString("name"),
                rs.getDate("birthday").toLocalDate()
        );
    }
}
