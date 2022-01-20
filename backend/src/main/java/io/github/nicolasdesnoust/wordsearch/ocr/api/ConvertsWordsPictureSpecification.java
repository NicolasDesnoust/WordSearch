package io.github.nicolasdesnoust.wordsearch.ocr.api;

import io.github.nicolasdesnoust.wordsearch.core.api.OpenApiConstants;
import io.github.nicolasdesnoust.wordsearch.core.api.RestApiError;
import io.github.nicolasdesnoust.wordsearch.ocr.usecases.ConvertsWordsPictureUseCase.ConvertsWordsPictureResponse;
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
public interface ConvertsWordsPictureSpecification {

    @Operation(
            summary = "Extraire le contenu textuel de l'image d'une liste de mots",
            description = ""
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConvertsWordsPictureResponse.class))
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
    ResponseEntity<ConvertsWordsPictureResponse> convertsWordsPicture(MultipartFile wordsPicture);
}
