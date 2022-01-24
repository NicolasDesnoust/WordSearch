package io.github.nicolasdesnoust.wordsearch.solver.domain.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import io.github.nicolasdesnoust.wordsearch.solver.domain.Coordinates;
import io.github.nicolasdesnoust.wordsearch.solver.domain.Direction;
import io.github.nicolasdesnoust.wordsearch.solver.domain.Grid;
import io.github.nicolasdesnoust.wordsearch.solver.domain.GridLine;
import io.github.nicolasdesnoust.wordsearch.solver.util.ArrayUtil;

public class TopToBottomIterator implements Iterator<GridLine> {
    
    private final Grid grid;
    private int currentColumnIndex;

    public TopToBottomIterator(Grid grid) {
        this.grid = grid;
        this.currentColumnIndex = 0;
    }

    @Override
    public boolean hasNext() {
        return currentColumnIndex < grid.getLetters()[0].length;
    }

    @Override
    public GridLine next() {
        if(hasNext()) {
            String column = ArrayUtil.getColumn(grid.getLetters(), currentColumnIndex, ' ');
            Coordinates firstCellCoordinates = new Coordinates(currentColumnIndex, 0);

            currentColumnIndex++;

            return new GridLine(column, Direction.TOP_TO_BOTTOM, firstCellCoordinates);
        } else {
            throw new NoSuchElementException();
        }
    }

}
