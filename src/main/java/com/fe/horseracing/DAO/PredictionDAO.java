package com.fe.horseracing.DAO;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.fe.horseracing.enums.PredictionStatus;
import com.fe.horseracing.pojo.Prediction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
@Transactional
public class PredictionDAO {
    @PersistenceContext 
    private EntityManager em;
    
    // CREATE
    public void save(Prediction prediction) {
        em.persist(prediction);
    }

    // UPDATE
    public void update(Prediction prediction) {
        em.merge(prediction);
    }

    // DELETE
    public void delete(Long predictionId) {
        Prediction prediction = em.find(
                        Prediction.class,
                        predictionId);
        if (prediction != null) {
            em.remove(prediction);
        }
    }

    // FIND BY ID
    public Prediction findById(Long predictionId) {

        return em.find(
                Prediction.class,
                predictionId);
    }

    // FIND ALL
    public List<Prediction> findAll() {

        TypedQuery<Prediction> query =
                em.createQuery(
                        "SELECT p FROM Prediction p",
                        Prediction.class);

        return query.getResultList();
    }

    // FIND BY USER
    public List<Prediction> findByUser(Long userId) {
        TypedQuery<Prediction> query =
                em.createQuery(
                        "SELECT p FROM Prediction p " +
                        "WHERE p.spectator.userId = :userId",
                        Prediction.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    // FIND BY RACE
    public List<Prediction> findByRace(Long raceId) {
        TypedQuery<Prediction> query =
                em.createQuery(
                        "SELECT p FROM Prediction p " +
                        "WHERE p.race.raceId = :raceId",
                        Prediction.class);
        query.setParameter("raceId", raceId);
        return query.getResultList();
    }

    // FIND BY HORSE
    public List<Prediction> findByHorse(Long horseId) {
        TypedQuery<Prediction> query = em.createQuery(
        				"SELECT p FROM Prediction p " +
                        "WHERE p.predictedHorse.horseId = :horseId",
                        Prediction.class);
        query.setParameter("horseId", horseId);
        return query.getResultList();
    }

    // FIND BY STATUS
    public List<Prediction> findByStatus( PredictionStatus status) {
        TypedQuery<Prediction> query =
                em.createQuery(
                        "SELECT p FROM Prediction p " +
                        "WHERE p.status = :status",
                        Prediction.class);
        query.setParameter("status", status);
        return query.getResultList();
    }
}
