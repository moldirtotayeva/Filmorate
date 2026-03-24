package com.practice.filmorate.service;

import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.model.Mpa;
import com.practice.filmorate.storage.impl.MpaDbStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class MpaService {
    private final MpaDbStorage mpaDbStorage;

    public Collection<Mpa> getAllMpa(){
        return mpaDbStorage.getAllMpa();
    }

    public Mpa getMpaById(Integer id){
        return mpaDbStorage.findMpaById(id).orElseThrow(() -> new NotFoundException("Mpa not found"));
    }

}
