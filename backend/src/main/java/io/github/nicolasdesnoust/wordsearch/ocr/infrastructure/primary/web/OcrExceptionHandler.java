package io.github.nicolasdesnoust.wordsearch.ocr.infrastructure.primary.web;

import io.github.nicolasdesnoust.wordsearch.core.infrastructure.primary.web.ErrorType;
import io.github.nicolasdesnoust.wordsearch.core.infrastructure.primary.web.LogRestApiError;
import io.github.nicolasdesnoust.wordsearch.core.infrastructure.primary.web.RestApiError;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.OpticalCharacterRecognition.OcrException;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.OpticalCharacterRecognitionImpl.UnsupportedFormatException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
class OcrExceptionHandler {

    private final Environment env;

    @LogRestApiError
    @ExceptionHandler(OcrException.class)
    public ResponseEntity<RestApiError> handleOcrException(
            OcrException exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        RestApiError apiError = RestApiError.builder()
                .withStatus(status.value())
                .withType(ErrorType.OPTICAL_CHARACTER_RECOGNITION_ERROR)
                .withTitle(env.getProperty("word-search.errors.optical-character-recognition-error.title"))
                .withDetail(env.getProperty("word-search.errors.optical-character-recognition-error.detail"))
                .withPath(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(apiError);
    }

    @LogRestApiError
    @ExceptionHandler(UnsupportedFormatException.class)
    public ResponseEntity<RestApiError> handleUnsupportedFormatException(
            UnsupportedFormatException exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        RestApiError apiError = RestApiError.builder()
                .withStatus(status.value())
                .withType(ErrorType.UNSUPPORTED_FORMAT)
                .withTitle(env.getProperty("word-search.errors.unsupported-format.title"))
                .withDetail(exception.getMessage())
                .withPath(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(apiError);
    }

}