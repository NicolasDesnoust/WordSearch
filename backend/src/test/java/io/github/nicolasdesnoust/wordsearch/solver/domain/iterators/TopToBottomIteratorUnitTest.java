package io.github.nicolasdesnoust.wordsearch.solver.domain.iterators;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.github.nicolasdesnoust.wordsearch.solver.domain.Coordinates;
import io.github.nicolasdesnoust.wordsearch.solver.domain.Direction;
import io.github.nicolasdesnoust.wordsearch.solver.domain.Grid;
import io.github.nicolasdesnoust.wordsearch.solver.domain.GridLine;

class TopToBottomIteratorUnitTest {

    Grid grid = new Grid(new char[][] {
            { 'A', 'U', },
            { 'F', 'E', },
            { 'I', 'R', },
            { 'S', 'D', }
    });

    @Test
    void givenSimpleGrid_whenUseIterator_thenSuccess() {
        var underTest = new TopToBottomIterator(grid);

        assertThat(underTest).toIterable()
        .containsExactly(
                new GridLine("AFIS", Direction.TOP_TO_BOTTOM, new Coordinates(0, 0)),
                new GridLine("UERD", Direction.TOP_TO_BOTTOM, new Coordinates(1, 0))
        );
    }
}
