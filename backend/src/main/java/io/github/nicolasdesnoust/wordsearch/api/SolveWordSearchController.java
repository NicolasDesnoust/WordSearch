package io.github.nicolasdesnoust.wordsearch.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.nicolasdesnoust.wordsearch.usecases.SolveWordSearchUseCase;
import io.github.nicolasdesnoust.wordsearch.usecases.SolveWordSearchUseCase.SolveWordSearchRequest;
import io.github.nicolasdesnoust.wordsearch.usecases.SolveWordSearchUseCase.SolveWordSearchResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/grids")
public class SolveWordSearchController {

    private final SolveWordSearchUseCase useCase;

    @PostMapping("/_solve")
    public ResponseEntity<SolveWordSearchResponse> solveWordSearch(@RequestBody SolveWordSearchRequest request) {
        var response = useCase.solveWordSearch(request);

        return ResponseEntity.ok(response);
    }

}
