package io.github.nicolasdesnoust.wordsearch.domain.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import io.github.nicolasdesnoust.wordsearch.domain.Coordinates;
import io.github.nicolasdesnoust.wordsearch.domain.Direction;
import io.github.nicolasdesnoust.wordsearch.domain.Grid;
import io.github.nicolasdesnoust.wordsearch.domain.GridLine;

public class TopLeftToBottomRightIterator implements Iterator<GridLine> {
    
    private final Grid grid;
    private int diagonalIndex;

    public TopLeftToBottomRightIterator(Grid grid) {
        this.grid = grid;
        this.diagonalIndex = 0;
    }

    @Override
    public boolean hasNext() {
        return diagonalIndex < grid.getWidth() + grid.getHeight() - 2;
    }

    @Override
    public GridLine next() {
        if(hasNext()) {
            String diagonal = "";
            for (int j = Math.max(0, grid.getWidth() - 1 - diagonalIndex); j < grid.getWidth(); j++) {
                int i = diagonalIndex + j - grid.getWidth() + 1;
                
                if (i < grid.getHeight() && j < grid.getWidth()) {
                    diagonal += grid.getLetters()[i][j];
                }
            }

            Coordinates firstCellCoordinates = new Coordinates(
                    diagonalIndex < grid.getWidth() ? grid.getWidth() - 1 - diagonalIndex : 0,
                    diagonalIndex < grid.getWidth() ? 0 : diagonalIndex - grid.getWidth() + 1
            );

            diagonalIndex++;

            return new GridLine(diagonal, Direction.TOP_LEFT_TO_BOTTOM_RIGHT, firstCellCoordinates);
        } else {
            throw new NoSuchElementException();
        }
    }

}
