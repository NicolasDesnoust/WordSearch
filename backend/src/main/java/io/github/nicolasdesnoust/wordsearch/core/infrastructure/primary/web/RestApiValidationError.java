package io.github.nicolasdesnoust.wordsearch.core.infrastructure.primary.web;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.ConstraintViolation;

import io.github.nicolasdesnoust.wordsearch.core.infrastructure.primary.web.RestApiError.RestApiSubError;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
	private Serializable rejectedValue;

	@Schema(
			description = "Message expliquant pourquoi la valeur de la propriété est considérée invalide.",
			example = "Le champ 'grid' ne peut être null.",
			required = true
	)
	private String message;

	public static <T> RestApiValidationError fromConstraintViolation(
			ConstraintViolation<T> constraintViolation
	) {
		
		String object = constraintViolation.getRootBeanClass().getSimpleName();
		String field = constraintViolation.getPropertyPath().toString();
		Serializable invalidValue = makeObjectSerializable(constraintViolation.getInvalidValue());
		String message = constraintViolation.getMessage();
		
		return new RestApiValidationError(
				object,
				field,
				invalidValue,
				message
		);
	}

	private static Serializable makeObjectSerializable(Object toMakeSerializable) {
		Serializable serialized;
		
		if(toMakeSerializable instanceof Serializable) {
			serialized = (Serializable) toMakeSerializable;
		} else {
			serialized = Objects.toString(toMakeSerializable);
		}

		return serialized;
	}
}

