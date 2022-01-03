package io.github.nicolasdesnoust.wordsearch.domain.iterators;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.github.nicolasdesnoust.wordsearch.domain.Coordinates;
import io.github.nicolasdesnoust.wordsearch.domain.Direction;
import io.github.nicolasdesnoust.wordsearch.domain.Grid;
import io.github.nicolasdesnoust.wordsearch.domain.GridLine;

public class BottomToTopIteratorUnitTest {

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
