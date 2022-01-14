package io.github.nicolasdesnoust.wordsearch.core.aop;

import static net.logstash.logback.marker.Markers.append;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Marker;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
class LogUseCaseExecutionAspect {

    @Around("@annotation(io.github.nicolasdesnoust.wordsearch.core.usecases.LogUseCaseExecution)")
    public Object logUseCaseExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        Object request = getRequestFrom(joinPoint);
        Object response = joinPoint.proceed();

        Marker useCaseMarkers = append("request", request)
                .and(append("response", response));

        String useCaseSimpleName = joinPoint.getThis().getClass().getSimpleName().split("\\$")[0];

        log.info(useCaseMarkers, "Use case {} called", useCaseSimpleName);

        return response;
    }

    private Object getRequestFrom(ProceedingJoinPoint joinPoint) {
        Object useCase;

        try {
            useCase = joinPoint.getArgs()[0];
        } catch(ArrayIndexOutOfBoundsException e) {
            throw new InvalidUseCaseException("Annotation @LogUseCaseExecution may not be placed on a valid UseCase.", e);
        }

        return useCase;
    }

    public static class InvalidUseCaseException extends RuntimeException {
        InvalidUseCaseException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
