package io.github.nicolasdesnoust.wordsearch.core.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum StandardErrorMessage implements UserFriendlyErrorMessage {

    INTERNAL_SERVER_ERROR ("server.internal-server-error"),
    CONSTRAINT_VIOLATION ("user.constraint-violation"),
    MAX_UPLOAD_SIZE_EXCEEDED("user.max-upload-size-exceeded"),
    UNSUPPORTED_FORMAT("user.unsupported-format");

    @Getter
    private final String messageKey;
}
