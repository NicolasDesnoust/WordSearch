package io.github.nicolasdesnoust.wordsearch.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import io.github.nicolasdesnoust.wordsearch.architecture.Layers.TechnicalLayer;
import io.github.nicolasdesnoust.wordsearch.core.api.LogRestApiError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

@AnalyzeClasses(
        packages = Layers.ABSOLUTE_PATH_OF_BASE_PACKAGE,
        importOptions = ImportOption.DoNotIncludeTests.class
)
public class ExceptionHandlerRulesTest {

    @ArchTest
    static ArchRule exception_handlers_should_be_in_their_technical_layer =
            classes()
                    .that().haveSimpleNameEndingWith("ExceptionHandler")
                    .should().resideInAPackage(TechnicalLayer.API.getAbsolutePath());

    @ArchTest
    static ArchRule exception_handlers_should_return_ResponseEntity_instances =
            methods()
                    .that().areAnnotatedWith(ExceptionHandler.class)
                    .should().haveRawReturnType(ResponseEntity.class);

    @ArchTest
    static ArchRule all_exceptions_translated_to_rest_api_errors_should_be_logged =
            methods()
                    .that().areAnnotatedWith(ExceptionHandler.class)
                    .should().beAnnotatedWith(LogRestApiError.class);

    @ArchTest
    static ArchRule annotation_LogRestApiError_should_be_used_only_for_exception_handlers =
            methods()
                    .that().areAnnotatedWith(LogRestApiError.class)
                    .should().beAnnotatedWith(ExceptionHandler.class);

}