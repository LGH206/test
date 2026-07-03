package com.fe.horseracing.service.impl;

import com.fe.horseracing.DAO.JockeyDAO;
import com.fe.horseracing.DAO.RaceDAO;
import com.fe.horseracing.DAO.RaceResultDAO;
import com.fe.horseracing.DAO.HorseDAO;
import com.fe.horseracing.pojo.Jockey;
import com.fe.horseracing.pojo.Race;
import com.fe.horseracing.pojo.RaceResult;
import com.fe.horseracing.pojo.Horse;
import com.fe.horseracing.repository.UserRepository;
import com.fe.horseracing.repository.interfaces.JockeyRepository;
import com.fe.horseracing.repository.interfaces.RaceResultRepository;
import com.fe.horseracing.repository.interfaces.RegistrationRepository;
import com.fe.horseracing.repository.RaceRepository;
import com.fe.horseracing.repository.HorseRepository;
import com.fe.horseracing.service.interfaces.IJockeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Service
@Transactional
public class JockeyServiceImpl implements IJockeyService {

    private final JockeyRepository jockeyRepository;
    private final UserRepository userRepository;
    private final RaceRepository raceRepository;
    private final RaceResultRepository raceResultRepository;
    private final HorseRepository horseRepository;
    private final RegistrationRepository registrationRepository;

    @Autowired
    public JockeyServiceImpl(JockeyRepository jockeyRepository,
                             UserRepository userRepository,
                             RaceRepository raceRepository,
                             RaceResultRepository raceResultRepository,
                             HorseRepository horseRepository,
                             RegistrationRepository registrationRepository) {
        this.jockeyRepository = jockeyRepository;
        this.userRepository = userRepository;
        this.raceRepository = raceRepository;
        this.raceResultRepository = raceResultRepository;
        this.horseRepository = horseRepository;
        this.registrationRepository = registrationRepository;
    }

    @Override
    public Long registerJockey(Jockey jockey, String rawPassword) {
        if (jockeyRepository.existsByLicenseNumber(jockey.getLicenseNumber())) {
            throw new IllegalArgumentException("License number already exists.");
        }
        if (userRepository.existsByUserName(jockey.getUserName())) {
            throw new IllegalArgumentException("Username is already taken.");
        }
        if (userRepository.existsByEmail(jockey.getEmail())) {
            throw new IllegalArgumentException("Email already exists.");
        }
        
        String hashedPassword = org.mindrot.jbcrypt.BCrypt.hashpw(rawPassword, org.mindrot.jbcrypt.BCrypt.gensalt(12));
        jockey.setPassword(hashedPassword);
        
        return jockeyRepository.save(jockey).getUserId();
    }

    @Override
    public JockeyDAO getJockeyById(Long jockeyId) {
        return jockeyRepository.findById(jockeyId)
                .map(this::convertToDAO)
                .orElse(null);
    }

    @Override
    public JockeyDAO getJockeyByUserId(Long userId) {
        return jockeyRepository.findByUserId(userId)
                .map(this::convertToDAO)
                .orElse(null);
    }

    @Override
    public List<JockeyDAO> getAllJockeys() {
        List<JockeyDAO> daos = new ArrayList<>();
        jockeyRepository.findAll().forEach(j -> daos.add(convertToDAO(j)));
        return daos;
    }

    @Override
    public List<JockeyDAO> getAvailableJockeys() {
        List<JockeyDAO> daos = new ArrayList<>();
        jockeyRepository.findByJockeyStatus("AVAILABLE").forEach(j -> daos.add(convertToDAO(j)));
        return daos;
    }

    @Override
    public boolean updateJockeyProfile(Jockey jockey) {
        return jockeyRepository.findById(jockey.getUserId()).map(j -> {
            j.setFirstName(jockey.getFirstName());
            j.setLastName(jockey.getLastName());
            j.setPhoneNumber(jockey.getPhoneNumber());
            j.setEmail(jockey.getEmail());
            j.setNationality(jockey.getNationality());
            j.setDateOfBirth(jockey.getDateOfBirth());
            j.setWeight(jockey.getWeight());
            j.setExperienceYears(jockey.getExperienceYears());
            if (jockey.getProfileImageUrl() != null) {
                j.setProfileImageUrl(jockey.getProfileImageUrl());
            }
            jockeyRepository.save(j);
            return true;
        }).orElse(false);
    }

    @Override
    public boolean blockJockey(Long jockeyId) {
        return jockeyRepository.findById(jockeyId).map(j -> {
            j.setJockeyStatus("SUSPENDED");
            jockeyRepository.save(j);
            return true;
        }).orElse(false);
    }

