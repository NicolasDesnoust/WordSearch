package io.github.nicolasdesnoust.wordsearch.solver.configuration;

import io.github.nicolasdesnoust.wordsearch.solver.domain.GridFactory;
import io.github.nicolasdesnoust.wordsearch.solver.domain.NaiveWordFinder;
import io.github.nicolasdesnoust.wordsearch.solver.domain.WordsFactory;
import io.github.nicolasdesnoust.wordsearch.solver.usecases.SolveWordSearchUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validator;

@Configuration
public class SolverApplicationContext {

    @Bean
    public SolveWordSearchUseCase solveWordSearchUseCase(
            @Autowired GridFactory gridFactory,
            @Autowired WordsFactory wordsFactory,
            @Autowired NaiveWordFinder wordFindingFacade,
            @Autowired Validator validator
    ) {
        return new SolveWordSearchUseCase(
                gridFactory,
                wordsFactory,
                wordFindingFacade,
                validator
        );
    }

    @Bean
    public GridFactory gridFactory() {
        return new GridFactory();
    }

    @Bean
    public WordsFactory wordsFactory() {
        return new WordsFactory();
    }

    @Bean
    public NaiveWordFinder wordFinder() {
        return new NaiveWordFinder();
    }

}
