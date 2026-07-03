package com.fe.horseracing.repository.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.fe.horseracing.DAO.RaceResultDAO;
import com.fe.horseracing.enums.ResultStatus;
import com.fe.horseracing.pojo.RaceResult;
import com.fe.horseracing.repository.interfaces.IRaceResultRepository;

@Repository
public class RaceResultRepository implements IRaceResultRepository{
    
	@Autowired
    private RaceResultDAO raceResultDAO;
    
	@Override
	public void save(RaceResult raceResult) {
		// TODO Auto-generated method stub
		raceResultDAO.save(raceResult);
	}

	@Override
	public void update(RaceResult raceResult) {
		// TODO Auto-generated method stub
		raceResultDAO.update(raceResult);
	}

	@Override
	public void delete(Long resultId) {
		// TODO Auto-generated method stub
		raceResultDAO.delete(resultId);
	}

	@Override
	public RaceResult findById(Long resultId) {
		// TODO Auto-generated method stub
		return raceResultDAO.findById(resultId);
	}

	@Override
	public List<RaceResult> findAll() {
		// TODO Auto-generated method stub
		return raceResultDAO.findAll();
	}

	@Override
	public List<RaceResult> findByRace(Long raceId) {
		// TODO Auto-generated method stub
		return raceResultDAO.findByRace(raceId);
	}

	@Override
	public List<RaceResult> findByReferee(Long refereeId) {
		// TODO Auto-generated method stub
		return raceResultDAO.findByReferee(refereeId);
	}

	@Override
	public List<RaceResult> findVerifiedResults() {
		// TODO Auto-generated method stub
		return raceResultDAO.findVerifiedResults();
	}

	@Override
	public List<RaceResult> findByHorse(Long horseId) {
		// TODO Auto-generated method stub
		return raceResultDAO.findByHorse(horseId);
	}

	@Override
	public List<RaceResult> findByStatus(ResultStatus status) {
		// TODO Auto-generated method stub
		return raceResultDAO.findByStatus(status);
	}

	@Override
	public List<RaceResult> getRaceRanking(Long raceId) {
		// TODO Auto-generated method stub
		return raceResultDAO.getRaceRanking(raceId);
	}

	@Override
	public List<RaceResult> getTop3(Long raceId) {
		// TODO Auto-generated method stub
		 return raceResultDAO.getTop3(raceId);
	}

	@Override
	public RaceResult getWinner(Long raceId) {
		// TODO Auto-generated method stub
		return raceResultDAO.getWinner(raceId);
	}

	@Override
	public Long countWinsByHorse(Long horseId) {
		// TODO Auto-generated method stub
		return raceResultDAO.countWinsByHorse(horseId);
	}

	@Override
	public Long countRacesByHorse(Long horseId) {
		// TODO Auto-generated method stub
		return raceResultDAO.countRacesByHorse(horseId);
	}

}
