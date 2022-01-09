package io.github.nicolasdesnoust.wordsearch.core.api;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum ErrorType {

    INTERNAL_ERROR ("InternalError");

    @Getter
    @JsonValue
    private final String value;

    ErrorType(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
