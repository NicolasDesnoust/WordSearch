package io.github.nicolasdesnoust.wordsearch.domain.iterators;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.github.nicolasdesnoust.wordsearch.domain.Grid;

public class BottomLeftToTopRightIteratorUnitTest {

    Grid grid = new Grid(new char[][] {
            { 'A', 'U', 'E' },
            { 'F', 'E', 'Z' },
            { 'I', 'R', 'T' },
    });

    @Test
    void givenSimpleGrid_whenUseIterator_thenSuccess() {
        var underTest = new BottomLeftToTopRightIterator(grid);

        assertThat(underTest).toIterable()
                .containsExactly("A", "FU", "IEE", "RZ");
    }
}
