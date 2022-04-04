package io.github.nicolasdesnoust.wordsearch.core.infrastructure.primary.web;

import io.github.nicolasdesnoust.wordsearch.core.domain.StandardErrorMessage;
import io.github.nicolasdesnoust.wordsearch.core.domain.UserFriendlyErrorMessage;
import io.github.nicolasdesnoust.wordsearch.core.infrastructure.primary.web.RestApiError.RestApiSubError;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * A builder that can be used in {@link org.springframework.web.bind.annotation.ControllerAdvice @ControllerAdvice} classes
 * to simplify the construction of error responses.
 */
@Service
@Scope(SCOPE_PROTOTYPE)
public class ErrorResponseBuilder {

    private static final String MESSAGE_PREFIX = "word-search.errors.";
    private static final String DEFAULT_KEY = StandardErrorMessage.INTERNAL_SERVER_ERROR.getMessageKey();

    private final MessageSource messages;

    private int status;

    private UserFriendlyErrorMessage userFriendlyErrorMessage;

    private final List<String> messageArguments;

    private String path;

    private List<RestApiSubError> subErrors;

    public ErrorResponseBuilder(MessageSource messages) {
        this.messages = messages;

        this.userFriendlyErrorMessage = StandardErrorMessage.INTERNAL_SERVER_ERROR;
        this.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.messageArguments = new ArrayList<>();
    }

    public ErrorResponseBuilder withUserFriendlyErrorMessage(UserFriendlyErrorMessage userFriendlyErrorMessage) {
        this.userFriendlyErrorMessage = userFriendlyErrorMessage;
        return this;
    }

    public ErrorResponseBuilder withMessageArgument(String argument) {
        this.messageArguments.add(argument);
        return this;
    }

    public ErrorResponseBuilder withStatus(HttpStatus status) {
        this.status = status.value();
        return this;
    }

    public ErrorResponseBuilder withPath(String path) {
        this.path = path;
        return this;
    }

    public ErrorResponseBuilder withSubErrors(List<RestApiSubError> subErrors) {
        this.subErrors = subErrors;
        return this;
    }

    public ResponseEntity<RestApiError> buildErrorResponse() {
        String type;
        String message;

        Locale locale = LocaleContextHolder.getLocale();
        try {
            type = userFriendlyErrorMessage.getMessageKey();
            System.err.println(Arrays.toString(messageArguments.toArray()));
            message = messages.getMessage(MESSAGE_PREFIX + type, messageArguments.toArray(), locale);
            System.err.println(message);

        } catch (NoSuchMessageException e) {
            type = DEFAULT_KEY;
            message = messages.getMessage(MESSAGE_PREFIX + DEFAULT_KEY, null, locale);
            this.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        }

        RestApiError apiError = RestApiError.builder()
                .withStatus(status)
                .withType(type)
                .withMessage(message)
                .withPath(path)
                .withSubErrors(subErrors)
                .build();

        return ResponseEntity.status(status).body(apiError);
    }
}
