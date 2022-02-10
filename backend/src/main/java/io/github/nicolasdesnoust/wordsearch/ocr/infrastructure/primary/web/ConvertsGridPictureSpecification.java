package io.github.nicolasdesnoust.wordsearch.ocr.infrastructure.primary.web;

import io.github.nicolasdesnoust.wordsearch.core.infrastructure.primary.web.OpenApiConstants;
import io.github.nicolasdesnoust.wordsearch.core.infrastructure.primary.web.RestApiError;
import io.github.nicolasdesnoust.wordsearch.ocr.usecases.ConvertsGridPictureUseCase.ConvertsGridPictureResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

@Tag(
        name = OpenApiConstants.Ocr.TAG_NAME,
        description = OpenApiConstants.Ocr.TAG_DESCRIPTION
)
interface ConvertsGridPictureSpecification {

    @Operation(
            summary = "Extraire le contenu textuel de l'image d'une grille",
            description = ""
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConvertsGridPictureResponse.class))
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
    ResponseEntity<ConvertsGridPictureResponse> convertsGridPicture(MultipartFile gridPicture);
}
