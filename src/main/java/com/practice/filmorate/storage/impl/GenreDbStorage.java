package com.practice.filmorate.storage.impl;

import com.practice.filmorate.model.Genre;
import com.practice.filmorate.storage.GenreStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public Collection<Genre> findAllGenre() {
        return jdbcTemplate.query("select * from genres", GenreDbStorage::GenreMapRow);
    }

    @Override
    public Optional<Genre> findGenreById(Integer id) {
        String sql = "select * from genres where id=?";
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(sql, GenreDbStorage::GenreMapRow, id));
        }catch (EmptyResultDataAccessException ex){
            return Optional.empty();
        }

    }

    private static Genre GenreMapRow(ResultSet rs, int rowNum) throws SQLException{
        return new Genre(rs.getInt("id"),
                rs.getString("name"));
    }
}
