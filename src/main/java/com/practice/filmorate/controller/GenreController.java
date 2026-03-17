package com.practice.filmorate.controller;

import com.practice.filmorate.model.Genre;
import com.practice.filmorate.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    public Collection<Genre> getAllGenres(){
        return genreService.findAllGenre();
    }

    @PostMapping("/genre")
    public Genre createGenre(@RequestBody Genre genre){
       return genreService.create(genre);
    }
}
