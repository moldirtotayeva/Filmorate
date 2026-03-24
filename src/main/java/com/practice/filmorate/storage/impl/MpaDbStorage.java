package com.practice.filmorate.storage.impl;

import com.practice.filmorate.model.Mpa;
import com.practice.filmorate.storage.MpaStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public Collection<Mpa> getAllMpa() {
        String sql = "select * from mpa";
        return jdbcTemplate.query(sql, MpaDbStorage::MpaMapRow);
    }

    @Override
    public Optional<Mpa> findMpaById(Integer id) {
        String sql = "select * from mpa where id=?";
        try{
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(sql, MpaDbStorage::MpaMapRow,id));
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    private static Mpa MpaMapRow(ResultSet rs, int rowNum) throws SQLException{
        return new Mpa(rs.getInt("id"),
                rs.getString("name"));
    }
}
