package io.github.nicolasdesnoust.wordsearch.solver.usecases;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.github.nicolasdesnoust.wordsearch.architecture.Layers.ABSOLUTE_PATH_OF_BASE_PACKAGE;

/**
 * Entry point of all Cucumber acceptance tests for the module it belongs to.
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/solver")
@ConfigurationParameter(
        key = GLUE_PROPERTY_NAME,
        value = ABSOLUTE_PATH_OF_BASE_PACKAGE + ".solver.usecases"
)
@SuppressWarnings("squid:S2187")
public class SolverAcceptanceTests {
}
