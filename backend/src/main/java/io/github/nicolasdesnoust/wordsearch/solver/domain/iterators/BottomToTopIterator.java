package io.github.nicolasdesnoust.wordsearch.solver.domain.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import io.github.nicolasdesnoust.wordsearch.solver.domain.Coordinates;
import io.github.nicolasdesnoust.wordsearch.solver.domain.Direction;
import io.github.nicolasdesnoust.wordsearch.solver.domain.Grid;
import io.github.nicolasdesnoust.wordsearch.solver.domain.GridLine;

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
            String column = reverse(getColumn(grid.getLetters(), currentColumnIndex, ' '));
            Coordinates firstCellCoordinates = new Coordinates(currentColumnIndex, grid.getHeight() - 1);

            currentColumnIndex++;

            return new GridLine(column, Direction.BOTTOM_TO_TOP, firstCellCoordinates);
        } else {
            throw new NoSuchElementException();
        }
    }

    private String reverse(String stringToReverse) {
        return new StringBuilder(stringToReverse)
                .reverse()
                .toString();
    }

    private String getColumn(char[][] matrix, int column, int defaultVal) {
        var ints = IntStream.range(0, matrix.length)
                .map(i -> matrix[i].length < column ? defaultVal : matrix[i][column])
                .toArray();
        String res = "";
        for (int i : ints) {
            res += (char) i;
        }
        return res;
    }

}
