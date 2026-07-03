package com.fe.horseracing.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fe.horseracing.pojo.Horse;

@Repository
public interface HorseRepository extends JpaRepository<Horse, Long> {

}
