package io.github.nicolasdesnoust.wordsearch.domain.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import io.github.nicolasdesnoust.wordsearch.domain.Coordinates;
import io.github.nicolasdesnoust.wordsearch.domain.Direction;
import io.github.nicolasdesnoust.wordsearch.domain.Grid;
import io.github.nicolasdesnoust.wordsearch.domain.GridLine;

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
            String column = getColumn(grid.getLetters(), currentColumnIndex);
            Coordinates firstCellCoordinates = new Coordinates(currentColumnIndex, 0);

            currentColumnIndex++;

            return new GridLine(column, Direction.TOP_TO_BOTTOM, firstCellCoordinates);
        } else {
            throw new NoSuchElementException();
        }
    }

    String getColumn(char[][] matrix, int column) {
        var ints = IntStream.range(0, matrix.length)
                .map(i -> matrix[i].length < column ? ' ' : matrix[i][column])
                .toArray();
        String res = "";
        for (int i : ints) {
            res += (char) i;
        }
        return res;
    }

}
