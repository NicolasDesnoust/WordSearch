package io.github.nicolasdesnoust.wordsearch.ocr.usecases;

import io.cucumber.java.en.Then;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.OpticalCharacterRecognitionImpl.UnsupportedFormatException;
import lombok.RequiredArgsConstructor;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class OcrCommonStepDefinitions {

    private final OcrCommonScenarioContext commonScenarioContext;

    @Then("I should be told that the file format {string} is unsupported")
    public void iShouldBeToldThatTheFileFormatIsUnsupported(String fileFormat) {
        assertThat(commonScenarioContext.getThrown())
                .isInstanceOf(UnsupportedFormatException.class)
                .hasMessageContaining(fileFormat);
    }

}
