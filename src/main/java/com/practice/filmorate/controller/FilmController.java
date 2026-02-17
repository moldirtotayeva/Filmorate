package com.practice.filmorate.controller;

import com.practice.filmorate.exceptions.FilmAlreadyExistException;
import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.exceptions.ValidationException;
import com.practice.filmorate.model.Film;
import com.practice.filmorate.service.FilmService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @GetMapping
    public Collection<Film> getAllFilms() {
        return filmService.findAll();
    }

    @PostMapping
    public Film create(@RequestBody Film film) {
        //log.info("Created film with id {}", film.getId());
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        //log.info("Updated film with id {}", film.getId());
        return filmService.update(film);
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable int id) {
        return filmService.findById(id);
    }

}
