package com.practice.filmorate.controller;

import com.practice.filmorate.exceptions.ValidationException;
import com.practice.filmorate.model.Film;
import com.practice.filmorate.service.FilmService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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
        log.info("Created film with id {}", film.getId());
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        log.info("Updated film with id {}", film.getId());
        return filmService.update(film);
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Long id) {
        log.info("Fetching film with id {}", id);
        return filmService.findById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Adding film with id {}", id);
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Deleting film with id {}", id);
        filmService.removeLike(id, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularFilms(
            @RequestParam(value = "count", defaultValue = "10", required = false) Integer size) {
        if (size <= 0) {
            throw new ValidationException("size must be greater than 0");
        }
        log.info("Fetching popular films");
        return filmService.findPopularFilms(size);
    }

}
