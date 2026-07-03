package com.fe.horseracing.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.fe.horseracing.DAO.PredictionDAO;
import com.fe.horseracing.enums.PredictionStatus;
import com.fe.horseracing.pojo.Prediction;
import com.fe.horseracing.repository.interfaces.IPredictionRepository;

@Repository
public class PredictionRepository implements IPredictionRepository {

    @Autowired
    private PredictionDAO predictionDAO;

    @Override
    public void save(Prediction prediction) {
        predictionDAO.save(prediction);
    }

    @Override
    public void update(Prediction prediction) {
        predictionDAO.update(prediction);
    }

    @Override
    public void delete(Long predictionId) {
        predictionDAO.delete(predictionId);
    }

    @Override
    public Prediction findById(Long predictionId) {
        return predictionDAO.findById(predictionId);
    }

    @Override
    public List<Prediction> findAll() {
        return predictionDAO.findAll();
    }

    @Override
    public List<Prediction> findByUser(Long userId) {
        return predictionDAO.findByUser(userId);
    }

    @Override
    public List<Prediction> findByRace(Long raceId) {
        return predictionDAO.findByRace(raceId);
    }

    @Override
    public List<Prediction> findByHorse(Long horseId) {
        return predictionDAO.findByHorse(horseId);
    }

    @Override
    public List<Prediction> findByStatus(PredictionStatus status) {
        return predictionDAO.findByStatus(status);
    }
}