package com.fe.horseracing.service.interfaces;

import java.util.List;

import com.fe.horseracing.pojo.Horse;

public interface IHorseService {

    List<Horse> findAll();

    Horse findById(Long id);

    Horse save(Horse horse);

    void delete(Long id);
}