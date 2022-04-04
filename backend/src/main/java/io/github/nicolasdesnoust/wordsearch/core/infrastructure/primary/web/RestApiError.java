package io.github.nicolasdesnoust.wordsearch.core.infrastructure.primary.web;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.github.nicolasdesnoust.wordsearch.core.domain.StandardErrorMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

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
            description = "Détails de l'erreur (anglais ou français).",
            example = "Une erreur technique est survenue lors du traitement de votre demande",
            required = true
    )
    private String message;

    @Schema(
            description = "Statut HTTP de l'erreur.",
            example = "500",
            required = true
    )
    @Builder.Default
    private int status = 500;

    @Schema(
            description = "Type de l'erreur, qui permet de mieux distinguer programmatiquement les erreurs qui partagent un même statut HTTP.",
            example = "server.internal-server-error"
    )
    @Builder.Default
    private String type = StandardErrorMessage.INTERNAL_SERVER_ERROR.getMessageKey();

    @Schema(
            description = "URI à partir de laquelle l'erreur est survenue.",
            example = "/api/grids/_converts",
            required = true
    )
    private String path;

    @JsonInclude(Include.NON_EMPTY)
    @Schema(implementation = RestApiValidationError.class)
    private List<RestApiSubError> subErrors;

    interface RestApiSubError extends Serializable {

    }
}
