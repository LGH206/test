package com.fe.horseracing.repository.interfaces;

import com.fe.horseracing.pojo.Invitation;
import com.fe.horseracing.enums.InvitationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    @Query("SELECT i FROM Invitation i WHERE i.jockey.userId = :jockeyId AND i.status = com.fe.horseracing.enums.InvitationStatus.PENDING AND (i.expiresAt IS NULL OR i.expiresAt > CURRENT_TIMESTAMP) ORDER BY i.sentDate DESC")
    List<Invitation> findPendingInvitationsByJockeyId(Long jockeyId);

    @Query("SELECT i FROM Invitation i WHERE i.jockey.userId = :jockeyId")
    List<Invitation> findByJockeyId(Long jockeyId);

    @Query("SELECT i FROM Invitation i WHERE i.registration.horse.owner.userId = :ownerId")
    List<Invitation> findByOwnerId(Long ownerId);

    @Query("SELECT i FROM Invitation i WHERE i.registration.registrationId = :registrationId AND i.status = :status")
    List<Invitation> findByRegistrationIdAndStatus(Long registrationId, InvitationStatus status);

    @Query("SELECT i FROM Invitation i WHERE i.registration.registrationId = :registrationId")
    List<Invitation> findByRegistrationId(Long registrationId);
}
