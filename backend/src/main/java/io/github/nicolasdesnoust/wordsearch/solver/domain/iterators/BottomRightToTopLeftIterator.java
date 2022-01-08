package io.github.nicolasdesnoust.wordsearch.solver.domain.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import io.github.nicolasdesnoust.wordsearch.solver.domain.Coordinates;
import io.github.nicolasdesnoust.wordsearch.solver.domain.Direction;
import io.github.nicolasdesnoust.wordsearch.solver.domain.Grid;
import io.github.nicolasdesnoust.wordsearch.solver.domain.GridLine;

public class BottomRightToTopLeftIterator implements Iterator<GridLine> {

    private final Grid grid;
    private int diagonalIndex;

    public BottomRightToTopLeftIterator(Grid grid) {
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
            String diagonal = "";
            for (int j = grid.getWidth() - 1; j >= grid.getWidth() - 1 - diagonalIndex; j--) {
                int i = diagonalIndex + j - grid.getWidth() + 1;

                if (i < grid.getHeight() && j >= 0) {
                    diagonal += grid.getLetters()[i][j];
                }
            }

            Coordinates firstCellCoordinates = new Coordinates(
                    diagonalIndex < grid.getHeight() ? grid.getWidth() - 1
                    : grid.getWidth() - 1 - (diagonalIndex - grid.getHeight()) - 1,
                    diagonalIndex < grid.getHeight() ? diagonalIndex : grid.getHeight() - 1
            );

            diagonalIndex++;

            return new GridLine(diagonal, Direction.BOTTOM_RIGHT_TO_TOP_LEFT, firstCellCoordinates);
        } else {
            throw new NoSuchElementException();
        }
    }

}
