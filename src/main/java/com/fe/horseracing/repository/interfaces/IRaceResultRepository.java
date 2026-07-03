package com.fe.horseracing.repository.interfaces;

import java.util.List;
import com.fe.horseracing.enums.ResultStatus;
import com.fe.horseracing.pojo.RaceResult;

public interface IRaceResultRepository {
    void save(RaceResult raceResult);

    void update(RaceResult raceResult);

    void delete(Long resultId);

    RaceResult findById(Long resultId);

    List<RaceResult> findAll();

    List<RaceResult> findByRace(Long raceId);

    List<RaceResult> findByReferee(Long refereeId);

    List<RaceResult> findVerifiedResults();
    
    List<RaceResult> findByHorse(Long horseId);

    List<RaceResult> findByStatus(ResultStatus status);

    List<RaceResult> getRaceRanking(Long raceId);

    List<RaceResult> getTop3(Long raceId);

    RaceResult getWinner(Long raceId);

    Long countWinsByHorse(Long horseId);

    Long countRacesByHorse(Long horseId);
}
