package io.github.nicolasdesnoust.wordsearch.core.aop;

import static net.logstash.logback.marker.Markers.append;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Marker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import io.github.nicolasdesnoust.wordsearch.core.api.RestApiError;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
class LogRestApiErrorAspect {

    public static final String INVALID_EXCEPTION_HANDLER = "Annotation @LogRestApiError may not be placed on a valid @ExceptionHandler.";

    @Around("@annotation(io.github.nicolasdesnoust.wordsearch.core.api.LogRestApiError)")
    public ResponseEntity<Object> logRestApiError(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] arguments = joinPoint.getArgs();
        Object response = joinPoint.proceed();

        ResponseEntity<Object> responseEntity = getResponseEntityFrom(response);

        RestApiError restApiError = getRestApiErrorFrom(responseEntity);
        Exception exception = getSourceExceptionFrom(arguments);
        logRestApiError(restApiError, exception);

        return responseEntity;
    }

    private void logRestApiError(
            RestApiError restApiError,
            Exception exception
    ) {
        Marker restApiErrorMarkers = append("error", restApiError);

        String exceptionName = exception.getClass().getCanonicalName();
        String exceptionMessage = exception.getMessage();

        if(HttpStatus.valueOf(restApiError.getStatus()).is4xxClientError()) {
            log.warn(restApiErrorMarkers, "Resolved [{} : {}]", exceptionName, exceptionMessage);
        } else {
            log.error(restApiErrorMarkers, "Resolved [{} : {}]", exceptionName, exceptionMessage);
        }
    }

    private RestApiError getRestApiErrorFrom(ResponseEntity<Object> responseEntity) {
        RestApiError restApiError;

        try {
            restApiError = (RestApiError) responseEntity.getBody();
        } catch(ClassCastException e) {
            throw new InvalidExceptionHandlerException(INVALID_EXCEPTION_HANDLER, e);
        }

        if(restApiError == null) {
            throw new InvalidExceptionHandlerException(INVALID_EXCEPTION_HANDLER);
        }

        return restApiError;
    }

    @SuppressWarnings("unchecked")
    private ResponseEntity<Object> getResponseEntityFrom(Object response) {
        ResponseEntity<Object> responseEntity;

        try {
            responseEntity = (ResponseEntity<Object>) response;
        } catch(ClassCastException e) {
            throw new InvalidExceptionHandlerException(INVALID_EXCEPTION_HANDLER, e);
        }

        return responseEntity;
    }

    private Exception getSourceExceptionFrom(Object[] arguments) {
        Exception sourceException;

        try {
            sourceException = (Exception) arguments[0];
        } catch(ClassCastException | ArrayIndexOutOfBoundsException e) {
            throw new InvalidExceptionHandlerException(INVALID_EXCEPTION_HANDLER, e);
        }

        return sourceException;
    }

    public static class InvalidExceptionHandlerException extends RuntimeException {

        InvalidExceptionHandlerException(String message) {
            super(message);
        }

        InvalidExceptionHandlerException(String message, Throwable cause) {
            super(message, cause);
        }

    }

}
