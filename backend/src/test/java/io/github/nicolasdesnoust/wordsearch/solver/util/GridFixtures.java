package io.github.nicolasdesnoust.wordsearch.solver.util;

import io.github.nicolasdesnoust.wordsearch.solver.domain.Direction;
import lombok.experimental.UtilityClass;

/**
 * Utility class that helps to create grids of different shapes.
 * It is mainly used to make test cases simpler and less sensitive to refactoring.
 */
@UtilityClass
public class GridFixtures {

    public String getTheWordUsedInDirectionBasedFixtures() {
        return "BOL";
    }

    public String aGridWithOneWordInDirection(String direction) {
        return switch (Direction.valueOf(direction)) {
            case LEFT_TO_RIGHT -> """
                    BOL
                    XXX
                    XXX
                    """;
            case RIGHT_TO_LEFT -> """
                    LOB
                    XXX
                    XXX
                    """;
            case TOP_TO_BOTTOM -> """
                    BXX
                    OXX
                    LXX
                    """;
            case BOTTOM_TO_TOP -> """
                    LXX
                    OXX
                    BXX
                    """;
            case TOP_LEFT_TO_BOTTOM_RIGHT -> """
                    BXX
                    XOX
                    XXL
                    """;
            case BOTTOM_RIGHT_TO_TOP_LEFT -> """
                    LXX
                    XOX
                    XXB
                    """;
            case TOP_RIGHT_TO_BOTTOM_LEFT -> """
                    XXB
                    XOX
                    LXX
                    """;
            case BOTTOM_LEFT_TO_TOP_RIGHT -> """
                    XXL
                    XOX
                    BXX
                    """;
        };
    }

    public static String aValidGrid() {
        return """
                BOGJOEREH
                FERGDBMFO
                ADTJVBSJP
                """;
    }

    public enum InvalidGrid {
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
                return "A".repeat(MAXIMUM_GRID_SIZE + 1);
            }
        };

        private static final int MAXIMUM_GRID_SIZE = 5000;

        public abstract String build();
    }
}
