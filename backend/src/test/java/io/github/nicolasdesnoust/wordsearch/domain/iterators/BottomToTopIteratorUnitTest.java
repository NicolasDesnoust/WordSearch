package io.github.nicolasdesnoust.wordsearch.domain.iterators;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.github.nicolasdesnoust.wordsearch.domain.Grid;

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
                .containsExactly("SIFA", "DREU");
    }
}
