package io.github.nicolasdesnoust.wordsearch.domain.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import io.github.nicolasdesnoust.wordsearch.domain.Grid;

public class BottomToTopIterator implements Iterator<String> {
    
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
    public String next() {        
        if(hasNext()) {
            return reverse(getColumn(grid.getLetters(), currentColumnIndex++, ' '));
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
