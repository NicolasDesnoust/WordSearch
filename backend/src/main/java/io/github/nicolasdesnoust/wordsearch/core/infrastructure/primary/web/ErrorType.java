package io.github.nicolasdesnoust.wordsearch.core.infrastructure.primary.web;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum ErrorType {

    INTERNAL_ERROR ("InternalError"),
    CONSTRAINT_VIOLATION ("ConstraintViolation"),
    MAX_UPLOAD_SIZE_EXCEEDED("MaxUploadSizeExceeded"),
    OPTICAL_CHARACTER_RECOGNITION_ERROR("OpticalCharacterRecognitionError"),
    UNSUPPORTED_FORMAT("UnsupportedFormat");

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
