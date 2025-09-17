package com.copanote.SpringTest.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* com.copanote.SpringTest.aop..*(..))")
    public void call() {
    }
}
