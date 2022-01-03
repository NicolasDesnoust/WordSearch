package io.github.nicolasdesnoust.wordsearch.usecases;

import java.util.List;
import java.util.stream.Collectors;

import io.github.nicolasdesnoust.wordsearch.domain.Grid;
import io.github.nicolasdesnoust.wordsearch.domain.GridFactory;
import io.github.nicolasdesnoust.wordsearch.domain.WordLocation;
import io.github.nicolasdesnoust.wordsearch.domain.WordsFactory;
import io.github.nicolasdesnoust.wordsearch.domain.WordFinder;
import io.github.nicolasdesnoust.wordsearch.usecases.SolveWordSearchUseCase.SolveWordSearchResponse.WordLocationDto;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class SolveWordSearchUseCase {

    private final GridFactory gridFactory;
    private final WordsFactory wordsFactory;
    private final WordFinder wordFinder;

    public SolveWordSearchResponse solveWordSearch(SolveWordSearchRequest request) {
        Grid grid = gridFactory.createFrom(request.getGrid());
        List<String> words = wordsFactory.createFrom(request.getWords());

        WordFinder.FindWordsResult findWordsResult = wordFinder.findWords(grid, words);

        List<WordLocation> wordLocations = findWordsResult.getWordLocations();
        wordLocations.forEach(grid::markLettersAsUsed);
        wordLocations.forEach(wordLocation -> log.info("A word was found : {}.", wordLocation));

        return SolveWordSearchResponse.builder()
                .withInputGrid(grid.getLetters())
                .withWordLocations(toWordLocationDtoList(wordLocations))
                .withUnusedLetters(grid.getUnusedLetters())
                .build();
    }

    private List<WordLocationDto> toWordLocationDtoList(List<WordLocation> wordLocations) {
        return wordLocations.stream()
                .map(WordLocationDto::from)
                .collect(Collectors.toList());
    }

    @Value
    @Builder(setterPrefix = "with")
    public static class SolveWordSearchRequest {
        String words;
        String grid;
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
