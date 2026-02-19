package com.practice.filmorate.storage;

import com.practice.filmorate.exceptions.FilmAlreadyExistException;
import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.exceptions.ValidationException;
import com.practice.filmorate.model.Film;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private Map<Long, Film> films = new HashMap<>();
    private static long uniqueId = 1;

    @Override
    public Collection<Film> findAll() {
        return films.values();
    }

    @Override
    public Film create(Film film) {
        if (films.containsKey(film.getId())) {
            throw new FilmAlreadyExistException("Film with id " + film.getId() + " already exists");
        }
        validate(film);
        film.setId(uniqueId++);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Optional<Film> findById(Long id) {
        return films.values().stream().filter(film -> film.getId() == id).findFirst();
    }

    @Override
    public Film update(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("Film with id " + film.getId() + " does not exist");
        }
        validate(film);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public void addLike(Long id, Long userId) {
        films.get(id).getLikes().add(userId);
    }

    @Override
    public void removeLike(Long id, Long userId) {
        films.get(id).getLikes().remove(userId);
    }

    @Override
    public Collection<Film> findPopularFilms(Integer size) {
        return films.values().stream()
                .sorted(Comparator.comparingInt(f -> ((Film)f).getLikes().size()).reversed())
                .limit(size)
                .toList();
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

}
