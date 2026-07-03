package com.fe.horseracing.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fe.horseracing.pojo.Horse;
import com.fe.horseracing.repository.interfaces.HorseRepository;
import com.fe.horseracing.service.interfaces.IHorseService;

@Service
public class HorseServiceImpl implements IHorseService {

    @Autowired
    private HorseRepository horseRepository;

    @Override
    public List<Horse> findAll() {
        return horseRepository.findAll();
    }

    @Override
    public Horse findById(Long id) {
        return horseRepository.findById(id).orElse(null);
    }

    @Override
    public Horse save(Horse horse) {
        return horseRepository.save(horse);
    }

    @Override
    public void delete(Long id) {
        horseRepository.deleteById(id);
    }
}