package io.github.nicolasdesnoust.wordsearch.ocr.infrastructure.primary.web;

import io.github.nicolasdesnoust.wordsearch.core.infrastructure.primary.web.ErrorResponseBuilder;
import io.github.nicolasdesnoust.wordsearch.core.infrastructure.primary.web.LogRestApiError;
import io.github.nicolasdesnoust.wordsearch.core.infrastructure.primary.web.RestApiError;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.OpticalCharacterRecognition.OcrException;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.OpticalCharacterRecognitionImpl.UnsupportedFormatException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
class OcrExceptionHandler {

    private final ObjectFactory<ErrorResponseBuilder> errorResponseBuilderFactory;

    @LogRestApiError
    @ExceptionHandler
    public ResponseEntity<RestApiError> handleOcrException(
            OcrException exception,
            HttpServletRequest request
    ) {
        return errorResponseBuilderFactory.getObject()
                .withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .withUserFriendlyErrorMessage(exception.getUserFriendlyMessage())
                .withPath(request.getRequestURI())
                .buildErrorResponse();
    }

    @LogRestApiError
    @ExceptionHandler
    public ResponseEntity<RestApiError> handleUnsupportedFormatException(
            UnsupportedFormatException exception,
            HttpServletRequest request
    ) {
        return errorResponseBuilderFactory.getObject()
                .withStatus(HttpStatus.BAD_REQUEST)
                .withUserFriendlyErrorMessage(exception.getUserFriendlyMessage())
                .withMessageArgument(exception.getUnsupportedFormat())
                .withPath(request.getRequestURI())
                .buildErrorResponse();
    }

}