package io.github.nicolasdesnoust.wordsearch.ocr.domain;

import io.github.nicolasdesnoust.wordsearch.core.domain.UserFriendlyErrorMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OcrErrorMessage implements UserFriendlyErrorMessage {

    OPTICAL_CHARACTER_RECOGNITION_ERROR("ocr.optical-character-recognition-error");

    @Getter
    private final String messageKey;
}