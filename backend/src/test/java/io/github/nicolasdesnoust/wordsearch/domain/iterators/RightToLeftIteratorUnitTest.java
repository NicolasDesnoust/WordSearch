package io.github.nicolasdesnoust.wordsearch.domain.iterators;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.github.nicolasdesnoust.wordsearch.domain.Grid;

class RightToLeftIteratorUnitTest {

    Grid grid = new Grid(new char[][] {
            { 'A', 'P', 'E', 'X' },
            { 'F', 'E', 'Z', 'R' }
    });

    @Test
    void givenSimpleGrid_whenUseIterator_thenSuccess() {
        var underTest = new RightToLeftIterator(grid);

        assertThat(underTest).toIterable()
                .containsExactly("XEPA", "RZEF");
    }
}
