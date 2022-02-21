package io.github.nicolasdesnoust.wordsearch.solver.util;

import io.github.nicolasdesnoust.wordsearch.solver.domain.Coordinates;
import io.github.nicolasdesnoust.wordsearch.solver.domain.Direction;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

/**
 * Utility class that helps to create grids of different shapes.
 * It is mainly used to make test cases simpler and less sensitive to refactoring.
 */
@UtilityClass
public class GridFixtures {

    public DirectionBasedFixtures givenDirection(String direction) {
        return new DirectionBasedFixtures(Direction.valueOf(direction));
    }

    @RequiredArgsConstructor
    public static class DirectionBasedFixtures {

        private final Direction direction;

        public String aGridThatContainsOneWord() {
            return switch (direction) {
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
                    XBX
                    XOX
                    XLX
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

        public String getTheWordInsideTheGrid() {
            return "BOL";
        }

        public Coordinates getCoordinatesOfTheWordInsideTheGrid() {
            return switch (direction) {
                case LEFT_TO_RIGHT, TOP_LEFT_TO_BOTTOM_RIGHT -> new Coordinates(0, 0);
                case RIGHT_TO_LEFT, TOP_RIGHT_TO_BOTTOM_LEFT -> new Coordinates(2, 0);
                case TOP_TO_BOTTOM -> new Coordinates(1, 0);
                case BOTTOM_TO_TOP, BOTTOM_LEFT_TO_TOP_RIGHT -> new Coordinates(0, 2);
                case BOTTOM_RIGHT_TO_TOP_LEFT -> new Coordinates(2, 2);
            };
        }

    }

    public static String aValidGrid() {
        return """
                BOGJOEREH
                FERGDBMFO
                ADTJVBSJP
                """;
    }

    public enum InvalidGrids {
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
