package com.practice.filmorate.service;

import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.model.Film;
import com.practice.filmorate.storage.FilmStorage;
import com.practice.filmorate.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public Film findById(Long id) {
        return filmStorage.findById(id).orElseThrow(() -> new NotFoundException("Film not found"));
    }

    public void addLike(Long id, Long userId) {
        if (filmStorage.findById(id).isPresent() && userStorage.findById(id).isPresent()) {
            filmStorage.addLike(id, userId);
        } else {
            throw new NotFoundException("Film not found");
        }
    }

    public void removeLike(Long id, Long userId) {
        if (filmStorage.findById(id).isPresent() && userStorage.findById(id).isPresent()) {
            filmStorage.removeLike(id, userId); //?
        } else {
            throw new NotFoundException("Film not found");
        }
    }

    public Collection<Film> findPopularFilms(Integer size) {
        return filmStorage.findPopularFilms(size);
    }

}
