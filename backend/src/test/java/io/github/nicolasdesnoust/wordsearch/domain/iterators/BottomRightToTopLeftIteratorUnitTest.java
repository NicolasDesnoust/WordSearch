package io.github.nicolasdesnoust.wordsearch.domain.iterators;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.github.nicolasdesnoust.wordsearch.domain.Coordinates;
import io.github.nicolasdesnoust.wordsearch.domain.Direction;
import io.github.nicolasdesnoust.wordsearch.domain.Grid;
import io.github.nicolasdesnoust.wordsearch.domain.GridLine;

class BottomRightToTopLeftIteratorUnitTest {

    Grid grid = new Grid(new char[][] {
            { 'A', 'U', 'E' },
            { 'F', 'E', 'Z' },
            { 'I', 'R', 'T' },
    });

    @Test
    void givenSimpleGrid_whenUseIterator_thenSuccess() {
        var underTest = new BottomRightToTopLeftIterator(grid);

        assertThat(underTest).toIterable()
        .containsExactly(
            new GridLine("E", Direction.BOTTOM_RIGHT_TO_TOP_LEFT, new Coordinates(2, 0)),
            new GridLine("ZU", Direction.BOTTOM_RIGHT_TO_TOP_LEFT, new Coordinates(2, 1)),
            new GridLine("TEA", Direction.BOTTOM_RIGHT_TO_TOP_LEFT, new Coordinates(2, 2)),
            new GridLine("RF", Direction.BOTTOM_RIGHT_TO_TOP_LEFT, new Coordinates(1, 2))
        );
    }
}
