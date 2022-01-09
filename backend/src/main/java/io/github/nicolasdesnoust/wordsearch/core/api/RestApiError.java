package io.github.nicolasdesnoust.wordsearch.core.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder(setterPrefix = "with")
public class RestApiError implements Serializable {

    @Schema(
            description = "Date à laquelle l'erreur est survenue, au format ISO 8601.",
            example = "2022-09-07T13:16:52",
            required = true
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime timestamp = LocalDateTime.now();

    @Schema(
            description = "Titre de l'erreur (traduit).",
            example = "Un titre décrivant l'erreur manière sommaire",
            required = true
    )
    private String title;

    @Schema(
            description = "Détails de l'erreur (anglais ou français).",
            example = "Error details giving more informations than the title."
    )
    private String detail;

    @Schema(
            description = "Statut HTTP de l'erreur.",
            example = "500",
            required = true
    )
    @Builder.Default
    private int status = HttpStatus.INTERNAL_SERVER_ERROR.value();

    @Schema(
            description = "Type de l'erreur, qui permet de mieux distinguer programmatiquement les erreurs qui partagent un même statut HTTP.",
            example = "InternalServerError"
    )
    @Builder.Default
    private ErrorType type = ErrorType.INTERNAL_ERROR;

    @Schema(
            description = "URI à partir de laquelle l'erreur est survenue.",
            example = "http://localhost:8080/api/grids/_converts",
            required = true
    )
    private String path;

    @JsonInclude(Include.NON_EMPTY)
    @Schema(implementation = RestApiValidationError.class)
    private List<RestApiSubError> subErrors;

    interface RestApiSubError extends Serializable {

    }
}
