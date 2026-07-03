package com.fe.horseracing.repository.interfaces;

import com.fe.horseracing.pojo.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    @Query("SELECT r FROM Registration r WHERE r.horse.owner.userId = :ownerId")
    List<Registration> findByOwnerId(Long ownerId);

    @Query("SELECT r FROM Registration r WHERE r.race.raceId = :raceId")
    List<Registration> findByRaceId(Long raceId);

    @Query("SELECT r FROM Registration r WHERE r.jockey.userId = :jockeyId")
    List<Registration> findByJockeyId(Long jockeyId);

    @Query("SELECT r FROM Registration r WHERE r.status = :status")
    List<Registration> findByStatus(String status);

    @Query("SELECT COUNT(r) > 0 FROM Registration r WHERE r.race.raceId = :raceId AND r.horse.horseId = :horseId AND r.status NOT IN ('REJECTED', 'WITHDRAWN')")
    boolean existsDuplicateRegistration(Long raceId, Long horseId);

    Registration findByRaceAndHorse(com.fe.horseracing.pojo.Race race, com.fe.horseracing.pojo.Horse horse);
}
