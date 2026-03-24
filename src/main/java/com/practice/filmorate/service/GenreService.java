package com.practice.filmorate.service;

import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.model.Genre;
import com.practice.filmorate.storage.GenreStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreStorage genreStorage;

    public Collection<Genre> getAllGenre(){
        return genreStorage.findAllGenre();
    }

    public Genre getGenreById(Integer id){
        return genreStorage.findGenreById(id).orElseThrow(()->new NotFoundException("Genre not found"));
    }

}
