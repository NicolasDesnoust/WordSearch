package io.github.nicolasdesnoust.wordsearch.solver.domain;

import lombok.Value;

/**
 * A GridLine is a sequence of grid cells, in any possible direction.
 */
@Value
public class GridLine {
    private static final String WORD_MUST_BE_IN_LINE = "Word '%s' must be in line '%s'.";

    String letters;
    Direction direction;
    Coordinates firstCellCoordinates;

    public boolean contains(String word) {
        return letters.contains(word);
    }

    /**
     * Calculates the coordinates of a word that belongs to this line.
     * The coordinates are relative to the grid to which the line belong.
     */
    public Coordinates calculateCoordinatesOf(String word) {
        int wordIndex = letters.indexOf(word);
        if (wordIndex < 0) {
            throw new IllegalArgumentException(String.format(WORD_MUST_BE_IN_LINE, word, letters));
        }

        return wordIndexToCoordinates(wordIndex);
    }

    private Coordinates wordIndexToCoordinates(int wordIndex) {
        return new Coordinates(
                firstCellCoordinates.getX() + direction.getXMove() * wordIndex,
                firstCellCoordinates.getY() + direction.getYMove() * wordIndex
        );
    }

}
