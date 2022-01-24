package io.github.nicolasdesnoust.wordsearch.solver.domain.iterators;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.github.nicolasdesnoust.wordsearch.solver.domain.Coordinates;
import io.github.nicolasdesnoust.wordsearch.solver.domain.Direction;
import io.github.nicolasdesnoust.wordsearch.solver.domain.Grid;
import io.github.nicolasdesnoust.wordsearch.solver.domain.GridLine;

class BottomToTopIteratorUnitTest {

    Grid grid = new Grid(new char[][] {
            { 'A', 'U', },
            { 'F', 'E', },
            { 'I', 'R', },
            { 'S', 'D', }
    });

    @Test
    void givenSimpleGrid_whenUseIterator_thenSuccess() {
        var underTest = new BottomToTopIterator(grid);

        assertThat(underTest).toIterable()
        .containsExactly(
                new GridLine("SIFA", Direction.BOTTOM_TO_TOP, new Coordinates(0, 3)),
                new GridLine("DREU", Direction.BOTTOM_TO_TOP, new Coordinates(1, 3))
        );
    }
}
