package com.practice.filmorate.controller;

import com.practice.filmorate.exceptions.FilmAlreadyExistException;
import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.exceptions.ValidationException;
import com.practice.filmorate.model.Film;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Getter
@RestController
@RequestMapping("/films")
public class FilmController {
    private Map<Long, Film> films = new HashMap<>();
    private static long uniqueId = 1;

    public Long getUniqueId() {
        return uniqueId++;
    }

    @GetMapping
    public Collection<Film> findAll() {
        log.debug("Found {} films", films.size());
        return films.values();
    }

    @PostMapping
    public Film create(@RequestBody Film film) {
        if (films.containsKey(film.getId())) {
            throw new FilmAlreadyExistException("Film with id " + film.getId() + " already exists");
        }
        validate(film);
        film.setId(getUniqueId());
        films.put(film.getId(), film);
        log.debug("Created film with id {}", film.getId());
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("Film with id " + film.getId() + " does not exist");
        }
        validate(film);
        films.put(film.getId(), film);
        log.debug("Updated film with id {}", film.getId());
        return film;
    }

    private void validate(Film film){
        if (film.getName()==null || film.getName().isBlank()) {
            throw new ValidationException("Название фильма не может быть пустым");
        }
        if (film.getDescription()!=null && film.getDescription().length()>200){
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate()!=null && film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))){
            throw new ValidationException("Дата релиза фильма не должна быть раньше 28 декабря 1895 года");
        }
        if (film.getDuration()!=null && film.getDuration()<0){
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
    }
}
