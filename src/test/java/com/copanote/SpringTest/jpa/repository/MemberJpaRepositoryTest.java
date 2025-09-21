package com.copanote.SpringTest.jpa.repository;

import com.copanote.SpringTest.jpa.entity.Member;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;


@Slf4j
@SpringBootTest
@Transactional
public class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    void test_member() {
        Member member = new Member("memberA");
        Member saved = memberJpaRepository.save(member);
        Member find = memberJpaRepository.find(saved.getId());

        Assertions.assertThat(find.getId()).isEqualTo(saved.getId());
        Assertions.assertThat(find).isEqualTo(member);
    }

    @Test
    void test_basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
    }


    @Test
    void test_findByUsernameANdAgeGreaterThan() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        Member m3 = new Member("AAA", 30);

        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);
        memberJpaRepository.save(m3);

        List<Member> result = memberJpaRepository.findByUsernameAndAgeGreaterThan("AAA", 15);
        log.info("");


    }

}
