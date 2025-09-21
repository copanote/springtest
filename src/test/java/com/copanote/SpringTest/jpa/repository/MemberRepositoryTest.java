package com.copanote.SpringTest.jpa.repository;

import com.copanote.SpringTest.jpa.entity.Member;
import com.copanote.SpringTest.jpa.entity.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnitUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
@Transactional
public class MemberRepositoryTest {


    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @PersistenceContext
    EntityManager em;


    @Test
    void test_findby() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        Member m3 = new Member("AAA", 30);

        memberRepository.save(m1);
        memberRepository.save(m2);
        memberRepository.save(m3);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);
        log.info("result={}", result);
    }


    @Test
    void test_findMemberLazy() {

        Team teamA = new Team("TeamA");
        Team teamB = new Team("TeamB");

        teamRepository.save(teamA);
        teamRepository.save(teamB);

        memberRepository.save(new Member("member1", 10, teamA));
        memberRepository.save(new Member("member2", 20, teamB));

        em.flush();
        em.clear();

        List<Member> members = memberRepository.findAll();

        PersistenceUnitUtil util = em.getEntityManagerFactory().getPersistenceUnitUtil();
        //지연로딩  N+1 문제 발생
        for (Member member : members) {
            log.info("isInitialized={} , isLoaded={}, class={}", Hibernate.isInitialized(member.getTeam()), util.isLoaded(member.getTeam()), member.getTeam().getClass());
            log.info("team name={}", member.getTeam().getName());
        }

        List<Member> members2 = memberRepository.findMemberFetchJoin();
        for (Member member : members2) {
            log.info("isInitialized={} , isLoaded={}, class={}", Hibernate.isInitialized(member.getTeam()), util.isLoaded(member.getTeam()), member.getTeam().getClass());
            log.info("team name={}", member.getTeam().getName());
        }
    }
}
