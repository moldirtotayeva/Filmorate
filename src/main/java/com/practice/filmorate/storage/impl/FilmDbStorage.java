package com.practice.filmorate.storage.impl;

import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.exceptions.ValidationException;
import com.practice.filmorate.model.Film;
import com.practice.filmorate.model.Genre;
import com.practice.filmorate.model.Mpa;
import com.practice.filmorate.storage.FilmStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Primary
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final UserDbStorage userDbStorage;

    @Override
    public Collection<Film> findAll() {
        String sql = "select " +
                "f.id as film_id, f.name as film_name, f.description, f.release_date, f.duration, " +
                "m.id as mpa_id, m.name as mpa_name " +
                "from films f left join mpa m on f.mpa_id = m.id";
        return jdbcTemplate.query(sql,FilmDbStorage::makeFilm);
    }

    @Override
    public Film create(Film film) {
        validate(film);
        if (film.getMpa()!=null){
            if (!mpaExists(film.getMpa().getId())){
                throw new NotFoundException("MPA with id=\" + film.getMpa().getId() + \" not found");
            }
        }
        if (film.getGenres()!=null){
            for (Genre g : film.getGenres()){
                if (!genreExist(g.getId())){
                    throw new NotFoundException("Genre with id=" + g.getId() + " not found");
                }
            }
        }
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> params = Map.of(
                "name", film.getName(),
                "description", film.getDescription(),
                "release_date", film.getReleaseDate(),
                "duration", film.getDuration(),
                "mpa_id", film.getMpa() != null ? film.getMpa().getId() : null
        );
        Long id = insert.executeAndReturnKey(params).longValue();
        film.setId(id);
        if (film.getGenres() != null) {
            List<Genre> uniqueGenres = film.getGenres().stream()
                    .collect(Collectors.collectingAndThen(
                            Collectors.toMap(
                                    Genre::getId,
                                    g -> g,
                                    (g1, g2) -> g1
                            ),
                            m -> new ArrayList<>(m.values())
                    ));

            for (Genre g : uniqueGenres) {
                jdbcTemplate.update(
                        "insert into film_genres(film_id, genre_id) values(?, ?)",
                        id, g.getId()
                );
            }
        }
        return findById(film.getId()).orElseThrow();
    }

    @Override
    public Optional<Film> findById(Long id) {
        String sql = "select " +
                "f.id as film_id, f.name as film_name, f.description, f.release_date, f.duration, " +
                "m.id as mpa_id, m.name as mpa_name " +
                "from films f left join mpa m on f.mpa_id = m.id where f.id=?";

        try {
            Film film = jdbcTemplate.queryForObject(sql, FilmDbStorage::makeFilm, id);
            List<Genre> genres = getGenresByFilmId(id);
            film.setGenres(genres);
            return Optional.of(film);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Film update(Film film) {
        if (findById(film.getId()).isEmpty()) {
            throw new NotFoundException("Film not found");
        }

        if (film.getMpa() != null) {
            if (!mpaExists(film.getMpa().getId())) {
                throw new NotFoundException("MPA with id=" + film.getMpa().getId() + " not found");
            }
        }

        String sql = "update films set name=?, description=?, release_date=?, duration=?, mpa_id=? where id=?";

        jdbcTemplate.update(sql,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa() != null ? film.getMpa().getId() : null,
                film.getId());

        jdbcTemplate.update("delete from film_genres where film_id = ?", film.getId());

        if (film.getGenres() != null) {
            Set<Integer> uniqueGenreIds = film.getGenres().stream()
                    .map(Genre::getId)
                    .collect(Collectors.toSet());

            for (Integer genreId : uniqueGenreIds) {
                jdbcTemplate.update("insert into film_genres(film_id, genre_id) values(?, ?)",
                        film.getId(), genreId);
            }
        }
        return findById(film.getId()).orElseThrow();
    }

    @Override
    public void addLike(Long id, Long userId) {
        if (userDbStorage.findById(userId).isEmpty()){
            throw new NotFoundException("User not found");
        }
        if (findById(id).isEmpty()){
            throw new NotFoundException("Film not found");
        }
        String sql = "insert into likes(user_id, film_id) values (?, ?)";
        jdbcTemplate.update(sql, userId, id);
    }

    @Override
    public void removeLike(Long id, Long userId) {
        String sql = "delete from likes where user_id=? and film_id=?";
        int rows = jdbcTemplate.update(sql, userId, id);
        if (rows == 0) {
            if (findById(id).isEmpty() || userDbStorage.findById(userId).isEmpty()) {
                throw new NotFoundException("User not found");
            }
        }
    }

    @Override
    public Collection<Film> findPopularFilms(Integer size) {
        String sql = "select " +
                "f.id as film_id, f.name as film_name, f.description, f.release_date, f.duration, " +
                "m.id as mpa_id, m.name as mpa_name, " +
                "count(l.user_id) as likes_count " +
                "from films f " +
                "left join mpa m on f.mpa_id = m.id " +
                "left join likes l on f.id = l.film_id " +
                "group by f.id, m.id " +
                "order by likes_count desc " +
                "limit ?";
        return jdbcTemplate.query(sql, FilmDbStorage::makeFilm, size);
    }

    @Override
    public void validate(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            throw new ValidationException("Название фильма не может быть пустым");
        }
        if (film.getDescription() != null && film.getDescription().length() > 200) {
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate() != null && film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата релиза фильма не должна быть раньше 28 декабря 1895 года");
        }
        if (film.getDuration() != null && film.getDuration() < 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
    }

    private boolean mpaExists(int id) {
        String sql = "select count(*) from mpa where id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    private boolean genreExist(int id){
        String sql = "select count(*) from genres where id=?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return  count!=null && count>0;
    }

    private static Film makeFilm(ResultSet rs, int rowNum) throws SQLException{
        Mpa mpa = new Mpa(
                rs.getInt("mpa_id"),
                rs.getString("mpa_name"));

        Film film = new Film(
                rs.getLong("film_id"),
                rs.getString("film_name"),
                rs.getString("description"),
                rs.getDate("release_date").toLocalDate(),
                rs.getInt("duration"));
        film.setMpa(mpa);
        return film;
    }

    private List<Genre> getGenresByFilmId(Long filmId) {
        String sql = "select g.id, g.name " +
                "from genres g " +
                "join film_genres fg on g.id = fg.genre_id " +
                "where fg.film_id = ? " +
                "order by g.id";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Genre(rs.getInt("id"), rs.getString("name")), filmId
        );
    }
}
