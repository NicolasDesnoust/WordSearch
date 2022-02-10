package io.github.nicolasdesnoust.wordsearch.solver.infrastructure.primary.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.nicolasdesnoust.wordsearch.solver.usecases.SolveWordSearchUseCase;
import io.github.nicolasdesnoust.wordsearch.solver.usecases.SolveWordSearchUseCase.SolveWordSearchRequest;
import io.github.nicolasdesnoust.wordsearch.solver.usecases.SolveWordSearchUseCase.SolveWordSearchResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/grids")
class SolveWordSearchController implements SolveWordSearchSpecification {

    private final SolveWordSearchUseCase useCase;

    @Override
    @PostMapping("/_solve")
    public ResponseEntity<SolveWordSearchResponse> solveWordSearch(@RequestBody SolveWordSearchRequest request) {
        var response = useCase.solveWordSearch(request);

        return ResponseEntity.ok(response);
    }

}
