package com.fe.horseracing.pojo;

import java.time.LocalDate;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "races")
public class Race {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long raceId;
	
	@Column(nullable = false)
	private String raceName;
	
    private LocalDate raceDate;

    private String location;

    private String status;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;
    
    @ManyToOne
    @JoinColumn(name = "referee_id")
    private RaceReferee referee;
    
    @OneToMany(mappedBy = "race")
    private List<RaceResult> raceResults;
    
    @OneToMany(mappedBy = "race")
    private List<Invitation> invitations;
    
    @OneToMany(mappedBy = "race")
    private List<Registration> registrations;
    
    @OneToMany(mappedBy = "race")
    private List<Prediction> predictions;
    
    @OneToMany(mappedBy = "race")
    private List<Violation> violations;
    
	public Race() {
	}

	public Long getRaceId() {
		return raceId;
	}

	public void setRaceId(Long raceId) {
		this.raceId = raceId;
	}

	public String getRaceName() {
		return raceName;
	}

	public void setRaceName(String raceName) {
		this.raceName = raceName;
	}

	public LocalDate getRaceDate() {
		return raceDate;
	}

	public void setRaceDate(LocalDate raceDate) {
		this.raceDate = raceDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Tournament getTournament() {
		return tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

	public RaceReferee getReferee() {
		return referee;
	}

	public void setReferee(RaceReferee referee) {
		this.referee = referee;
	}

	public List<RaceResult> getRaceResults() {
		return raceResults;
	}

	public void setRaceResults(List<RaceResult> raceResults) {
		this.raceResults = raceResults;
	}

	public List<Invitation> getInvitations() {
		return invitations;
	}

	public void setInvitations(List<Invitation> invitations) {
		this.invitations = invitations;
	}

	public List<Prediction> getPredictions() {
		return predictions;
	}

	public void setPredictions(List<Prediction> predictions) {
		this.predictions = predictions;
	}

	public List<Registration> getRegistrations() {
		return registrations;
	}

	public void setRegistrations(List<Registration> registrations) {
		this.registrations = registrations;
	}

	public List<Violation> getViolations() {
		return violations;
	}

	public void setViolations(List<Violation> violations) {
		this.violations = violations;
	}

}
