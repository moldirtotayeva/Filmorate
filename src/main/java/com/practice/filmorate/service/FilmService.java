package com.practice.filmorate.service;

import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.model.Film;
import com.practice.filmorate.storage.FilmStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;

    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public Film findById(int id) {
        return filmStorage.findById(id).orElseThrow(()->new NotFoundException("Film not found"));
    }

}
