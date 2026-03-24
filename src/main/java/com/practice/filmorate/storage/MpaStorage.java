package com.practice.filmorate.storage;

import com.practice.filmorate.model.Mpa;

import java.util.Collection;
import java.util.Optional;

public interface MpaStorage {
    Collection<Mpa> getAllMpa();
    Optional<Mpa> findMpaById(Integer id);

}
