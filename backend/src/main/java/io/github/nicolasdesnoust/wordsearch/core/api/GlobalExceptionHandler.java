package io.github.nicolasdesnoust.wordsearch.core.api;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

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

}
