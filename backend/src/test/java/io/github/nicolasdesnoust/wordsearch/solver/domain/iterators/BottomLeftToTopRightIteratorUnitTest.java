package io.github.nicolasdesnoust.wordsearch.solver.domain.iterators;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.github.nicolasdesnoust.wordsearch.solver.domain.Coordinates;
import io.github.nicolasdesnoust.wordsearch.solver.domain.Direction;
import io.github.nicolasdesnoust.wordsearch.solver.domain.Grid;
import io.github.nicolasdesnoust.wordsearch.solver.domain.GridLine;

class BottomLeftToTopRightIteratorUnitTest {

    Grid grid = new Grid(new char[][] {
            { 'A', 'U', 'E' },
            { 'F', 'E', 'Z' },
            { 'I', 'R', 'T' },
    });

    @Test
    void givenSimpleGrid_whenUseIterator_thenSuccess() {
        var underTest = new BottomLeftToTopRightIterator(grid);

        assertThat(underTest).toIterable()
        .containsExactly(
                new GridLine("A", Direction.BOTTOM_LEFT_TO_TOP_RIGHT, new Coordinates(0, 0)),
                new GridLine("FU", Direction.BOTTOM_LEFT_TO_TOP_RIGHT, new Coordinates(0, 1)),
                new GridLine("IEE", Direction.BOTTOM_LEFT_TO_TOP_RIGHT, new Coordinates(0, 2)),
                new GridLine("RZ", Direction.BOTTOM_LEFT_TO_TOP_RIGHT, new Coordinates(1, 2))
        );
    }
}
