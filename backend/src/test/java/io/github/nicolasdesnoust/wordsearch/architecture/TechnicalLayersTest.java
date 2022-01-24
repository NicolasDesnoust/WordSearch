package io.github.nicolasdesnoust.wordsearch.architecture;

import static com.tngtech.archunit.base.DescribedPredicate.alwaysTrue;
import static com.tngtech.archunit.base.DescribedPredicate.not;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.assignableTo;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.equivalentTo;
import static com.tngtech.archunit.lang.conditions.ArchPredicates.are;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import org.springframework.web.bind.annotation.ControllerAdvice;

import io.github.nicolasdesnoust.wordsearch.WordSearchApplication;
import io.github.nicolasdesnoust.wordsearch.architecture.Layers.TechnicalLayer;

@AnalyzeClasses(
        packages = Layers.ABSOLUTE_PATH_OF_BASE_PACKAGE,
        importOptions = ImportOption.DoNotIncludeTests.class
)
class TechnicalLayersTest {

    @ArchTest
    static ArchRule cannot_bypass_architectural_checks_by_creating_new_technical_layers = classes()
            .that(are(not(equivalentTo(WordSearchApplication.class))))
            .should().resideInAnyPackage(
                    TechnicalLayer.API.getAbsolutePath(),
                    TechnicalLayer.USE_CASES.getAbsolutePath(),
                    TechnicalLayer.DOMAIN.getAbsolutePath(),
                    TechnicalLayer.CONFIGURATION.getAbsolutePath(),
                    TechnicalLayer.UTIL.getAbsolutePath(),
                    TechnicalLayer.AOP.getAbsolutePath()
            );

    @ArchTest
    static ArchRule use_framework_only_in_low_level_layers = noClasses()
            .that(are(not(equivalentTo(WordSearchApplication.class))))
            .and().resideOutsideOfPackages(
                    TechnicalLayer.API.getAbsolutePath(),
                    TechnicalLayer.CONFIGURATION.getAbsolutePath(),
                    TechnicalLayer.AOP.getAbsolutePath(),
                    TechnicalLayer.UTIL.getAbsolutePath()
            )
            .should().dependOnClassesThat()
            .resideInAnyPackage("org.springframework..");

    @ArchTest
    static ArchRule use_web_librairies_only_in_low_level_layers = noClasses()
            .that().resideOutsideOfPackages(
                    TechnicalLayer.API.getAbsolutePath(),
                    TechnicalLayer.CONFIGURATION.getAbsolutePath()
            )
            .should().dependOnClassesThat()
            .resideInAnyPackage("com.fasterxml.jackson..", "io.swagger..");

    @ArchTest
    static ArchRule layer_dependencies_are_respected = layeredArchitecture()
            .layer(TechnicalLayer.API.getName()).definedBy(TechnicalLayer.API.getAbsolutePath())
            .layer(TechnicalLayer.USE_CASES.getName()).definedBy(TechnicalLayer.USE_CASES.getAbsolutePath())
            .layer(TechnicalLayer.DOMAIN.getName()).definedBy(TechnicalLayer.DOMAIN.getAbsolutePath())
            .layer(TechnicalLayer.CONFIGURATION.getName()).definedBy(TechnicalLayer.CONFIGURATION.getAbsolutePath())
            .layer(TechnicalLayer.UTIL.getName()).definedBy(TechnicalLayer.UTIL.getAbsolutePath())
            .layer(TechnicalLayer.AOP.getName()).definedBy(TechnicalLayer.AOP.getAbsolutePath())

            .whereLayer(TechnicalLayer.API.getName()).mayOnlyBeAccessedByLayers(
                    TechnicalLayer.AOP.getName()
            )
            .whereLayer(TechnicalLayer.USE_CASES.getName()).mayOnlyBeAccessedByLayers(
                    TechnicalLayer.API.getName(),
                    TechnicalLayer.CONFIGURATION.getName(),
                    TechnicalLayer.AOP.getName()
            )
            .whereLayer(TechnicalLayer.DOMAIN.getName()).mayOnlyBeAccessedByLayers(
                    TechnicalLayer.USE_CASES.getName(),
                    TechnicalLayer.CONFIGURATION.getName()
            )
            .whereLayer(TechnicalLayer.CONFIGURATION.getName()).mayNotBeAccessedByAnyLayer()
            .whereLayer(TechnicalLayer.UTIL.getName()).mayOnlyBeAccessedByLayers(
                    TechnicalLayer.API.getName(),
                    TechnicalLayer.USE_CASES.getName(),
                    TechnicalLayer.DOMAIN.getName(),
                    TechnicalLayer.CONFIGURATION.getName()
            )
            .whereLayer(TechnicalLayer.AOP.getName()).mayNotBeAccessedByAnyLayer()

            .ignoreDependency(alwaysTrue(), assignableTo(RuntimeException.class))
            .because("RuntimeExceptions are allowed to bubble-up");

    @ArchTest
    static ArchRule use_abstractions_instead_of_concretions = noClasses()
            .that().resideOutsideOfPackages(
                    TechnicalLayer.CONFIGURATION.getAbsolutePath(),
                    "..impl.."
            )
            .and().areNotAnnotatedWith(ControllerAdvice.class)
            .should().dependOnClassesThat()
            .resideInAnyPackage("..impl..");
}
