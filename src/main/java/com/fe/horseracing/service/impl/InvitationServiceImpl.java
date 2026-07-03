package com.fe.horseracing.service.impl;

import com.fe.horseracing.DAO.InvitationDAO;
import com.fe.horseracing.pojo.Invitation;
import com.fe.horseracing.pojo.Jockey;
import com.fe.horseracing.pojo.Registration;
import com.fe.horseracing.pojo.Race;
import com.fe.horseracing.pojo.Horse;
import com.fe.horseracing.pojo.User;
import com.fe.horseracing.enums.InvitationStatus;
import com.fe.horseracing.repository.*;
import com.fe.horseracing.repository.interfaces.InvitationRepository;
import com.fe.horseracing.repository.interfaces.JockeyRepository;
import com.fe.horseracing.repository.interfaces.RegistrationRepository;
import com.fe.horseracing.service.interfaces.IInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Service
@Transactional
public class InvitationServiceImpl implements IInvitationService {

    private final InvitationRepository invitationRepository;
    private final RegistrationRepository registrationRepository;
    private final JockeyRepository jockeyRepository;
    private final RaceRepository raceRepository;
    private final HorseRepository horseRepository;
    private final UserRepository userRepository;

    @Autowired
    public InvitationServiceImpl(InvitationRepository invitationRepository,
                                 RegistrationRepository registrationRepository,
                                 JockeyRepository jockeyRepository,
                                 RaceRepository raceRepository,
                                 HorseRepository horseRepository,
                                 UserRepository userRepository) {
        this.invitationRepository = invitationRepository;
        this.registrationRepository = registrationRepository;
        this.jockeyRepository = jockeyRepository;
        this.raceRepository = raceRepository;
        this.horseRepository = horseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Long sendInvitation(Long registrationId, Long jockeyId, String message, int daysValid) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new IllegalArgumentException("Registration not found: " + registrationId));

        // Check registration status
        if (!"APPROVED".equals(registration.getStatus()) && !"PENDING".equals(registration.getStatus())) {
            throw new IllegalStateException("Registration status must be APPROVED or PENDING to send an invitation.");
        }

        // Check if there is already a PENDING invitation from this jockey for this registration
        List<Invitation> pendingInvitations = invitationRepository.findByRegistrationIdAndStatus(registrationId, InvitationStatus.PENDING);
        boolean hasPending = false;
        for (Invitation invitation : pendingInvitations) {
            if (invitation.getJockey() != null && invitation.getJockey().getUserId().equals(jockeyId)) {
                hasPending = true;
                break;
            }
        }
        
        if (hasPending) {
            throw new IllegalStateException("This Jockey already has a pending invitation for this registration.");
        }

        Jockey recipientJockey = jockeyRepository.findById(jockeyId).orElseThrow(() -> new IllegalArgumentException("Jockey not found: " + jockeyId));
        if (!"AVAILABLE".equals(recipientJockey.getJockeyStatus())) {
            throw new IllegalArgumentException("Jockey is currently BUSY or SUSPENDED and cannot be invited.");
        }

        LocalDateTime expiryDate = LocalDateTime.now().plusDays(daysValid > 0 ? daysValid : 3);

        Invitation invitation = new Invitation();
        invitation.setRegistration(registration);
        invitation.setJockey(recipientJockey);
        invitation.setRace(registration.getRace());
        invitation.setMessage(message);
        invitation.setExpiresAt(expiryDate);
        invitation.setStatus(InvitationStatus.PENDING);
        invitation.setSentDate(LocalDateTime.now());

