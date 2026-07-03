package com.fe.horseracing.pojo;

import java.time.LocalDateTime;
import com.fe.horseracing.enums.ResultStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "race_results")
public class RaceResult {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long resultId;
	
	private Integer position;
	
	private Double finishTime;
	
	private Double score;
	
	@Enumerated(EnumType.STRING)
    private ResultStatus verificationStatus;

    private String remarks;

    private LocalDateTime updateTime;
	
    @ManyToOne
    @JoinColumn(name = "race_id")
    private Race race;

    @ManyToOne
    @JoinColumn(name = "horse_id")
    private Horse horse;
    
	@ManyToOne
	@JoinColumn(name = "referee_id")
	private RaceReferee referee;
	
	public ResultStatus getVerificationStatus() {
		return verificationStatus;
	}

	public void setVerificationStatus(ResultStatus verificationStatus) {
		this.verificationStatus = verificationStatus;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	public RaceReferee getReferee() {
		return referee;
	}

	public void setReferee(RaceReferee referee) {
		this.referee = referee;
	}

	public Long getResultId() {
		return resultId;
	}

	public void setResultId(Long resultId) {
		this.resultId = resultId;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Double getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Double finishTime) {
		this.finishTime = finishTime;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
	}

	public Horse getHorse() {
		return horse;
	}

	public void setHorse(Horse horse) {
		this.horse = horse;
	}
	
	public RaceResult() {
		super();
	}

	public RaceResult(Long resultId, Integer position, Double finishTime, Double score, ResultStatus verificationStatus,
			String remarks, LocalDateTime updateTime, Race race, Horse horse, RaceReferee referee) {
		this.resultId = resultId;
		this.position = position;
		this.finishTime = finishTime;
		this.score = score;
		this.verificationStatus = verificationStatus;
		this.remarks = remarks;
		this.updateTime = updateTime;
		this.race = race;
		this.horse = horse;
		this.referee = referee;
	}

	@Override
	public String toString() {
		return "RaceResult [resultId=" + resultId + ", position=" + position + ", verificationStatus="
				+ verificationStatus + "]";
	}
	

	
}
