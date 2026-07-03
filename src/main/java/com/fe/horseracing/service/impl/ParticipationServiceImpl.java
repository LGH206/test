package com.fe.horseracing.service.impl;

import com.fe.horseracing.DAO.RegistrationDAO;
import com.fe.horseracing.pojo.Jockey;
import com.fe.horseracing.pojo.Registration;
import com.fe.horseracing.pojo.Race;
import com.fe.horseracing.pojo.Horse;
import com.fe.horseracing.repository.interfaces.JockeyRepository;
import com.fe.horseracing.repository.interfaces.RegistrationRepository;
import com.fe.horseracing.repository.RaceRepository;
import com.fe.horseracing.repository.HorseRepository;
import com.fe.horseracing.service.interfaces.IParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Service
@Transactional
public class ParticipationServiceImpl implements IParticipationService {

    private final RegistrationRepository registrationRepository;
    private final JockeyRepository jockeyRepository;
    private final RaceRepository raceRepository;
    private final HorseRepository horseRepository;

    @Autowired
    public ParticipationServiceImpl(RegistrationRepository registrationRepository,
                                    JockeyRepository jockeyRepository,
                                    RaceRepository raceRepository,
                                    HorseRepository horseRepository) {
        this.registrationRepository = registrationRepository;
        this.jockeyRepository = jockeyRepository;
        this.raceRepository = raceRepository;
        this.horseRepository = horseRepository;
    }

    @Override
    public Long registerHorse(Long raceId, Long horseId, Long ownerId, String notes) {
        if (registrationRepository.existsDuplicateRegistration(raceId, horseId)) {
            throw new IllegalStateException("This horse is already registered for this race and is in PENDING/APPROVED status.");
        }

        Race race = raceRepository.findById(raceId)
                .orElseThrow(() -> new IllegalArgumentException("Race not found: " + raceId));
        Horse horse = horseRepository.findById(horseId)
                .orElseThrow(() -> new IllegalArgumentException("Horse not found: " + horseId));

        Registration registration = new Registration();
        registration.setRace(race);
        registration.setHorse(horse);
        registration.setStatus("PENDING");
        registration.setRegistrationDate(LocalDate.now());

        return registrationRepository.save(registration).getRegistrationId();
    }

    @Override
    public boolean confirmParticipation(Long registrationId, Long ownerId) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new IllegalArgumentException("Registration not found: " + registrationId));

        Long actualOwnerId = (registration.getHorse() != null && registration.getHorse().getOwner() != null) ? registration.getHorse().getOwner().getUserId() : null;
        if (actualOwnerId == null || !actualOwnerId.equals(ownerId)) {
            throw new IllegalArgumentException("This registration does not belong to you.");
        }
        if (!"APPROVED".equals(registration.getStatus())) {
            throw new IllegalStateException("Registration must be APPROVED before confirming participation.");
        }
        if (registration.getJockey() == null) {
            throw new IllegalStateException("No Jockey has been assigned to this registration yet.");
        }

        registration.setStatus("CONFIRMED");
        registrationRepository.save(registration);
        return true;
    }

    @Override
    public boolean withdrawRegistration(Long registrationId, Long ownerId, String reason) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new IllegalArgumentException("Registration not found: " + registrationId));

        Long actualOwnerId = (registration.getHorse() != null && registration.getHorse().getOwner() != null) ? registration.getHorse().getOwner().getUserId() : null;
        if (actualOwnerId == null || !actualOwnerId.equals(ownerId)) {
            throw new IllegalArgumentException("This registration does not belong to you.");
        }
        if ("CONFIRMED".equals(registration.getStatus()) || "WITHDRAWN".equals(registration.getStatus()) || "REJECTED".equals(registration.getStatus())) {
            throw new IllegalStateException("Cannot withdraw registration in its current state.");
        }

        registration.setStatus("WITHDRAWN");
        registrationRepository.save(registration);

        if (registration.getJockey() != null) {
            Jockey assignedJockey = registration.getJockey();
            assignedJockey.setJockeyStatus("AVAILABLE");
            jockeyRepository.save(assignedJockey);
        }
        return true;
    }

    @Override
    public boolean approveRegistration(Long registrationId, String notes) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new IllegalArgumentException("Registration not found: " + registrationId));

        if (!"PENDING".equals(registration.getStatus())) {
            throw new IllegalStateException("Only registrations in PENDING status can be approved.");
        }

        registration.setStatus("APPROVED");
        registrationRepository.save(registration);
        return true;
    }

    @Override
    public boolean rejectRegistration(Long registrationId, String reason) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new IllegalArgumentException("Registration not found: " + registrationId));

        if (!"PENDING".equals(registration.getStatus())) {
            throw new IllegalStateException("Only registrations in PENDING status can be rejected.");
        }

        registration.setStatus("REJECTED");
        registrationRepository.save(registration);
        return true;
    }

    @Override
    public RegistrationDAO getRegistrationById(Long registrationId) {
        return registrationRepository.findById(registrationId)
                .map(this::convertToDAO)
                .orElse(null);
    }

    @Override
    public List<RegistrationDAO> getRegistrationsByOwner(Long ownerId) {
        List<RegistrationDAO> daos = new ArrayList<>();
        registrationRepository.findByOwnerId(ownerId).forEach(r -> daos.add(convertToDAO(r)));
        return daos;
    }

    @Override
    public List<RegistrationDAO> getRegistrationsByRace(Long raceId) {
        List<RegistrationDAO> daos = new ArrayList<>();
        registrationRepository.findByRaceId(raceId).forEach(r -> daos.add(convertToDAO(r)));
        return daos;
    }

    @Override
    public List<RegistrationDAO> getRegistrationsByJockey(Long jockeyId) {
        List<RegistrationDAO> daos = new ArrayList<>();
        registrationRepository.findByJockeyId(jockeyId).forEach(r -> daos.add(convertToDAO(r)));
        return daos;
    }

    @Override
    public List<RegistrationDAO> getPendingRegistrations() {
        List<RegistrationDAO> daos = new ArrayList<>();
        registrationRepository.findByStatus("PENDING").forEach(r -> daos.add(convertToDAO(r)));
        return daos;
    }

    private RegistrationDAO convertToDAO(Registration r) {
        if (r == null) return null;
        
        RegistrationDAO dao = new RegistrationDAO();
        dao.setRegistrationId(r.getRegistrationId());
        dao.setRaceId(r.getRace() != null ? r.getRace().getRaceId() : null);
        dao.setHorseId(r.getHorse() != null ? r.getHorse().getHorseId() : null);
        dao.setOwnerId(r.getHorse() != null && r.getHorse().getOwner() != null ? r.getHorse().getOwner().getUserId() : null);
        
        if (r.getJockey() != null) {
            dao.setJockeyId(r.getJockey().getUserId());
            dao.setJockeyName(r.getJockey().getFullName());
        }
        
        dao.setStatus(r.getStatus());
        
        if (r.getHorse() != null) {
            dao.setHorseName(r.getHorse().getHorseName());
        }
        if (r.getRace() != null) {
            dao.setRaceName(r.getRace().getRaceName());
            dao.setRaceDate(r.getRace().getRaceDate());
            dao.setLocation(r.getRace().getLocation());
        }
        return dao;
    }
}
