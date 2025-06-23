package org.example.bookapprest.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

   /* @Around("execution(public * org.example.bookapprest.service.impl.*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        log.debug(">> {}-{}() - {}", joinPoint.getSignature().getDeclaringTypeName(), methodName, Arrays.toString(args));
        Object result = joinPoint.proceed();
        log.debug("<< {}() - {}", methodName, result);
        return result;
    }*/

    @Before("execution(public * org.example.bookapprest.service.impl.*.*(..))")
    public void logBefore(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        log.debug(">> {}-{}() - {}", joinPoint.getSignature().getDeclaringTypeName(), methodName, Arrays.toString(args));
    }

    @AfterThrowing("execution(public * org.example.bookapprest.service.impl.*.*(..))")
    public void logAfterException(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        log.error(">> {}-{}() - {}", joinPoint.getSignature().getDeclaringTypeName(), methodName, Arrays.toString(args));
    }
}
