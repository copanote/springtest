package com.copanote.SpringTest.jpa.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class BasicTest {

    @Autowired
    EntityManager em;


    @Test
    void test() {
        Hello hello = new Hello();
        em.persist(hello);

        JPAQueryFactory query = new JPAQueryFactory(em);

        QHello qHello = QHello.hello;

        Hello result = query.selectFrom(qHello).fetchOne();

        log.info("hello Id={}", result.getId());
    }

}
