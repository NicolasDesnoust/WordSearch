package io.github.nicolasdesnoust.wordsearch.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.GeneralCodingRules.*;

@AnalyzeClasses(
        packages = Layers.ABSOLUTE_PATH_OF_BASE_PACKAGE,
        importOptions = ImportOption.DoNotIncludeTests.class
)
class CodingRulesTest {

    @ArchTest
    static ArchRule no_access_to_standard_streams = NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;

    @ArchTest
    static ArchRule no_generic_exceptions = NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;

    @ArchTest
    static ArchRule no_java_util_logging = NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING;

    @ArchTest
    static ArchRule no_field_injection = NO_CLASSES_SHOULD_USE_FIELD_INJECTION;

}
