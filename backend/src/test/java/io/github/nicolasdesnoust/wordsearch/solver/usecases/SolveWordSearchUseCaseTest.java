package io.github.nicolasdesnoust.wordsearch.solver.usecases;

import io.github.nicolasdesnoust.wordsearch.core.aop.ValidateRequestAspect;
import io.github.nicolasdesnoust.wordsearch.solver.domain.Direction;
import io.github.nicolasdesnoust.wordsearch.solver.domain.GridFactory;
import io.github.nicolasdesnoust.wordsearch.solver.domain.NaiveWordFinder;
import io.github.nicolasdesnoust.wordsearch.solver.domain.WordFinder;
import io.github.nicolasdesnoust.wordsearch.solver.domain.WordsFactory;
import io.github.nicolasdesnoust.wordsearch.solver.usecases.SolveWordSearchUseCase.SolveWordSearchRequest;
import io.github.nicolasdesnoust.wordsearch.solver.usecases.SolveWordSearchUseCase.SolveWordSearchResponse;
import io.github.nicolasdesnoust.wordsearch.solver.usecases.SolveWordSearchUseCase.SolveWordSearchResponse.WordLocationDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class SolveWordSearchUseCaseTest {

    static SolveWordSearchUseCase underTest;

    @BeforeAll
    public static void setupUseCaseWithItsDependenciesAndAOP() {
        GridFactory gridFactory = new GridFactory();
        WordsFactory wordsFactory = new WordsFactory();
        WordFinder wordFinder = new NaiveWordFinder();

        SolveWordSearchUseCase solveWordSearchUseCase = new SolveWordSearchUseCase(
                gridFactory,
                wordsFactory,
                wordFinder
        );

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        ValidateRequestAspect aspect = new ValidateRequestAspect(validator);
        AspectJProxyFactory factory = new AspectJProxyFactory(solveWordSearchUseCase);
        factory.addAspect(aspect);
        underTest = factory.getProxy();
    }

    static Stream<String> invalidGrids() {
        int maximumGridSize = 5000;

        return Stream.of("", "   ", null, "A".repeat(maximumGridSize + 1));
    }

    static Stream<String> invalidWords() {
        int maximumWordsSize = 5000;

        return Stream.of("", "   ", null, "A".repeat(maximumWordsSize + 1));
    }

    static Stream<Arguments> gridPerDirection() {
        return Stream.of(
                Arguments.of(
                        String.join(System.lineSeparator(),
                                "BOL",
                                "XXX",
                                "XXX"
                        ),
                        Direction.LEFT_TO_RIGHT.toString()
                ),
                Arguments.of(
                        String.join(System.lineSeparator(),
                                "LOB",
                                "XXX",
                                "XXX"
                        ),
                        Direction.RIGHT_TO_LEFT.toString()
                ),
                Arguments.of(
                        String.join(System.lineSeparator(),
                                "BXX",
                                "OXX",
                                "LXX"
                        ),
                        Direction.TOP_TO_BOTTOM.toString()
                ),
                Arguments.of(
                        String.join(System.lineSeparator(),
                                "LXX",
                                "OXX",
                                "BXX"
                        ),
                        Direction.BOTTOM_TO_TOP.toString()
                ),
                Arguments.of(
                        String.join(System.lineSeparator(),
                                "XXL",
                                "XOX",
                                "BXX"
                        ),
                        Direction.BOTTOM_LEFT_TO_TOP_RIGHT.toString()
                ),
                Arguments.of(
                        String.join(System.lineSeparator(),
                                "LXX",
                                "XOX",
                                "XXB"
                        ),
                        Direction.BOTTOM_RIGHT_TO_TOP_LEFT.toString()
                ),
                Arguments.of(
                        String.join(System.lineSeparator(),
                                "BXX",
                                "XOX",
                                "XXL"
                        ),
                        Direction.TOP_LEFT_TO_BOTTOM_RIGHT.toString()
                ),
                Arguments.of(
                        String.join(System.lineSeparator(),
                                "XXB",
                                "XOX",
                                "LXX"
                        ),
                        Direction.TOP_RIGHT_TO_BOTTOM_LEFT.toString()
                )
        );
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
                .withGrid(String.join(System.lineSeparator(),
                        "BONJOURED",
                        "ALLODSOFE",
                        "FERGDBMFIM",
                        "WQ"
                ))
                .withWords(invalidWords)
                .build();

        assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> underTest.solveWordSearch(request))
                .withMessageContaining("words");
    }

    @Test
    void givenGridWithOneWord_whenSolveWordSearch_thenFindTheWordLocation() {
        var request = SolveWordSearchRequest.builder()
                .withGrid(String.join(System.lineSeparator(),
                        "BOGJOFRED",
                        "GDALLOSOF"
                ))
                .withWords("ALLO")
                .build();

        var response = underTest.solveWordSearch(request);

        assertThat(response)
                .extracting(SolveWordSearchResponse::getWordLocations)
                .isEqualTo(List.of(WordLocationDto.builder()
                        .withWord("ALLO")
                        .withX(2).withY(1)
                        .withDirection(Direction.LEFT_TO_RIGHT.toString())
                        .build()
                ));
    }

    @Test
    void whenSolveWordSearch_thenReturnInitialGrid() {
        var request = SolveWordSearchRequest.builder()
                .withGrid(String.join(System.lineSeparator(),
                        "BONJOURED",
                        "ALLODSOFE"
                ))
                .withWords("BONJOUR")
                .build();

        var response = underTest.solveWordSearch(request);

        assertThat(response)
                .extracting(SolveWordSearchResponse::getInputGrid)
                .isEqualTo(new char[][]{
                        "BONJOURED".toCharArray(),
                        "ALLODSOFE".toCharArray()
                });
    }

    @Test
    void givenGridWithOneWord_whenSolveWordSearch_thenFindUnusedLetters() {
        var request = SolveWordSearchRequest.builder()
                .withGrid(String.join(System.lineSeparator(),
                        "BONJOURED",
                        "ALLODSOFE"
                ))
                .withWords("BONJOUR")
                .build();

        var response = underTest.solveWordSearch(request);

        assertThat(response.getUnusedLetters())
                .containsExactlyInAnyOrder('E', 'D', 'A', 'L', 'L', 'O', 'D', 'S', 'O', 'F', 'E');
    }

    @Test
    void givenGridWithWordsEverywhere_whenSolveWordSearch_thenReturnNoUnusedLetters() {
        var request = SolveWordSearchRequest.builder()
                .withGrid(String.join(System.lineSeparator(),
                        "HELLO",
                        "ALLOT",
                        "COLLA"
                ))
                .withWords("HELLO ALLO OTA COL LOL")
                .build();

        var response = underTest.solveWordSearch(request);

        assertThat(response.getUnusedLetters()).isEmpty();
    }

    @Test
    void givenGridWithoutWords_whenSolveWordSearch_thenReturnNoWordLocations() {
        var request = SolveWordSearchRequest.builder()
                .withGrid(String.join(System.lineSeparator(),
                        "BOFJOFRED",
                        "ALGODSOFE"
                ))
                .withWords("BONSOIR")
                .build();

        var response = underTest.solveWordSearch(request);

        assertThat(response.getWordLocations()).isEmpty();
    }

    @Test
    void givenGridWithoutWords_whenSolveWordSearch_thenReturnAllUnusedLetters() {
        var request = SolveWordSearchRequest.builder()
                .withGrid(String.join(System.lineSeparator(),
                        "BO",
                        "AL"
                ))
                .withWords("BONSOIR")
                .build();

        var response = underTest.solveWordSearch(request);

        assertThat(response.getUnusedLetters())
                .containsExactlyInAnyOrder('B', 'O', 'A', 'L');
    }

    @ParameterizedTest
    @MethodSource("gridPerDirection")
    void givenGridWithOneWord_whenSolveWordSearch_thenFindWordsInEachDirection(String grid, String direction) {
        var request = SolveWordSearchRequest.builder()
                .withGrid(grid)
                .withWords("BOL")
                .build();

        var response = underTest.solveWordSearch(request);

        assertThat(response.getWordLocations())
                .hasSize(1)
                .flatExtracting(
                        WordLocationDto::getDirection,
                        WordLocationDto::getWord
                )
                .containsOnly(direction, "BOL");
    }

    @Test
    void givenGridWithHoles_whenSolveWordSearch_thenFillHolesWithSpaces() {
        var request = SolveWordSearchRequest.builder()
                .withGrid(String.join(System.lineSeparator(),
                        "BONJOURED",
                        "ACLOD",
                        "FERGDBMFIM",
                        "WQ"
                ))
                .withWords("BONJOUR")
                .build();

        var response = underTest.solveWordSearch(request);

        assertThat(response)
                .extracting(SolveWordSearchResponse::getInputGrid)
                .isEqualTo(new char[][]{
                        "BONJOURED ".toCharArray(),
                        "ACLOD     ".toCharArray(),
                        "FERGDBMFIM".toCharArray(),
                        "WQ        ".toCharArray()
                });
    }

    @Test
    void whenSolveWordSearch_thenIgnoreCase() {
        var request = SolveWordSearchRequest.builder()
                .withGrid(String.join(System.lineSeparator(),
                        "bOnJoUREh",
                        "FErGdBmFo",
                        "AdTJvBsJP"
                ))
                .withWords("BONjOUr Hop")
                .build();

        var response = underTest.solveWordSearch(request);

        assertThat(response.getWordLocations())
                .extracting(WordLocationDto::getWord)
                .containsExactlyInAnyOrder("BONJOUR", "HOP");
    }

    @Test
    void whenSolveWordSearch_thenUppercaseInputGrid() {
        var request = SolveWordSearchRequest.builder()
                .withGrid(String.join(System.lineSeparator(),
                        "bOnJoUREh",
                        "FErGdBmFo",
                        "AdTJvBsJP"
                ))
                .withWords("BONJOUR")
                .build();

        var response = underTest.solveWordSearch(request);

        assertThat(response.getInputGrid())
                .isEqualTo(new char[][]{
                        "BONJOUREH".toCharArray(),
                        "FERGDBMFO".toCharArray(),
                        "ADTJVBSJP".toCharArray()
                });
    }

    @Test
    void givenWordsWithAccents_whenSolveWordSearch_thenStripAccents() {
        var request = SolveWordSearchRequest.builder()
                .withGrid(String.join(System.lineSeparator(),
                        "AAACEEEEIIOUUU",
                        "OUUUAAACEEEEII"
                ))
                .withWords("âàäçéèêëîïôùûü ÔÙÛÜÂÀÄÇÉÈÊËÎÏ")
                .build();

        var response = underTest.solveWordSearch(request);

        assertThat(response.getWordLocations())
                .extracting(WordLocationDto::getWord)
                .containsExactlyInAnyOrder("AAACEEEEIIOUUU", "OUUUAAACEEEEII");
    }

    @Test
    void givenGridWithAccents_whenSolveWordSearch_thenStripAccents() {
        var request = SolveWordSearchRequest.builder()
                .withGrid(String.join(System.lineSeparator(),
                        "âàäçéèêëîïôùûü",
                        "ÔÙÛÜÂÀÄÇÉÈÊËÎÏ"
                ))
                .withWords("BONJOUR")
                .build();

        var response = underTest.solveWordSearch(request);

        assertThat(response.getInputGrid())
                .isEqualTo(new char[][]{
                        "AAACEEEEIIOUUU".toCharArray(),
                        "OUUUAAACEEEEII".toCharArray()
                });
    }

    @Test
    void givenDuplicateWords_whenSolveWordSearch_thenOnlyLookForOne() {
        var request = SolveWordSearchRequest.builder()
                .withGrid(String.join(System.lineSeparator(),
                        "BONJOURED",
                        "GDALLOSOF"
                ))
                .withWords("BONJOUR BONJOUR BONJOUR BONJOUR")
                .build();

        var response = underTest.solveWordSearch(request);

        assertThat(response.getWordLocations())
                .hasSize(1)
                .extracting(WordLocationDto::getWord)
                .containsExactly("BONJOUR");
    }

}