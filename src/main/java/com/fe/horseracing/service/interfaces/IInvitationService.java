package com.fe.horseracing.service.interfaces;

import com.fe.horseracing.DAO.InvitationDAO;
import java.util.List;

public interface IInvitationService {
    Long sendInvitation(Long registrationId, Long jockeyId, String message, int daysValid);
    boolean acceptInvitation(Long invitationId, Long jockeyId, String responseMessage);
    boolean declineInvitation(Long invitationId, Long jockeyId, String responseMessage);
    boolean cancelInvitation(Long invitationId, Long ownerId);
    InvitationDAO getInvitationById(Long invitationId);
    List<InvitationDAO> getPendingInvitations(Long jockeyId);
    List<InvitationDAO> getSentInvitations(Long ownerId);
}
