package com.copanote.SpringTest.jpa.repository;

import com.copanote.SpringTest.jpa.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
