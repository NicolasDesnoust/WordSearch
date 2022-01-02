package io.github.nicolasdesnoust.wordsearch.domain.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import io.github.nicolasdesnoust.wordsearch.domain.Grid;

public class TopToBottomIterator implements Iterator<String> {
    
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
    public String next() {        
        if(hasNext()) {
            return getColumn(grid.getLetters(), currentColumnIndex++);
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
