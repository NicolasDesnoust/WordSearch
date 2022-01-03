package io.github.nicolasdesnoust.wordsearch.domain.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import io.github.nicolasdesnoust.wordsearch.domain.Coordinates;
import io.github.nicolasdesnoust.wordsearch.domain.Direction;
import io.github.nicolasdesnoust.wordsearch.domain.Grid;
import io.github.nicolasdesnoust.wordsearch.domain.GridLine;

public class RightToLeftIterator implements Iterator<GridLine> {

    private final Grid grid;
    private int currentRowIndex;

    public RightToLeftIterator(Grid grid) {
        this.grid = grid;
        this.currentRowIndex = 0;
    }

    @Override
    public boolean hasNext() {
        return currentRowIndex < grid.getLetters().length;
    }

    @Override
    public GridLine next() {
        if (hasNext()) {
            String row = reverse(String.valueOf(grid.getLetters()[currentRowIndex]));
            Coordinates firstCellCoordinates = new Coordinates(grid.getWidth() - 1, currentRowIndex);

            currentRowIndex++;

            return new GridLine(row, Direction.RIGHT_TO_LEFT, firstCellCoordinates);
        } else {
            throw new NoSuchElementException();
        }
    }

    private String reverse(String stringToReverse) {
        return new StringBuilder(stringToReverse)
                .reverse()
                .toString();
    }

}
