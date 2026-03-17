package com.practice.filmorate.storage.impl;

import com.practice.filmorate.model.Genre;
import com.practice.filmorate.storage.GenreStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public Collection<Genre> findAllGenre() {
        return jdbcTemplate.query("select * from genres", GenreDbStorage::GenreMapRow);
    }

    @Override
    public Genre create(Genre genre) {
//        String sql = "insert into genres(id, name) values(?, ?)";
//        jdbcTemplate.update(sql, user.getId(), user.getName());
//        return user;
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("genres")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> parametr = Map.of("name", genre.getName());
        Integer id = insert.executeAndReturnKey(parametr).intValue();
        genre.setId(id);
        return genre;
    }

    private static Genre GenreMapRow(ResultSet rs, int rowNum) throws SQLException{
        return new Genre(rs.getInt("id"),
                rs.getString("name"));
    }
}
