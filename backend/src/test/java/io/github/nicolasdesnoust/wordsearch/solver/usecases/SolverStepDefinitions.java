package io.github.nicolasdesnoust.wordsearch.solver.usecases;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.nicolasdesnoust.wordsearch.core.aop.ValidateRequestAspect;
import io.github.nicolasdesnoust.wordsearch.solver.domain.GridFactory;
import io.github.nicolasdesnoust.wordsearch.solver.domain.NaiveWordFinder;
import io.github.nicolasdesnoust.wordsearch.solver.domain.WordFinder;
import io.github.nicolasdesnoust.wordsearch.solver.domain.WordsFactory;
import io.github.nicolasdesnoust.wordsearch.solver.usecases.SolveWordSearchUseCase.SolveWordSearchRequest;
import io.github.nicolasdesnoust.wordsearch.solver.usecases.SolveWordSearchUseCase.SolveWordSearchRequest.SolveWordSearchRequestBuilder;
import io.github.nicolasdesnoust.wordsearch.solver.usecases.SolveWordSearchUseCase.SolveWordSearchResponse;
import io.github.nicolasdesnoust.wordsearch.solver.usecases.SolveWordSearchUseCase.SolveWordSearchResponse.WordLocationDto;
import io.github.nicolasdesnoust.wordsearch.solver.util.GridFixtures;
import io.github.nicolasdesnoust.wordsearch.solver.util.WordFixtures;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class SolverStepDefinitions {

    private final SolveWordSearchRequestBuilder requestBuilder = SolveWordSearchRequest.builder()
            .withGrid(GridFixtures.aValidGrid())
            .withWords(WordFixtures.someValidWords());
    private SolveWordSearchUseCase underTest;
    private SolveWordSearchResponse response;
    private Throwable thrown;

    @Before
    public void setupUseCaseWithItsDependenciesAndAOP() {
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

    @Given("a grid of letters")
    public void aGridOfLetters() {
        requestBuilder.withGrid("""
                BONJOURED
                ALLODSOFE
                """);
    }

    @When("I solve the word search puzzle")
    public void iSolveTheWordSearchPuzzle() {
        response = underTest.solveWordSearch(requestBuilder.build());
    }

    @Then("I should be given back the grid")
    public void iShouldBeGivenBackTheGrid() {
        assertThat(response)
                .extracting(SolveWordSearchResponse::getInputGrid)
                .isEqualTo(new char[][]{
                        "BONJOURED".toCharArray(),
                        "ALLODSOFE".toCharArray()
                });
    }

    @When("I try to solve the word search puzzle")
    public void iTryToSolveTheWordSearchPuzzle() {
        thrown = catchThrowable(this::iSolveTheWordSearchPuzzle);
    }

    @Given("a grid that contains no words")
    public void aGridThatContainsNoWords() {
        requestBuilder.withGrid("""
                BOGJOEREH
                FERGDBMFO
                ADTJVBSJP
                """);
    }

    @Then("all letters of the grid should be unused")
    public void allLettersOfTheGridShouldBeUnused() {
        List<Character> expectedUnusedLetters = "BOGJOEREHFERGDBMFOADTJVBSJP".chars()
                .mapToObj(e -> (char) e)
                .collect(Collectors.toList());
        List<Character> actualUnusedLetters = response.getUnusedLetters();

        assertThat(actualUnusedLetters)
                .containsExactlyInAnyOrderElementsOf(expectedUnusedLetters);
    }

    @Given("a grid of letters that contains a word in direction {string}")
    public void aGridOfLettersThatContainsAWordInDirection(String wordDirection) {
        requestBuilder.withGrid(GridFixtures.aGridWithOneWordInDirection(wordDirection))
                .withWords(GridFixtures.getTheWordUsedInDirectionBasedFixtures());
    }

    @Then("I should be given the word")
    public void iShouldBeGivenTheWord() {
        assertThat(response.getWordLocations())
                .extracting(WordLocationDto::getWord)
                .containsOnly(GridFixtures.getTheWordUsedInDirectionBasedFixtures());
    }

    @And("I should be given the direction {string}")
    public void iShouldBeGivenTheDirection(String wordDirection) {
        assertThat(response.getWordLocations())
                .flatExtracting(WordLocationDto::getDirection)
                .containsExactly(wordDirection);
    }

    @Given("a grid with words everywhere")
    public void aGridWithWordsEverywhere() {
        requestBuilder
                .withGrid("""
                        HELLO
                        ALLOT
                        COLLA
                        """)
                .withWords("HELLO ALLO OTA COL LOL");
    }

    @Then("I should be told that there is no unused letters")
    public void iShouldBeToldThatThereIsNoUnusedLetters() {
        assertThat(response.getUnusedLetters()).isEmpty();
    }

    @Then("I should be given no word locations")
    public void iShouldBeGivenNoWordLocations() {
        assertThat(response.getWordLocations()).isEmpty();
    }

    @Given("a grid with rows of different sizes")
    public void aGridWithRowsOfDifferentSizes() {
        requestBuilder.withGrid("""
                BONJOURED
                ACLOD
                FERGDBMFIM
                WQ
                """);
    }

    @Then("I should be given back the grid with the holes filled with spaces")
    public void iShouldBeGivenBackTheGridWithTheHolesFilledWithSpaces() {
        assertThat(response)
                .extracting(SolveWordSearchResponse::getInputGrid)
                .isEqualTo(new char[][]{
                        "BONJOURED ".toCharArray(),
                        "ACLOD     ".toCharArray(),
                        "FERGDBMFIM".toCharArray(),
                        "WQ        ".toCharArray()
                });
    }

    @Given("a grid and a list of words of different case")
    public void aGridAndAListOfWordsOfDifferentCase() {
        requestBuilder.withGrid("""                       
                bOnJoUREh
                FErGdBmFo
                AdTJvBsJP
                """)
                .withWords("BONjOUr Hop");
    }

    @Then("I should be given the word locations regardless of their case")
    public void iShouldBeGivenTheWordLocationsRegardlessOfTheirCase() {
        assertThat(response.getWordLocations())
                .extracting(WordLocationDto::getWord)
                .containsExactlyInAnyOrder("BONJOUR", "HOP");
    }

    @Given("a grid with lowercase letters")
    public void aGridWithLowercaseLetters() {
        requestBuilder.withGrid("""      
                bOnJoUREh
                FErGdBmFo
                AdTJvBsJP
                """);
    }

    @Then("I should be given back the grid with uppercase letters")
    public void iShouldBeGivenBackTheGridWithUppercaseLetters() {
        assertThat(response.getInputGrid())
                .isEqualTo(new char[][]{
                        "BONJOUREH".toCharArray(),
                        "FERGDBMFO".toCharArray(),
                        "ADTJVBSJP".toCharArray()
                });
    }

    @Given("a grid with accents on its letters")
    public void aGridWithAccentsOnItsLetters() {
        requestBuilder.withGrid("""
                âàäçéèêëîïôùûü
                ÔÙÛÜÂÀÄÇÉÈÊËÎÏ
                """);
    }

    @Then("I should be given back the grid without accents")
    public void iShouldBeGivenBackTheGridWithoutAccents() {
        assertThat(response.getInputGrid())
                .isEqualTo(new char[][]{
                        "AAACEEEEIIOUUU".toCharArray(),
                        "OUUUAAACEEEEII".toCharArray()
                });
    }

    @Given("a list of words with accents on their letters")
    public void aListOfWordsWithAccentsOnTheirLetters() {
        requestBuilder.withGrid("""
                AAACEEEEIIOUUU
                OUUUAAACEEEEII
                """)
                .withWords("âàäçéèêëîïôùûü ÔÙÛÜÂÀÄÇÉÈÊËÎÏ");
    }

    @Then("I should be given the words found without accents")
    public void iShouldBeGivenTheWordsFoundWithoutAccents() {
        assertThat(response.getWordLocations())
                .extracting(WordLocationDto::getWord)
                .containsExactlyInAnyOrder("AAACEEEEIIOUUU", "OUUUAAACEEEEII");
    }

    @Given("a list of words with duplicates")
    public void aListOfWordsWithDuplicates() {
        requestBuilder.withGrid("""
                BONJOURED
                GDALLOSOF
                """)
                .withWords("BONJOUR BONJOUR BONJOUR BONJOUR");
    }

    @Then("I should be given the duplicates location only once")
    public void iShouldBeGivenTheDuplicatesLocationOnlyOnce() {
        assertThat(response.getWordLocations())
                .hasSize(1)
                .extracting(WordLocationDto::getWord)
                .containsExactly("BONJOUR");
    }

    @Given("a grid that contains one word")
    public void aGridThatContainsOneWord() {
        requestBuilder.withGrid("""
                BONJOURED
                ALLODSOFE
                """)
                .withWords("BONJOUR");
    }

    @Then("I should be told which letters are unused")
    public void iShouldBeToldWhichLettersAreUnused() {
        assertThat(response.getUnusedLetters())
                .containsExactlyInAnyOrder('E', 'D', 'A', 'L', 'L', 'O', 'D', 'S', 'O', 'F', 'E');
    }

    @Given("an invalid grid that is {string}")
    public void anInvalidGridThatIs(String invalidGridType) {
        String invalidGrid = GridFixtures.InvalidGrid.valueOf(invalidGridType)
                .build();

        requestBuilder.withGrid(invalidGrid);
    }

    @Given("an invalid word list that is {string}")
    public void anInvalidWordListThatIs(String invalidWordsType) {
        String invalidWords = WordFixtures.InvalidWords.valueOf(invalidWordsType)
                .build();

        requestBuilder.withWords(invalidWords);
    }

    @Then("I should be told {string} is/are invalid")
    public void iShouldBeToldIsInvalid(String invalidThing) {
        assertThat(thrown)
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining(invalidThing);
    }
}
