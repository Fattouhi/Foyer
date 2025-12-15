package com.esprim.Foyer.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class PerformanceAspect {

    @Around("execution(* com.esprim.Foyer.services.ReservationServiceImpl.ajouterReservation(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        log.info("Début de l'exécution de la méthode: " + joinPoint.getSignature().getName());

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        log.info("Fin de l'exécution de la méthode: " + joinPoint.getSignature().getName());
        log.info("Temps d'exécution: " + executionTime + " ms");

        return result;
    }
}