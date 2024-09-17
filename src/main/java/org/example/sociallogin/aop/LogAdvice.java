package org.example.sociallogin.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Log4j2
public class LogAdvice {

    @Before("execution(* org.example.sociallogin.controller.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("--------------------------------");
        log.info("--------------------------------");

        log.info(Arrays.toString(joinPoint.getArgs()));

        log.info("--------------------------------");
        log.info("--------------------------------");
    }

    @Around("execution(* org.example.sociallogin.service.*.*(..))")
    public Object logBefore(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();

        log.info("TIME: " + (end - start));

        return result;
    }
}
