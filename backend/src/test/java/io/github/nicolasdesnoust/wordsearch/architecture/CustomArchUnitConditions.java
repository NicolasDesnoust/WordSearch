package io.github.nicolasdesnoust.wordsearch.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

import java.lang.annotation.Annotation;
import java.util.Set;

public class CustomArchUnitConditions {

    static ArchCondition<JavaMethod> haveAllParametersAnnotatedWith(Class<? extends Annotation> annotationClass) {
        return new ArchCondition<JavaMethod>("have all parameters annotated with @" + annotationClass.getSimpleName()) {
            @Override
            public void check(JavaMethod method, ConditionEvents events) {
                boolean areAllParametersAnnotated = true;
                for (Annotation[] parameterAnnotations : method.reflect().getParameterAnnotations()) {
                    boolean isParameterAnnotated = false;
                    for (Annotation annotation : parameterAnnotations) {
                        if (annotation.annotationType().equals(annotationClass)) {
                            isParameterAnnotated = true;
                        }
                    }
                    areAllParametersAnnotated &= isParameterAnnotated;
                }
                String message = (areAllParametersAnnotated ? "" : "not ")
                        + "all parameters of " + method.getDescription()
                        + " are annotated with @" + annotationClass.getSimpleName();
                events.add(new SimpleConditionEvent(method, areAllParametersAnnotated, message));
            }
        };
    }

    static ArchCondition<JavaClass> containOnlyOnePublicMethod() {

        return new ArchCondition<>("Only one public method") {

            private static final int USE_CASES_PUBLIC_METHODS_LIMIT = 1;

            @Override
            public void check(final JavaClass clazz, final ConditionEvents events) {

                final String name = clazz.getName();
                final Set<JavaMethod> methodsSet = clazz.getMethods();
                int PublicMethodsCount = 0;

                for (final JavaMethod javaMethod : methodsSet) {
                    if (javaMethod.getModifiers()
                            .contains(JavaModifier.PUBLIC)) {
                        PublicMethodsCount = PublicMethodsCount + 1;
                    }
                }

                if (PublicMethodsCount > USE_CASES_PUBLIC_METHODS_LIMIT) {
                    throw new AssertionError(String.format("Class %s contains %d public methods, when limit is %d",
                            name, PublicMethodsCount, USE_CASES_PUBLIC_METHODS_LIMIT));
                }
            }
        };
    }

}