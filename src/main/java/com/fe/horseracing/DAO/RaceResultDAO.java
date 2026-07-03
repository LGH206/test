package com.fe.horseracing.DAO;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fe.horseracing.enums.ResultStatus;
import com.fe.horseracing.pojo.RaceResult;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
@Transactional
public class RaceResultDAO {
	
    @PersistenceContext
    private EntityManager em;
    
    // CREATE
    public void save(RaceResult raceResult) {
        em.persist(raceResult);
    }

    // UPDATE
    public void update(RaceResult raceResult) {
        em.merge(raceResult);
    }
    
    // DELETE
    public void delete(Long resultId) {
        RaceResult result = em.find(RaceResult.class, resultId);
        if (result != null) {
            em.remove(result);
        }
    }
    
    // READ BY ID
    public RaceResult findById(Long resultId) {
        return em.find(RaceResult.class, resultId);
    }
    
    // READ ALL
    public List<RaceResult> findAll() {
        TypedQuery<RaceResult> query = em.createQuery( 
                		"SELECT r FROM RaceResult r",
                        RaceResult.class);
        return query.getResultList();
    }
    
    // FIND RESULTS OF A RACE
    public List<RaceResult> findByRace(Long raceId) {
        TypedQuery<RaceResult> query = em.createQuery(
                        "SELECT r FROM RaceResult r WHERE r.race.raceId = :raceId",
                        RaceResult.class);
        query.setParameter("raceId", raceId);
        return query.getResultList();
    }
    
    // FIND RESULTS VERIFIED BY REFEREE
    public List<RaceResult> findByReferee(Long refereeId) {
        TypedQuery<RaceResult> query = em.createQuery(
                        "SELECT r FROM RaceResult r WHERE r.referee.userId = :refereeId",
                        RaceResult.class);
        query.setParameter("refereeId", refereeId);
        return query.getResultList();
    }
    
    // FIND VERIFIED RESULTS
    public List<RaceResult> findVerifiedResults() {
        TypedQuery<RaceResult> query = em.createQuery(
                        "SELECT r FROM RaceResult r " +
                        "WHERE r.verificationStatus = :status",
                        RaceResult.class);
        query.setParameter("status", ResultStatus.VERIFIED);
        return query.getResultList();
    }
    
    // FIND RESULTS BY HORSE
    public List<RaceResult> findByHorse(Long horseId) {
        TypedQuery<RaceResult> query = em.createQuery(
                        "SELECT r FROM RaceResult r " +
                        "WHERE r.horse.horseId = :horseId",
                        RaceResult.class);
        query.setParameter("horseId", horseId);
        return query.getResultList();
    }
    
    // FIND RESULTS BY STATUS
    public List<RaceResult> findByStatus(ResultStatus status) {
        TypedQuery<RaceResult> query = em.createQuery(
                        "SELECT r FROM RaceResult r " +
                        "WHERE r.verificationStatus = :status",
                        RaceResult.class);
        query.setParameter("status", status);
        return query.getResultList();
    }
    
    // RACE RANKING
    public List<RaceResult> getRaceRanking(Long raceId) {
        TypedQuery<RaceResult> query = em.createQuery(
                        "SELECT r FROM RaceResult r " +
                        "WHERE r.race.raceId = :raceId " +
                        "ORDER BY r.position ASC",
                        RaceResult.class);
        query.setParameter("raceId", raceId);
        return query.getResultList();
    }
    
    // TOP 3 OF A RACE
    public List<RaceResult> getTop3(Long raceId) {
        TypedQuery<RaceResult> query = em.createQuery(
                        "SELECT r FROM RaceResult r " +
                        "WHERE r.race.raceId = :raceId " +
                        "ORDER BY r.position ASC",
                        RaceResult.class);
        query.setParameter("raceId", raceId);
        query.setMaxResults(3);
        return query.getResultList();
    }
    
    //WINNER
    public RaceResult getWinner(Long raceId) {
    	TypedQuery<RaceResult> query = em.createQuery(
                        "SELECT r FROM RaceResult r " +
                        "WHERE r.race.raceId = :raceId " +
                        "AND r.position = 1",
                        RaceResult.class);
        query.setParameter("raceId", raceId);
        List<RaceResult> results = query.getResultList();
        return results.isEmpty()
                ? null
                : results.get(0);
    }
    
    //COUNT WIN
    public Long countWinsByHorse(Long horseId) {
        TypedQuery<Long> query = em.createQuery(
                        "SELECT COUNT(r) " +
                        "FROM RaceResult r " +
                        "WHERE r.horse.horseId = :horseId " +
                        "AND r.position = 1",
                        Long.class);
        query.setParameter("horseId", horseId);
        return query.getSingleResult();
    }
    
    public Long countRacesByHorse(Long horseId) {
        TypedQuery<Long> query = em.createQuery(
                        "SELECT COUNT(r) " +
                        "FROM RaceResult r " +
                        "WHERE r.horse.horseId = :horseId",
                        Long.class);
        query.setParameter("horseId", horseId);
        return query.getSingleResult();
    }
}
