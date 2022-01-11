package io.github.nicolasdesnoust.wordsearch;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import io.github.nicolasdesnoust.wordsearch.Layers.FeatureLayer;

import static com.tngtech.archunit.base.DescribedPredicate.not;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.equivalentTo;
import static com.tngtech.archunit.lang.conditions.ArchPredicates.are;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(
        packages = Layers.ABSOLUTE_PATH_OF_BASE_PACKAGE,
        importOptions = ImportOption.DoNotIncludeTests.class
)
class FeatureLayersTest {

    @ArchTest
    static ArchRule cannot_bypass_architectural_rules_by_creating_new_feature_layers = classes()
            .that(are(not(equivalentTo(WordSearchApplication.class))))
            .should().resideInAnyPackage(
                    FeatureLayer.CORE.getAbsolutePath(),
                    FeatureLayer.SOLVER.getAbsolutePath(),
                    FeatureLayer.OCR.getAbsolutePath()
            );

    @ArchTest
    static ArchRule layer_dependencies_are_respected = layeredArchitecture()
            .layer(FeatureLayer.CORE.getName()).definedBy(FeatureLayer.CORE.getAbsolutePath())
            .layer(FeatureLayer.SOLVER.getName()).definedBy(FeatureLayer.SOLVER.getAbsolutePath())
            .layer(FeatureLayer.OCR.getName()).definedBy(FeatureLayer.OCR.getAbsolutePath())

            .whereLayer(FeatureLayer.CORE.getName()).mayOnlyBeAccessedByLayers(
                    FeatureLayer.SOLVER.getName(),
                    FeatureLayer.OCR.getName()
            )
            .whereLayer(FeatureLayer.SOLVER.getName()).mayOnlyBeAccessedByLayers(
                    FeatureLayer.OCR.getName()
            )
            .whereLayer(FeatureLayer.OCR.getName()).mayNotBeAccessedByAnyLayer();
}
