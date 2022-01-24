package io.github.nicolasdesnoust.wordsearch.solver.domain.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import io.github.nicolasdesnoust.wordsearch.solver.domain.Coordinates;
import io.github.nicolasdesnoust.wordsearch.solver.domain.Direction;
import io.github.nicolasdesnoust.wordsearch.solver.domain.Grid;
import io.github.nicolasdesnoust.wordsearch.solver.domain.GridLine;

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
            StringBuilder diagonalBuilder = new StringBuilder();

            for (int x = Math.max(0, grid.getWidth() - 1 - diagonalIndex); x < grid.getWidth(); x++) {
                int y = diagonalIndex + x - grid.getWidth() + 1;
                
                if (y < grid.getHeight() && x < grid.getWidth()) {
                    char currentLetter = grid.getLetters()[y][x];
                    diagonalBuilder.append(currentLetter);
                }
            }

            Coordinates firstCellCoordinates = new Coordinates(
                    diagonalIndex < grid.getWidth() ? grid.getWidth() - 1 - diagonalIndex : 0,
                    diagonalIndex < grid.getWidth() ? 0 : diagonalIndex - grid.getWidth() + 1
            );

            diagonalIndex++;

            return new GridLine(diagonalBuilder.toString(), Direction.TOP_LEFT_TO_BOTTOM_RIGHT, firstCellCoordinates);
        } else {
            throw new NoSuchElementException();
        }
    }

}
