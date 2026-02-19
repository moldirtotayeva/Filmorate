package com.practice.filmorate.storage;

import com.practice.filmorate.model.Film;
import java.util.Collection;
import java.util.Optional;

public interface FilmStorage {
    Collection<Film> findAll();
    Film create(Film film);
    Optional<Film> findById(Long id);
    Film update(Film film);
    void addLike(Long id, Long userId);
    void removeLike(Long id, Long userId);
    Collection<Film> findPopularFilms(Integer size);
    void validate(Film film);
}
