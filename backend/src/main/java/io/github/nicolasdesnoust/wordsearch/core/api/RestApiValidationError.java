package io.github.nicolasdesnoust.wordsearch.core.api;

import io.github.nicolasdesnoust.wordsearch.core.api.RestApiError.RestApiSubError;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.ConstraintViolation;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class RestApiValidationError implements RestApiSubError {

	@Schema(
			description = "Objet invalide.",
			example = "SolveWordSearchRequest",
			required = true
	)
	private String object;

	@Schema(
			description = "Propriété invalide de l'objet.",
			example = "grid",
			required = true
	)
	private String field;

	@Schema(
			description = "Valeur de la propriété invalide.",
			example = "null",
			required = true
	)
	private Object rejectedValue;

	@Schema(
			description = "Message expliquant pourquoi la valeur de la propriété est considérée invalide.",
			example = "Le champ 'grid' ne peut être null.",
			required = true
	)
	private String message;

	public static <T> RestApiValidationError fromConstraintViolation(
			ConstraintViolation<T> constraintViolation
	) {
		return new RestApiValidationError(
				constraintViolation.getRootBeanClass().getSimpleName(),
				constraintViolation.getPropertyPath().toString(),
				constraintViolation.getInvalidValue(),
				constraintViolation.getMessage()
		);
	}
}

