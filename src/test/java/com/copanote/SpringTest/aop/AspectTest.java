package com.copanote.SpringTest.aop;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;


@Slf4j
@Import(AspectAdvice.class)
@SpringBootTest
public class AspectTest {


    @Autowired
    ConcreteService cs;


    @Test
    void testAop() {
        cs.call();
    }
}
