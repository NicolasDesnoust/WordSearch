package io.github.nicolasdesnoust.wordsearch.core.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ValidateRequestAspect {

    private static final String MISSING_METHOD_PARAMETER = "Annotation @ValidateRequest must be placed on a method parameter.";

    private final Validator validator;

    @Pointcut("execution(* *(@io.github.nicolasdesnoust.wordsearch.core.usecases.ValidateRequest (*), ..))")
    public static void requests() {
        // Select all method parameters annotated with @ValidateRequest
    }

    @Before("requests()")
    public void validateUseCaseRequest(JoinPoint joinPoint) {
        Object request = getRequestFrom(joinPoint);
        String requestSimpleName = request.getClass().getSimpleName().split("\\$")[0];

        log.debug("Validating request {}", requestSimpleName);
        validate(request);
    }

    private Object getRequestFrom(JoinPoint joinPoint) {
        Object useCase;

        try {
            useCase = joinPoint.getArgs()[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new MissingMethodParameterException(MISSING_METHOD_PARAMETER, e);
        }

        return useCase;
    }

    private void validate(Object request) {
        Set<ConstraintViolation<Object>> violations = validator.validate(request);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    public static class MissingMethodParameterException extends RuntimeException {
        MissingMethodParameterException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
