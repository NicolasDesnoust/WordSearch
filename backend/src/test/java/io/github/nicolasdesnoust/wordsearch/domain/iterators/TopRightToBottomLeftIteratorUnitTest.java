package io.github.nicolasdesnoust.wordsearch.domain.iterators;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.github.nicolasdesnoust.wordsearch.domain.Coordinates;
import io.github.nicolasdesnoust.wordsearch.domain.Direction;
import io.github.nicolasdesnoust.wordsearch.domain.Grid;
import io.github.nicolasdesnoust.wordsearch.domain.GridLine;

class TopRightToBottomLeftIteratorUnitTest {

    Grid grid = new Grid(new char[][] {
            { 'A', 'U', 'E' },
            { 'F', 'E', 'Z' },
            { 'I', 'R', 'T' },
    });

    @Test
    void givenSimpleGrid_whenUseIterator_thenSuccess() {
        var underTest = new TopRightToBottomLeftIterator(grid);

        assertThat(underTest).toIterable()
        .containsExactly(
            new GridLine("A", Direction.TOP_RIGHT_TO_BOTTOM_LEFT, new Coordinates(0, 0)),
            new GridLine("UF", Direction.TOP_RIGHT_TO_BOTTOM_LEFT, new Coordinates(1, 0)),
            new GridLine("EEI", Direction.TOP_RIGHT_TO_BOTTOM_LEFT, new Coordinates(2, 0)),
            new GridLine("ZR", Direction.TOP_RIGHT_TO_BOTTOM_LEFT, new Coordinates(2, 1))
        );
    }
}
