package io.github.nicolasdesnoust.wordsearch.core.domain;

import lombok.Getter;

@Getter
public class WordSearchException extends RuntimeException {

    private final UserFriendlyErrorMessage userFriendlyMessage;

    public WordSearchException(String debugMessage, UserFriendlyErrorMessage userFriendlyMessage) {
        super(debugMessage);
        this.userFriendlyMessage = userFriendlyMessage;
    }

    public WordSearchException(String debugMessage, UserFriendlyErrorMessage userFriendlyMessage, Throwable cause) {
        super(debugMessage, cause);
        this.userFriendlyMessage = userFriendlyMessage;
    }

}
