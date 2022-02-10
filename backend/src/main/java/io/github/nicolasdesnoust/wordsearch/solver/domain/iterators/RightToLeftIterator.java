package io.github.nicolasdesnoust.wordsearch.solver.domain.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import io.github.nicolasdesnoust.wordsearch.solver.domain.Coordinates;
import io.github.nicolasdesnoust.wordsearch.solver.domain.Direction;
import io.github.nicolasdesnoust.wordsearch.solver.domain.Grid;
import io.github.nicolasdesnoust.wordsearch.solver.domain.GridLine;
import io.github.nicolasdesnoust.wordsearch.solver.domain.util.StringUtil;

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
            String leftToRightRow = String.valueOf(grid.getLetters()[currentRowIndex]);
            String rigthToLeftRow = StringUtil.reverse(leftToRightRow);
            Coordinates firstCellCoordinates = new Coordinates(grid.getWidth() - 1, currentRowIndex);

            currentRowIndex++;

            return new GridLine(rigthToLeftRow, Direction.RIGHT_TO_LEFT, firstCellCoordinates);
        } else {
            throw new NoSuchElementException();
        }
    }

}
