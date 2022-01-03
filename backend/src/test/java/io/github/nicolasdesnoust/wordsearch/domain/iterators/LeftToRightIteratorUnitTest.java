package io.github.nicolasdesnoust.wordsearch.domain.iterators;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.nicolasdesnoust.wordsearch.domain.Coordinates;
import io.github.nicolasdesnoust.wordsearch.domain.Direction;
import io.github.nicolasdesnoust.wordsearch.domain.GridLine;
import org.junit.jupiter.api.Test;

import io.github.nicolasdesnoust.wordsearch.domain.Grid;

class LeftToRightIteratorUnitTest {

    Grid grid = new Grid(new char[][] {
            { 'A', 'P', 'E', 'X' },
            { 'F', 'E', 'Z', 'R' }
    });

    @Test
    void givenSimpleGrid_whenUseIterator_thenSuccess() {
        var underTest = new LeftToRightIterator(grid);

        assertThat(underTest).toIterable()
        .containsExactly(
                new GridLine("APEX", Direction.LEFT_TO_RIGHT, new Coordinates(0, 0)),
                new GridLine("FEZR", Direction.LEFT_TO_RIGHT, new Coordinates(0, 1))
        );
    }
}
