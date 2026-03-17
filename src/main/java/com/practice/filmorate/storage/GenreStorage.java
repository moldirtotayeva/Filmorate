package com.practice.filmorate.storage;

import com.practice.filmorate.model.Genre;

import java.util.Collection;

public interface GenreStorage {
    Collection<Genre> findAllGenre();
    Genre create(Genre user);
}