        Invitation savedInvitation = invitationRepository.save(invitation);
        return savedInvitation.getInvitationId();
    }

    @Override
    public boolean acceptInvitation(Long invitationId, Long jockeyId, String responseMessage) {
        Invitation invitation = invitationRepository.findById(invitationId).orElseThrow(() -> new IllegalArgumentException("Invitation not found: " + invitationId));
        if (invitation.getJockey() == null || !invitation.getJockey().getUserId().equals(jockeyId)) {
            throw new IllegalArgumentException("This invitation is not addressed to you.");
        }
        
        if (invitation.getStatus() != InvitationStatus.PENDING) {
            throw new IllegalStateException("This invitation is no longer in PENDING status.");
        }
        
        if (invitation.isExpired()) {
            invitation.setStatus(InvitationStatus.EXPIRED);
            invitationRepository.save(invitation);
            throw new IllegalStateException("This invitation has expired.");
        }

        // Accept invitation
        invitation.setStatus(InvitationStatus.ACCEPTED);
        invitation.setResponseMessage(responseMessage);
        invitation.setResponseDate(LocalDateTime.now());
        invitationRepository.save(invitation);

        // Assign Jockey to the race registration
        Registration registration = invitation.getRegistration();
        if (registration != null) {
            registration.setJockey(invitation.getJockey());
            if ("APPROVED".equals(registration.getStatus())) {
                registration.setStatus("CONFIRMED");
            }
            registrationRepository.save(registration);

            // Automatically reject/cancel other PENDING invitations for this registration
            List<Invitation> otherPending = invitationRepository.findByRegistrationIdAndStatus(registration.getRegistrationId(), InvitationStatus.PENDING);
            for (Invitation other : otherPending) {
                other.setStatus(InvitationStatus.REJECTED);
                invitationRepository.save(other);
            }
        }

        // Update Jockey's status to BUSY
        Jockey assignedJockey = invitation.getJockey();
        assignedJockey.setJockeyStatus("BUSY");
        jockeyRepository.save(assignedJockey);

        return true;
    }

    @Override
    public boolean declineInvitation(Long invitationId, Long jockeyId, String responseMessage) {
        Invitation invitation = invitationRepository.findById(invitationId).orElseThrow(() -> new IllegalArgumentException("Invitation not found: " + invitationId));
        
        if (invitation.getJockey() == null || !invitation.getJockey().getUserId().equals(jockeyId)) {
            throw new IllegalArgumentException("This invitation is not addressed to you.");
        }
        
        if (invitation.getStatus() != InvitationStatus.PENDING) {
            throw new IllegalStateException("Invitation is no longer in PENDING status.");
        }

        invitation.setStatus(InvitationStatus.REJECTED);
        invitation.setResponseMessage(responseMessage);
        invitation.setResponseDate(LocalDateTime.now());
        invitationRepository.save(invitation);
        return true;
    }

    @Override
    public boolean cancelInvitation(Long invitationId, Long ownerId) {
        Invitation invitation = invitationRepository.findById(invitationId).orElseThrow(() -> new IllegalArgumentException("Invitation not found: " + invitationId));
        
        Long actualOwnerId = (invitation.getRegistration() != null && invitation.getRegistration().getHorse() != null && invitation.getRegistration().getHorse().getOwner() != null) 
                       ? invitation.getRegistration().getHorse().getOwner().getUserId() : null;
        if (actualOwnerId == null || !actualOwnerId.equals(ownerId)) {
            throw new IllegalArgumentException("You do not own this invitation.");
        }

        if (invitation.getStatus() != InvitationStatus.PENDING) {
            throw new IllegalStateException("Can only cancel invitations in PENDING status.");
        }

        invitation.setStatus(InvitationStatus.REJECTED);
        invitationRepository.save(invitation);
        return true;
    }

    @Override
    public InvitationDAO getInvitationById(Long invitationId) {
        Optional<Invitation> optInvitation = invitationRepository.findById(invitationId);
        if (optInvitation.isPresent()) {
            return convertToDAO(optInvitation.get());
        }
        return null;
    }

    @Override
    public List<InvitationDAO> getPendingInvitations(Long jockeyId) {
        List<Invitation> invitations = invitationRepository.findPendingInvitationsByJockeyId(jockeyId);
        List<InvitationDAO> daos = new ArrayList<>();
        
        for (Invitation i : invitations) {
            daos.add(convertToDAO(i));
        }
        
        return daos;
    }

    @Override
    public List<InvitationDAO> getSentInvitations(Long ownerId) {
        List<Invitation> invitations = invitationRepository.findByOwnerId(ownerId);
        List<InvitationDAO> daos = new ArrayList<>();
        
        for (Invitation i : invitations) {
            daos.add(convertToDAO(i));
        }
        
        return daos;
    }

    private InvitationDAO convertToDAO(Invitation i) {
        if (i == null) {
            return null;
        }
        
        InvitationDAO dao = new InvitationDAO();
        dao.setInvitationId(i.getInvitationId());
        
        if (i.getRegistration() != null) {
            dao.setRegistrationId(i.getRegistration().getRegistrationId());
            if (i.getRegistration().getHorse() != null) {
                dao.setHorseId(i.getRegistration().getHorse().getHorseId());
                dao.setHorseName(i.getRegistration().getHorse().getHorseName());
                if (i.getRegistration().getHorse().getOwner() != null) {
                    dao.setOwnerId(i.getRegistration().getHorse().getOwner().getUserId());
                    dao.setOwnerName(i.getRegistration().getHorse().getOwner().getFullName());
                }
            }
        }
        
        if (i.getJockey() != null) {
            dao.setJockeyId(i.getJockey().getUserId());
            dao.setJockeyName(i.getJockey().getFullName());
        }
        
        dao.setRaceId(i.getRace() != null ? i.getRace().getRaceId() : null);
        dao.setStatus(i.getStatus() != null ? i.getStatus().name() : null);
        dao.setMessage(i.getMessage());
        dao.setResponseMessage(i.getResponseMessage());
        dao.setSentAt(i.getSentDate());
        dao.setRespondedAt(i.getResponseDate());
        dao.setExpiresAt(i.getExpiresAt());

        if (i.getRace() != null) {
            dao.setRaceName(i.getRace().getRaceName());
            dao.setRaceDate(i.getRace().getRaceDate());
            dao.setLocation(i.getRace().getLocation());
        }

        return dao;
    }
}
