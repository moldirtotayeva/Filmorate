package com.practice.filmorate.storage;

import com.practice.filmorate.model.Film;
import java.util.Collection;
import java.util.Optional;

public interface FilmStorage {
    Collection<Film> findAll();
    Film create(Film film);
    Optional<Film> findById(int id);
    Film update(Film film);
    void validate(Film film);
}
