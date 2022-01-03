package io.github.nicolasdesnoust.wordsearch.domain.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import io.github.nicolasdesnoust.wordsearch.domain.Coordinates;
import io.github.nicolasdesnoust.wordsearch.domain.Direction;
import io.github.nicolasdesnoust.wordsearch.domain.Grid;
import io.github.nicolasdesnoust.wordsearch.domain.GridLine;

public class LeftToRightIterator implements Iterator<GridLine> {
    
    private final Grid grid;
    private int currentRowIndex;

    public LeftToRightIterator(Grid grid) {
        this.grid = grid;
        this.currentRowIndex = 0;
    }

    @Override
    public boolean hasNext() {
        return currentRowIndex < grid.getLetters().length;
    }

    @Override
    public GridLine next() {
        if(hasNext()) {
            String row = String.valueOf(grid.getLetters()[currentRowIndex]);
            Coordinates firstCellCoordinates = new Coordinates(0, currentRowIndex);

            currentRowIndex++;

            return new GridLine(row, Direction.LEFT_TO_RIGHT, firstCellCoordinates);
        } else {
            throw new NoSuchElementException();
        }
    }

}
