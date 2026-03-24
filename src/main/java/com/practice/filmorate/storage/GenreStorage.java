package com.practice.filmorate.storage;

import com.practice.filmorate.model.Genre;

import java.util.Collection;
import java.util.Optional;

public interface GenreStorage {
    Collection<Genre> findAllGenre();
    Optional<Genre> findGenreById(Integer id);

}
