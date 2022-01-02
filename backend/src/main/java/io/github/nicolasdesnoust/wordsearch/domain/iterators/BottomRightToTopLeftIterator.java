package io.github.nicolasdesnoust.wordsearch.domain.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import io.github.nicolasdesnoust.wordsearch.domain.Grid;

public class BottomRightToTopLeftIterator implements Iterator<String> {

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
    public String next() {
        if (hasNext()) {
            String currentline = "";
            for (int j = grid.getWidth() - 1; j >= grid.getWidth() - 1 - diagonalIndex; j--) {
                int i = diagonalIndex + j - grid.getWidth() + 1;

                if (i < grid.getHeight() && j >= 0) {
                    currentline += grid.getLetters()[i][j];
                }
            }
            diagonalIndex++;

            return currentline;
        } else {
            throw new NoSuchElementException();
        }
    }

}
