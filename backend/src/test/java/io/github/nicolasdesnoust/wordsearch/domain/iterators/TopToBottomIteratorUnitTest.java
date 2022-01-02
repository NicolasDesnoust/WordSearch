package io.github.nicolasdesnoust.wordsearch.domain.iterators;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.github.nicolasdesnoust.wordsearch.domain.Grid;

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
                .containsExactly("AFIS", "UERD");
    }
}
