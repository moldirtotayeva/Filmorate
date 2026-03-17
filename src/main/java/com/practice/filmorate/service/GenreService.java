package com.practice.filmorate.service;

import com.practice.filmorate.model.Genre;
import com.practice.filmorate.storage.GenreStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreStorage genreStorage;

    public Collection<Genre> findAllGenre(){
        return genreStorage.findAllGenre();
    }

    public Genre create(Genre user) {
        return genreStorage.create(user);
    }
}
