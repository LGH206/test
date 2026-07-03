package com.fe.horseracing.repository.interfaces;

import java.util.List;
import com.fe.horseracing.enums.PredictionStatus;
import com.fe.horseracing.pojo.Prediction;

public interface IPredictionRepository {

    void save(Prediction prediction);

    void update(Prediction prediction);

    void delete(Long predictionId);

    Prediction findById(Long predictionId);

    List<Prediction> findAll();

    List<Prediction> findByUser(Long userId);

    List<Prediction> findByRace(Long raceId);

    List<Prediction> findByHorse(Long horseId);

    List<Prediction> findByStatus(PredictionStatus status);
}
