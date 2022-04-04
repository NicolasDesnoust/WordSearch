package io.github.nicolasdesnoust.wordsearch.core.domain;

import java.io.Serializable;

/**
 * Represents a message that can be shown as is 
 * to the end user or at least to the API user.
 */
@FunctionalInterface
public interface UserFriendlyErrorMessage extends Serializable {
    String getMessageKey();
}
