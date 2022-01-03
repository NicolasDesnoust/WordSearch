package io.github.nicolasdesnoust.wordsearch.domain;

import lombok.Value;

/**
 * A GridLine is a sequence of grid cells, in any possible direction.
 */
@Value
public class GridLine {
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

        return wordIndexToCoordinates(wordIndex);
    }

    private Coordinates wordIndexToCoordinates(int wordIndex) {
        if (wordIndex < 0 || wordIndex >= letters.length()) {
            throw new IllegalArgumentException("Parameter 'wordIndex' must be between 0 inclusive and " + letters.length() + " exclusive.");
        }

        return new Coordinates(
                firstCellCoordinates.getX() + direction.getXMove() * wordIndex,
                firstCellCoordinates.getY() + direction.getYMove() * wordIndex
        );
    }

}
