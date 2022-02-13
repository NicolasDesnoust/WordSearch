package io.github.nicolasdesnoust.wordsearch.solver.util;

import lombok.experimental.UtilityClass;

/**
 * Utility class that helps to create word lists of different shapes.
 * It is mainly used to make test cases simpler and less sensitive to refactoring.
 */
@UtilityClass
public class WordFixtures {

    public String someValidWords() {
        return "BONJOUR BONSOIR";
    }

    public enum InvalidWords {
        EMPTY {
            @Override
            public String build() {
                return "";
            }
        },
        BLANK {
            @Override
            public String build() {
                return "   ";
            }
        },
        NULL {
            @Override
            public String build() {
                return null;
            }
        },
        TOO_LARGE {
            @Override
            public String build() {
                return "A".repeat(MAXIMUM_WORDS_SIZE + 1);
            }
        };

        private static final int MAXIMUM_WORDS_SIZE = 5000;

        public abstract String build();
    }
}
