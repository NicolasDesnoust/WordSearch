package io.github.nicolasdesnoust.wordsearch.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import io.github.nicolasdesnoust.wordsearch.architecture.Layers.TechnicalLayer;
import io.github.nicolasdesnoust.wordsearch.core.usecases.LogUseCaseExecution;

import java.util.Set;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

@AnalyzeClasses(
        packages = Layers.ABSOLUTE_PATH_OF_BASE_PACKAGE,
        importOptions = ImportOption.DoNotIncludeTests.class
)
public class UseCaseRulesTest {

    @ArchTest
    public static ArchRule use_cases_should_have_only_one_public_method =
            classes()
                    .that().haveSimpleNameEndingWith("UseCase")
                    .should(containOnlyOnePublicMethod());

    @ArchTest
    static ArchRule use_cases_should_be_in_their_technical_layer =
            classes()
                    .that().haveSimpleNameEndingWith("UseCase")
                    .should().resideInAPackage(TechnicalLayer.USE_CASES.getAbsolutePath());

    @ArchTest
    static ArchRule all_use_case_executions_should_be_logged =
            methods()
                    .that().areDeclaredInClassesThat().haveSimpleNameEndingWith("UseCase")
                    .and().arePublic()
                    .should().beAnnotatedWith(LogUseCaseExecution.class);

    private static ArchCondition<JavaClass> containOnlyOnePublicMethod() {

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
