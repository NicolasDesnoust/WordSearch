package io.github.nicolasdesnoust.wordsearch.core.infrastructure.primary.web;

import io.github.nicolasdesnoust.wordsearch.core.domain.StandardErrorMessage;
import io.github.nicolasdesnoust.wordsearch.core.infrastructure.primary.web.RestApiError.RestApiSubError;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ObjectFactory<ErrorResponseBuilder> errorResponseBuilderFactory;
    private final Environment env;

    @LogRestApiError
    @ExceptionHandler
    public ResponseEntity<RestApiError> handleAllExceptions(
            Exception exception,
            HttpServletRequest request
    ) {
        return errorResponseBuilderFactory.getObject()
                .withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .withUserFriendlyErrorMessage(StandardErrorMessage.INTERNAL_SERVER_ERROR)
                .withPath(request.getRequestURI())
                .buildErrorResponse();
    }

    @LogRestApiError
    @ExceptionHandler
    public ResponseEntity<RestApiError> handleConstraintViolationException(
            ConstraintViolationException exception,
            HttpServletRequest request
    ) {
        List<RestApiSubError> subErrors = exception.getConstraintViolations().stream()
                .map(RestApiValidationError::fromConstraintViolation)
                .collect(Collectors.toList());

        return errorResponseBuilderFactory.getObject()
                .withStatus(HttpStatus.BAD_REQUEST)
                .withUserFriendlyErrorMessage(StandardErrorMessage.CONSTRAINT_VIOLATION)
                .withPath(request.getRequestURI())
                .withSubErrors(subErrors)
                .buildErrorResponse();
    }

    @LogRestApiError
    @ExceptionHandler
    public ResponseEntity<RestApiError> handleMaxUploadSizeExceededException(
            MaxUploadSizeExceededException exception,
            HttpServletRequest request
    ) {
        String fileSize = toHumanReadableFileSize(request.getHeader(HttpHeaders.CONTENT_LENGTH));
        String maxRequestSize = toHumanReadableFileSize(env.getProperty("spring.servlet.multipart.max-request-size"));

        return errorResponseBuilderFactory.getObject()
                .withStatus(HttpStatus.BAD_REQUEST)
                .withUserFriendlyErrorMessage(StandardErrorMessage.MAX_UPLOAD_SIZE_EXCEEDED)
                .withMessageArgument(fileSize)
                .withMessageArgument(maxRequestSize)
                .withPath(request.getRequestURI())
                .buildErrorResponse();
    }

    private String toHumanReadableFileSize(String rawFileSize) {
        if(rawFileSize == null) {
            return "unknown";
        }

        return FileUtils.byteCountToDisplaySize(Long.parseLong(rawFileSize));
    }

}
