package io.github.nicolasdesnoust.wordsearch.solver.domain.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import io.github.nicolasdesnoust.wordsearch.solver.domain.Coordinates;
import io.github.nicolasdesnoust.wordsearch.solver.domain.Direction;
import io.github.nicolasdesnoust.wordsearch.solver.domain.Grid;
import io.github.nicolasdesnoust.wordsearch.solver.domain.GridLine;
import io.github.nicolasdesnoust.wordsearch.solver.domain.util.ArrayUtil;
import io.github.nicolasdesnoust.wordsearch.solver.domain.util.StringUtil;

public class BottomToTopIterator implements Iterator<GridLine> {
    
    private final Grid grid;
    private int currentColumnIndex;

    public BottomToTopIterator(Grid grid) {
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
            String topToBottomColumn = ArrayUtil.getColumn(grid.getLetters(), currentColumnIndex, ' ');
            String bottomToTopColumn = StringUtil.reverse(topToBottomColumn);
            Coordinates firstCellCoordinates = new Coordinates(currentColumnIndex, grid.getHeight() - 1);

            currentColumnIndex++;

            return new GridLine(bottomToTopColumn, Direction.BOTTOM_TO_TOP, firstCellCoordinates);
        } else {
            throw new NoSuchElementException();
        }
    }

}
