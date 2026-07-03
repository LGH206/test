package com.fe.horseracing.pojo;

import java.time.LocalDateTime;
import com.fe.horseracing.enums.InvitationStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "invitations")
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invitationId;

    private LocalDateTime sentDate;

    private LocalDateTime responseDate;

    @Enumerated(EnumType.STRING)
    private InvitationStatus status;

    private String message;

    @ManyToOne
    @JoinColumn(name = "race_id")
    private Race race;

    @ManyToOne
    @JoinColumn(name = "referee_id")
    private User referee;

    @ManyToOne
    @JoinColumn(name = "jockey_id")
    private Jockey jockey;

    @ManyToOne
    @JoinColumn(name = "registration_id")
    private Registration registration;

    private String responseMessage;

    private LocalDateTime expiresAt;

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    @Transient
    public boolean isExpired() {
        return expiresAt != null && LocalDateTime.now().isAfter(expiresAt);
    }

	public Long getInvitationId() {
		return invitationId;
	}

	public void setInvitationId(Long invitationId) {
		this.invitationId = invitationId;
	}

	public LocalDateTime getSentDate() {
		return sentDate;
	}

	public void setSentDate(LocalDateTime sentDate) {
		this.sentDate = sentDate;
	}

	public LocalDateTime getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(LocalDateTime responseDate) {
		this.responseDate = responseDate;
	}

	public InvitationStatus getStatus() {
		return status;
	}

	public void setStatus(InvitationStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
	}

	public User getReferee() {
		return referee;
	}

	public void setReferee(User referee) {
		this.referee = referee;
	}

	public Jockey getJockey() {
		return jockey;
	}

	public void setJockey(Jockey jockey) {
		this.jockey = jockey;
	}

	public Registration getRegistration() {
		return registration;
	}

	public void setRegistration(Registration registration) {
		this.registration = registration;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	
	public Invitation() {
	}

	public Invitation(Long invitationId, LocalDateTime sentDate, LocalDateTime responseDate, InvitationStatus status,
			String message, Race race, User referee) {
		super();
		this.invitationId = invitationId;
		this.sentDate = sentDate;
		this.responseDate = responseDate;
		this.status = status;
		this.message = message;
		this.race = race;
		this.referee = referee;
	}

	@Override
	public String toString() {
		return "Invitation [invitationId=" + invitationId + ", status=" + status + "]";
	}
}
