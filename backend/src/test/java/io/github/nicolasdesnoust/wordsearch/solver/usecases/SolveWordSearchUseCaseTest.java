package io.github.nicolasdesnoust.wordsearch.solver.usecases;

import io.github.nicolasdesnoust.wordsearch.solver.domain.GridFactory;
import io.github.nicolasdesnoust.wordsearch.solver.domain.NaiveWordFinder;
import io.github.nicolasdesnoust.wordsearch.solver.domain.WordFinder;
import io.github.nicolasdesnoust.wordsearch.solver.domain.WordsFactory;
import io.github.nicolasdesnoust.wordsearch.solver.usecases.SolveWordSearchUseCase.SolveWordSearchRequest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class SolveWordSearchUseCaseTest {

    GridFactory gridFactory = new GridFactory();
    WordsFactory wordsFactory = new WordsFactory();
    WordFinder wordFinder = new NaiveWordFinder();
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    SolveWordSearchUseCase underTest = new SolveWordSearchUseCase(
            gridFactory,
            wordsFactory,
            wordFinder,
            validator
    );

    static Stream<String> invalidGrids() {
        int maximumGridSize = 5000;

        return Stream.of("", "   ", null, "A".repeat(maximumGridSize + 1));
    }

    static Stream<String> invalidWords() {
        int maximumWordsSize = 5000;

        return Stream.of("", "   ", null, "A".repeat(maximumWordsSize + 1));
    }

    @ParameterizedTest
    @MethodSource("invalidGrids")
    void givenInvalidGrid_whenSolveWordSearch_thenThrowMeaningfulConstraintViolationException(String invalidGrid) {
        var request = SolveWordSearchRequest.builder()
                .withGrid(invalidGrid)
                .withWords("JOUR NUIT")
                .build();

        assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> underTest.solveWordSearch(request))
                .withMessageContaining("grid");
    }

    @ParameterizedTest
    @MethodSource("invalidWords")
    void givenInvalidWords_whenSolveWordSearch_thenThrowMeaningfulConstraintViolationException(String invalidWords) {
        var request = SolveWordSearchRequest.builder()
                .withGrid("BONJOURED/n" +
                        "ALLODSOFE")
                .withWords(invalidWords)
                .build();

        assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> underTest.solveWordSearch(request))
                .withMessageContaining("words");
    }

}