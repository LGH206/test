package com.fe.horseracing.pojo;

import java.util.List;
import com.fe.horseracing.enums.RefereeStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "raceReferee")
public class RaceReferee extends User {

	@Column(nullable = false, unique = true)
    private String licenseNumber;

	@Column(nullable = false)
    private Integer experienceYears;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
    private RefereeStatus status;
	/*
	 * ACTIVE
	 * SUSPENDED
	 * RETIRED
	 */
	
	@OneToMany(mappedBy = "referee")
	private List<Race> races;
	
	@OneToMany(mappedBy = "referee")
	private List<Invitation> invitations;
	
	@OneToMany(mappedBy = "referee")
	private List<RaceResult> raceResults;
	
	@OneToMany(mappedBy = "referee")
	private List<Violation> violations;
	
	
	public List<Invitation> getInvitations() {
		return invitations;
	}

	public void setInvitations(List<Invitation> invitations) {
		this.invitations = invitations;
	}

	public List<RaceResult> getRaceResults() {
		return raceResults;
	}

	public void setRaceResults(List<RaceResult> raceResults) {
		this.raceResults = raceResults;
	}

	public List<Race> getRaces() {
		return races;
	}

	public void setRaces(List<Race> races) {
		this.races = races;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public Integer getExperienceYears() {
		return experienceYears;
	}

	public void setExperienceYears(Integer experienceYears) {
		this.experienceYears = experienceYears;
	}

	public RefereeStatus getStatus() {
		return status;
	}

	public void setStatus(RefereeStatus status) {
		this.status = status;
	}

	public RaceReferee() {
	}

	public RaceReferee(String licenseNumber, Integer experienceYears, RefereeStatus status) {
		this.licenseNumber = licenseNumber;
		this.experienceYears = experienceYears;
		this.status = status;
	}

	public List<Violation> getViolations() {
		return violations;
	}

	public void setViolations(List<Violation> violations) {
		this.violations = violations;
	}

	@Override
	public String toString() {
		return "RaceReferee [licenseNumber=" + licenseNumber + ", experienceYears=" + experienceYears + ", status="
				+ status + "]";
	}
	
}
