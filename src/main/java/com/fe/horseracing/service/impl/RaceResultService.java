package com.fe.horseracing.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fe.horseracing.enums.ResultStatus;
import com.fe.horseracing.pojo.RaceResult;
import com.fe.horseracing.repository.interfaces.IRaceResultRepository;
import com.fe.horseracing.service.interfaces.IRaceResultService;

@Service
public class RaceResultService implements IRaceResultService {

    private IRaceResultRepository raceResultRepo;

	@Autowired
    public RaceResultService( IRaceResultRepository raceResultRepo) {
        this.raceResultRepo = raceResultRepo;
    }

	@Override
	public void save(RaceResult raceResult) {
		// TODO Auto-generated method stub
		raceResultRepo.save(raceResult);
	}

	@Override
	public void update(RaceResult raceResult) {
		// TODO Auto-generated method stub
		raceResultRepo.update(raceResult);
	}

	@Override
	public void delete(Long resultId) {
		// TODO Auto-generated method stub
		raceResultRepo.delete(resultId);
	}

	@Override
	public RaceResult findById(Long resultId) {
		// TODO Auto-generated method stub
		 return raceResultRepo.findById(resultId);
	}

	@Override
	public List<RaceResult> findAll() {
		// TODO Auto-generated method stub
        return raceResultRepo.findAll();
	}

	@Override
	public List<RaceResult> findByRace(Long raceId) {
		// TODO Auto-generated method stub
		 return raceResultRepo.findByRace(raceId);
	}

	@Override
	public List<RaceResult> findByReferee(Long refereeId) {
		// TODO Auto-generated method stub
		return raceResultRepo.findByReferee(refereeId);
	}

	@Override
	public List<RaceResult> findVerifiedResults() {
		// TODO Auto-generated method stub
		return raceResultRepo.findVerifiedResults();
	}

	@Override
	public List<RaceResult> findByHorse(Long horseId) {
		// TODO Auto-generated method stub
		return raceResultRepo.findByHorse(horseId);
	}

	@Override
	public List<RaceResult> findByStatus(ResultStatus status) {
		// TODO Auto-generated method stub
		return raceResultRepo.findByStatus(status);
	}

	@Override
	public List<RaceResult> getRaceRanking(Long raceId) {
		// TODO Auto-generated method stub
		return raceResultRepo.getRaceRanking(raceId);
	}

	@Override
	public List<RaceResult> getTop3(Long raceId) {
		// TODO Auto-generated method stub
		return raceResultRepo.getTop3(raceId);
	}

	@Override
	public RaceResult getWinner(Long raceId) {
		// TODO Auto-generated method stub
		return raceResultRepo.getWinner(raceId);
	}

	@Override
	public Long countWinsByHorse(Long horseId) {
		// TODO Auto-generated method stub
		return raceResultRepo.countWinsByHorse(horseId);
	}

	@Override
	public Long countRacesByHorse(Long horseId) {
		// TODO Auto-generated method stub
		return raceResultRepo.countRacesByHorse(horseId);
	}

}
