package io.github.nicolasdesnoust.wordsearch.core.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.github.nicolasdesnoust.wordsearch.core.api.RestApiError.RestApiSubError;
import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final Environment env;

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<RestApiError> handleAllExceptions(
            Exception exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        RestApiError apiError = RestApiError.builder()
                .withStatus(status.value())
                .withType(ErrorType.INTERNAL_ERROR)
                .withTitle(env.getProperty("word-search.errors.internal-server-error.title"))
                .withDetail(env.getProperty("word-search.errors.internal-server-error.detail"))
                .withPath(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(apiError);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<RestApiError> handleConstraintViolationException(
            ConstraintViolationException exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<RestApiSubError> subErrors = exception.getConstraintViolations().stream()
                .map(RestApiValidationError::fromConstraintViolation)
                .collect(Collectors.toList());

        RestApiError apiError = RestApiError.builder()
                .withStatus(status.value())
                .withType(ErrorType.CONSTRAINT_VIOLATION)
                .withTitle(env.getProperty("word-search.errors.constraint-violation.title"))
                .withDetail(exception.getMessage())
                .withPath(request.getRequestURI())
                .withSubErrors(subErrors)
                .build();

        return ResponseEntity.status(status).body(apiError);
    }

}