    @Override
    public boolean activateJockey(Long jockeyId) {
        return jockeyRepository.findById(jockeyId).map(j -> {
            j.setJockeyStatus("AVAILABLE");
            jockeyRepository.save(j);
            return true;
        }).orElse(false);
    }

    @Override
    public boolean updateJockeyResults(Long jockeyId, boolean winStatus) {
        return jockeyRepository.findById(jockeyId).map(j -> {
            j.setTotalRaces(j.getTotalRaces() + 1);
            if (winStatus) {
                j.setTotalWins(j.getTotalWins() + 1);
            }
            jockeyRepository.save(j);
            return true;
        }).orElse(false);
    }

    @Override
    public List<JockeyDAO> getJockeyLeaderboard() {
        List<JockeyDAO> daos = new ArrayList<>();
        jockeyRepository.findAllByOrderByTotalWinsDesc().forEach(j -> daos.add(convertToDAO(j)));
        return daos;
    }

    @Override
    public List<RaceDAO> getRaceSchedule() {
        List<RaceDAO> daos = new ArrayList<>();
        raceRepository.findByStatusInOrderByRaceDateAsc(List.of("PENDING")).forEach(r -> {
            RaceDAO dao = new RaceDAO();
            dao.setRaceId(r.getRaceId());
            dao.setRaceName(r.getRaceName());
            dao.setRaceDate(r.getRaceDate());
            dao.setLocation(r.getLocation());
            dao.setStatus(r.getStatus());
            dao.setTournamentId(r.getTournamentId());
            daos.add(dao);
        });
        return daos;
    }

    @Override
    public List<RaceResultDAO> getPersonalResults(Long jockeyId) {
        List<RaceResultDAO> daos = new ArrayList<>();
        raceResultRepository.findByJockeyUserId(jockeyId).forEach(rr -> {
            com.fe.horseracing.pojo.Registration reg = registrationRepository.findByRaceAndHorse(rr.getRace(), rr.getHorse());
            com.fe.horseracing.pojo.Jockey jockey = (reg != null) ? reg.getJockey() : null;
            
            RaceResultDAO dao = new RaceResultDAO();
            dao.setResultId(rr.getResultId());
            dao.setRaceId(rr.getRace() != null ? rr.getRace().getRaceId() : null);
            dao.setJockeyId(jockey != null ? jockey.getUserId() : null);
            dao.setHorseId(rr.getHorse() != null ? rr.getHorse().getHorseId() : null);
            dao.setPosition(rr.getPosition());
            dao.setTimeTaken(rr.getFinishTime());
            dao.setPrizeMoney(null);
            dao.setNotes(rr.getRemarks());
            
            if (rr.getRace() != null) {
                dao.setRaceName(rr.getRace().getRaceName());
            }
            if (jockey != null) {
                dao.setJockeyName(jockey.getFullName());
            }
            if (rr.getHorse() != null) {
                dao.setHorseName(rr.getHorse().getHorseName());
            }
            daos.add(dao);
        });
        return daos;
    }

    @Override
    public HorseDAO getHorseDetails(Long horseId) {
        return horseRepository.findById(horseId).map(h -> {
            HorseDAO dao = new HorseDAO();
            dao.setHorseId(h.getHorseId());
            dao.setName(h.getHorseName());
            dao.setBreed(h.getBreed());
            dao.setAge(h.getAge());
            dao.setHealthStatus(h.getStatus());
            dao.setAchievements(h.getMedicalCertificate());
            dao.setOwnerId(h.getOwner() != null ? h.getOwner().getUserId() : null);
            dao.setStatus(h.getStatus());
            return dao;
        }).orElse(null);
    }

    private JockeyDAO convertToDAO(Jockey j) {
        if (j == null) return null;
        JockeyDAO dao = new JockeyDAO();
        dao.setUserId(j.getUserId());
        dao.setUserName(j.getUserName());
        dao.setEmail(j.getEmail());
        dao.setFullName(j.getFullName());
        dao.setPhoneNumber(j.getPhoneNumber());
        dao.setRole(j.getRole());
        dao.setStatus(j.getJockeyStatus());
        dao.setLicenseNumber(j.getLicenseNumber());
        dao.setNationality(j.getNationality());
        dao.setDateOfBirth(j.getDateOfBirth());
        dao.setWeight(j.getWeight());
        dao.setExperienceYears(j.getExperienceYears());
        dao.setTotalRaces(j.getTotalRaces());
        dao.setTotalWins(j.getTotalWins());
        dao.setWinRate(j.getWinRate());
        dao.setProfileImageUrl(j.getProfileImageUrl());
        dao.setJockeyStatus(j.getJockeyStatus());
        return dao;
    }
}
