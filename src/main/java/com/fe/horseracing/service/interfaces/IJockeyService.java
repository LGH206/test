package com.fe.horseracing.service.interfaces;

import com.fe.horseracing.DAO.JockeyDAO;
import com.fe.horseracing.DAO.RaceDAO;
import com.fe.horseracing.DAO.HorseDAO;
import com.fe.horseracing.DAO.RaceResultDAO;
import com.fe.horseracing.pojo.Jockey;
import java.util.List;

public interface IJockeyService {
    Long registerJockey(Jockey jockey, String rawPassword);
    JockeyDAO getJockeyById(Long jockeyId);
    JockeyDAO getJockeyByUserId(Long userId);
    List<JockeyDAO> getAllJockeys();
    List<JockeyDAO> getAvailableJockeys();
    boolean updateJockeyProfile(Jockey jockey);
    boolean blockJockey(Long jockeyId);
    boolean activateJockey(Long jockeyId);
    boolean updateJockeyResults(Long jockeyId, boolean winStatus);
    List<JockeyDAO> getJockeyLeaderboard();
    List<RaceDAO> getRaceSchedule();
    List<RaceResultDAO> getPersonalResults(Long jockeyId);
    HorseDAO getHorseDetails(Long horseId);
}
