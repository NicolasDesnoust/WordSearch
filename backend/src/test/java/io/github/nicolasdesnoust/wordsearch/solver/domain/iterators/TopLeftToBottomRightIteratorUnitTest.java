package io.github.nicolasdesnoust.wordsearch.solver.domain.iterators;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.github.nicolasdesnoust.wordsearch.solver.domain.Coordinates;
import io.github.nicolasdesnoust.wordsearch.solver.domain.Direction;
import io.github.nicolasdesnoust.wordsearch.solver.domain.Grid;
import io.github.nicolasdesnoust.wordsearch.solver.domain.GridLine;

class TopLeftToBottomRightIteratorUnitTest {

    Grid grid = new Grid(new char[][] {
            { 'A', 'U', 'E' },
            { 'F', 'E', 'Z' },
            { 'I', 'R', 'T' },
    });

    @Test
    void givenSimpleGrid_whenUseIterator_thenSuccess() {
        var underTest = new TopLeftToBottomRightIterator(grid);

        assertThat(underTest).toIterable()
        .containsExactly(
            new GridLine("E", Direction.TOP_LEFT_TO_BOTTOM_RIGHT, new Coordinates(2, 0)),
            new GridLine("UZ", Direction.TOP_LEFT_TO_BOTTOM_RIGHT, new Coordinates(1, 0)),
            new GridLine("AET", Direction.TOP_LEFT_TO_BOTTOM_RIGHT, new Coordinates(0, 0)),
            new GridLine("FR", Direction.TOP_LEFT_TO_BOTTOM_RIGHT, new Coordinates(0, 1))
        );
    }
}
