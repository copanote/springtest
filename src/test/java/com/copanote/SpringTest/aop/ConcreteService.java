package com.copanote.SpringTest.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConcreteService {

    public void call() {
        log.info("ConcreteService call");
    }

}
