package com.fe.horseracing.service.interfaces;

import com.fe.horseracing.DAO.RegistrationDAO;
import java.util.List;

public interface IParticipationService {
    Long registerHorse(Long raceId, Long horseId, Long ownerId, String notes);
    boolean approveRegistration(Long registrationId, String notes);
    boolean rejectRegistration(Long registrationId, String reason);
    boolean confirmParticipation(Long registrationId, Long ownerId);
    boolean withdrawRegistration(Long registrationId, Long ownerId, String reason);
    RegistrationDAO getRegistrationById(Long registrationId);
    List<RegistrationDAO> getRegistrationsByOwner(Long ownerId);
    List<RegistrationDAO> getRegistrationsByRace(Long raceId);
    List<RegistrationDAO> getRegistrationsByJockey(Long jockeyId);
    List<RegistrationDAO> getPendingRegistrations();
}
