package io.github.nicolasdesnoust.wordsearch.solver.usecases;

import io.github.nicolasdesnoust.wordsearch.core.usecases.LogUseCaseExecution;
import io.github.nicolasdesnoust.wordsearch.solver.domain.Grid;
import io.github.nicolasdesnoust.wordsearch.solver.domain.GridFactory;
import io.github.nicolasdesnoust.wordsearch.solver.domain.WordFinder;
import io.github.nicolasdesnoust.wordsearch.solver.domain.WordLocation;
import io.github.nicolasdesnoust.wordsearch.solver.domain.WordsFactory;
import io.github.nicolasdesnoust.wordsearch.solver.usecases.SolveWordSearchUseCase.SolveWordSearchResponse.WordLocationDto;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SolveWordSearchUseCase {

    private final GridFactory gridFactory;
    private final WordsFactory wordsFactory;
    private final WordFinder wordFinder;
    private final Validator validator;

    @LogUseCaseExecution
    public SolveWordSearchResponse solveWordSearch(SolveWordSearchRequest request) {
        validate(request);

        Grid grid = gridFactory.createFrom(request.getGrid());
        List<String> words = wordsFactory.createFrom(request.getWords());

        List<WordLocation> wordLocations = wordFinder.findWords(grid, words);

        wordLocations.forEach(grid::markLettersAsUsed);

        return SolveWordSearchResponse.builder()
                .withInputGrid(grid.getLetters())
                .withWordLocations(toWordLocationDtoList(wordLocations))
                .withUnusedLetters(grid.getUnusedLetters())
                .build();
    }

    private void validate(SolveWordSearchRequest request) {
        Set<ConstraintViolation<SolveWordSearchRequest>> violations = validator.validate(request);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    private List<WordLocationDto> toWordLocationDtoList(List<WordLocation> wordLocations) {
        return wordLocations.stream()
                .map(WordLocationDto::from)
                .collect(Collectors.toList());
    }

    @Value
    @Builder(setterPrefix = "with")
    public static class SolveWordSearchRequest {
        @NotBlank
        @Size(max = 5000)
        String grid;

        @NotBlank
        @Size(max = 5000)
        String words;
    }

    @Value
    @Builder(setterPrefix = "with")
    public static class SolveWordSearchResponse {
        char[][] inputGrid;
        List<WordLocationDto> wordLocations;
        List<Character> unusedLetters;

        @Value
        @Builder(setterPrefix = "with")
        public static class WordLocationDto {
            String word;
            int x;
            int y;
            String direction;

            private static WordLocationDto from(WordLocation wordLocation) {
                return WordLocationDto.builder()
                        .withWord(wordLocation.getWord())
                        .withX(wordLocation.getCoordinates().getX())
                        .withY(wordLocation.getCoordinates().getY())
                        .withDirection(wordLocation.getDirection().toString())
                        .build();
            }
        }
    }

}
