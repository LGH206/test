package com.fe.horseracing.pojo;

import java.time.LocalDateTime;
import com.fe.horseracing.enums.ViolationType;
import jakarta.persistence.*;

@Entity
@Table(name = "violations")
public class Violation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long violationId;

    @Enumerated(EnumType.STRING)
    private ViolationType violationType;
    
    @PrePersist
    public void prePersist() {
    	createdAt = LocalDateTime.now();
    }

    @Column(nullable = false)
    private String description;

    private String penalty;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "race_id")
    private Race race;

    @ManyToOne
    @JoinColumn(name = "horse_id")
    private Horse horse;

    @ManyToOne
    @JoinColumn(name = "jockey_id")
    private Jockey jockey;

    @ManyToOne
    @JoinColumn(name = "referee_id")
    private RaceReferee referee;

    public Violation() {
    }

	public Long getViolationId() {
		return violationId;
	}

	public void setViolationId(Long violationId) {
		this.violationId = violationId;
	}

	public ViolationType getViolationType() {
		return violationType;
	}

	public void setViolationType(ViolationType violationType) {
		this.violationType = violationType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPenalty() {
		return penalty;
	}

	public void setPenalty(String penalty) {
		this.penalty = penalty;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
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

	public Jockey getJockey() {
		return jockey;
	}

	public void setJockey(Jockey jockey) {
		this.jockey = jockey;
	}

	public RaceReferee getReferee() {
		return referee;
	}

	public void setReferee(RaceReferee referee) {
		this.referee = referee;
	}

	@Override
	public String toString() {
		return "Violation [violationId=" + violationId + ", violationType=" + violationType + ", description="
				+ description + ", penalty=" + penalty + ", createdAt=" + createdAt + "]";
	}
	
}
