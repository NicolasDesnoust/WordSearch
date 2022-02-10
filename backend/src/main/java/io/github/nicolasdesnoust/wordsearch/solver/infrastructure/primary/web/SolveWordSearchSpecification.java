package io.github.nicolasdesnoust.wordsearch.solver.infrastructure.primary.web;

import io.github.nicolasdesnoust.wordsearch.core.infrastructure.primary.web.OpenApiConstants;
import io.github.nicolasdesnoust.wordsearch.core.infrastructure.primary.web.RestApiError;
import io.github.nicolasdesnoust.wordsearch.solver.usecases.SolveWordSearchUseCase.SolveWordSearchRequest;
import io.github.nicolasdesnoust.wordsearch.solver.usecases.SolveWordSearchUseCase.SolveWordSearchResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(
        name = OpenApiConstants.Solver.TAG_NAME,
        description = OpenApiConstants.Solver.TAG_DESCRIPTION
)
interface SolveWordSearchSpecification {

    @Operation(
            summary = "Résoudre une grille de mot-mêlés",
            description = ""
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SolveWordSearchResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "${word-search.errors.constraint-violation.title}",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestApiError.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "${word-search.errors.internal-server-error.title}",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestApiError.class))
            )
    })
    ResponseEntity<SolveWordSearchResponse> solveWordSearch(SolveWordSearchRequest request);
}
