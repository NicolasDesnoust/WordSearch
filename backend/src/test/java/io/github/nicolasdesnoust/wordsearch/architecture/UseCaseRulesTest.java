package io.github.nicolasdesnoust.wordsearch.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import io.github.nicolasdesnoust.wordsearch.architecture.Layers.TechnicalLayer;
import io.github.nicolasdesnoust.wordsearch.core.usecases.LogUseCaseExecution;
import io.github.nicolasdesnoust.wordsearch.core.usecases.ValidateRequest;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
import static io.github.nicolasdesnoust.wordsearch.architecture.CustomArchUnitConditions.containOnlyOnePublicMethod;
import static io.github.nicolasdesnoust.wordsearch.architecture.CustomArchUnitConditions.haveAllParametersAnnotatedWith;

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
    static ArchRule use_case_executions_should_be_logged =
            methods()
                    .that().areDeclaredInClassesThat().haveSimpleNameEndingWith("UseCase")
                    .and().arePublic()
                    .should().beAnnotatedWith(LogUseCaseExecution.class);

    @ArchTest
    static ArchRule use_case_requests_should_be_validated =
            methods()
                    .that().areDeclaredInClassesThat().haveSimpleNameEndingWith("UseCase")
                    .and().arePublic()
                    .should(haveAllParametersAnnotatedWith(ValidateRequest.class));

}
