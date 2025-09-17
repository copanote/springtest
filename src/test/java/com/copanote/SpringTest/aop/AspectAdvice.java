package com.copanote.SpringTest.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;


@Slf4j
@Aspect
public class AspectAdvice {


    @Around("com.copanote.SpringTest.aop.Pointcuts.call()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("around start");
        Object proceed = joinPoint.proceed();
        log.info("around end");
        return proceed;
    }


    @Before("com.copanote.SpringTest.aop.Pointcuts.call()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[before] {}", joinPoint.getSignature());
    }

    @After("com.copanote.SpringTest.aop.Pointcuts.call()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[after] {}", joinPoint.getSignature());
    }

    @AfterReturning("com.copanote.SpringTest.aop.Pointcuts.call()")
    public void doAfterRetuning(JoinPoint joinPoint, Object result) {
        log.info("[AfterReturning] {}, return={}", joinPoint.getSignature(), result);
    }


    @AfterThrowing("com.copanote.SpringTest.aop.Pointcuts.call()")
    public void doAfterThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("[AfterThrowing] {}, ex={}", joinPoint.getSignature(), ex);
    }


}
