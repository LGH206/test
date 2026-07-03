package com.fe.horseracing.DAO;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.fe.horseracing.pojo.Violation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class ViolationDAO {
	
    @PersistenceContext
    private EntityManager em;
    
    public void save(Violation violation) {
        em.persist(violation);
    }

    public void update(Violation violation) {
        em.merge(violation);
    }

    public void delete(Long violationId) {
        Violation violation = em.find(Violation.class, violationId);
        if (violation != null) {
            em.remove(violation);
        }
    }

    public Violation findById(Long violationId) {
        return em.find(Violation.class, violationId);
    }

    public List<Violation> findAll() {
        TypedQuery<Violation> query = em.createQuery(
                        "SELECT v FROM Violation v",
                        Violation.class);
        return query.getResultList();
    }

    public List<Violation> findByRace(Long raceId) {
        TypedQuery<Violation> query = em.createQuery(
                        "SELECT v FROM Violation v " +
                        "WHERE v.race.raceId = :raceId",
                        Violation.class);
        query.setParameter("raceId", raceId);
        return query.getResultList();
    }

    public List<Violation> findByHorse(Long horseId) {
        TypedQuery<Violation> query = em.createQuery(
                        "SELECT v FROM Violation v " +
                        "WHERE v.horse.horseId = :horseId",
                        Violation.class);
        query.setParameter("horseId", horseId);
        return query.getResultList();
    }

    public List<Violation> findByReferee(Long refereeId) {
        TypedQuery<Violation> query = em.createQuery(
                        "SELECT v FROM Violation v " +
                        "WHERE v.referee.userId = :refereeId",
                        Violation.class);
        query.setParameter("refereeId", refereeId);
        return query.getResultList();
    }
}
