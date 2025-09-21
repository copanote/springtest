package com.copanote.SpringTest.jpa.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class TeamTest {


    @PersistenceContext
    EntityManager em;


    @Test
    void createTeam() {

        Team teamA = new Team("teamA");
        em.persist(teamA);


    }

}
