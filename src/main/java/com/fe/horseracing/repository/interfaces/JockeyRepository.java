package com.fe.horseracing.repository.interfaces;

import com.fe.horseracing.pojo.Jockey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface JockeyRepository extends JpaRepository<Jockey, Long> {
    Optional<Jockey> findByLicenseNumber(String licenseNumber);
    boolean existsByLicenseNumber(String licenseNumber);
    List<Jockey> findByJockeyStatus(String status);
    List<Jockey> findAllByOrderByTotalWinsDesc();

    @Query("SELECT j FROM Jockey j WHERE j.userId = :userId")
    Optional<Jockey> findByUserId(Long userId);
}
