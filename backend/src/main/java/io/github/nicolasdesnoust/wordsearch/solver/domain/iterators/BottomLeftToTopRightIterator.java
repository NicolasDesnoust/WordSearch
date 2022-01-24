package io.github.nicolasdesnoust.wordsearch.solver.domain.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import io.github.nicolasdesnoust.wordsearch.solver.domain.Coordinates;
import io.github.nicolasdesnoust.wordsearch.solver.domain.Direction;
import io.github.nicolasdesnoust.wordsearch.solver.domain.Grid;
import io.github.nicolasdesnoust.wordsearch.solver.domain.GridLine;

public class BottomLeftToTopRightIterator implements Iterator<GridLine> {

    private final Grid grid;
    private int diagonalIndex;

    public BottomLeftToTopRightIterator(Grid grid) {
        this.grid = grid;
        this.diagonalIndex = 0;
    }

    @Override
    public boolean hasNext() {
        return diagonalIndex < grid.getWidth() + grid.getHeight() - 2;
    }

    @Override
    public GridLine next() {
        if (hasNext()) {
            StringBuilder diagonalBuilder = new StringBuilder();
            
            for (int j = 0; j <= diagonalIndex; j++) {
                int i = diagonalIndex - j;
                
                if (i < grid.getHeight() && j < grid.getWidth()) {
                    char currentLetter = grid.getLetters()[i][j];
                    diagonalBuilder.append(currentLetter);
                }
            }

            Coordinates firstCellCoordinates = new Coordinates(
                    diagonalIndex < grid.getHeight() ? 0 : diagonalIndex - grid.getHeight() + 1,
                    diagonalIndex < grid.getHeight() ? diagonalIndex : grid.getHeight() - 1
            );

            diagonalIndex++;

            return new GridLine(diagonalBuilder.toString(), Direction.BOTTOM_LEFT_TO_TOP_RIGHT, firstCellCoordinates);
        } else {
            throw new NoSuchElementException();
        }
    }

}
