package io.github.nicolasdesnoust.wordsearch.ocr.usecases;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.nicolasdesnoust.wordsearch.core.aop.ValidateRequestAspect;
import io.github.nicolasdesnoust.wordsearch.ocr.configuration.OcrConfigurationProperties;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.OcrOptions.DetectionMode;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.OpticalCharacterRecognition;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.OpticalCharacterRecognitionImpl;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.TextBoundingBox;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.TextDetection;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.TextRecognition;
import io.github.nicolasdesnoust.wordsearch.ocr.domain.util.StopWatchFactory;
import io.github.nicolasdesnoust.wordsearch.ocr.infrastructure.secondary.stopwatch.SpringStopWatchFactory;
import io.github.nicolasdesnoust.wordsearch.ocr.usecases.ConvertsGridPictureUseCase.ConvertsGridPictureRequest;
import io.github.nicolasdesnoust.wordsearch.ocr.usecases.ConvertsGridPictureUseCase.ConvertsGridPictureResponse;
import io.github.nicolasdesnoust.wordsearch.ocr.util.FileUtil;
import io.github.nicolasdesnoust.wordsearch.ocr.util.TextBoundingBoxFixtures;
import io.github.nicolasdesnoust.wordsearch.solver.domain.GridFactory;
import io.github.nicolasdesnoust.wordsearch.solver.util.GridFixtures;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import javax.validation.Validation;
import javax.validation.Validator;
import java.io.File;
import java.time.Duration;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.mockito.AdditionalAnswers.answersWithDelay;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;

@RequiredArgsConstructor
public class ConvertsGridPictureStepDefinitions {

    private static final Duration MAXIMUM_DURATION = Duration.ofMillis(100);

    private final OcrCommonScenarioContext commonScenarioContext;
    private TextDetection textDetection;
    private TextRecognition textRecognition;
    private ConvertsGridPictureUseCase underTest;
    private ConvertsGridPictureResponse response;
    private File gridPicture;

    @Before("@ConvertsGridPicture")
    public void setup() {
        textDetection = mock(TextDetection.class);
        textRecognition = mock(TextRecognition.class);
        var ocrConfiguration = OcrConfigurationProperties.builder()
                .withDataPath(Strings.EMPTY)
                .withMaximumDuration(MAXIMUM_DURATION.toMillis())
                .build();

        StopWatchFactory stopWatchFactory = new SpringStopWatchFactory();
        OpticalCharacterRecognition ocr = new OpticalCharacterRecognitionImpl(
                textDetection, textRecognition, ocrConfiguration, stopWatchFactory);
        ConvertsGridPictureUseCase useCase = new ConvertsGridPictureUseCase(ocr, new GridFactory());

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        ValidateRequestAspect aspect = new ValidateRequestAspect(validator);
        AspectJProxyFactory factory = new AspectJProxyFactory(useCase);
        factory.addAspect(aspect);

        underTest = factory.getProxy();
    }

    @Given("a grid picture")
    public void aGridPicture() {
        aGridPictureWithFormat("jpg");
    }

    @When("I converts the grid picture into text")
    public void iConvertsTheGridPictureIntoText() {
        var request = new ConvertsGridPictureRequest(gridPicture);
        response = underTest.convertsGridPicture(request);
    }

    @Then("I should be given a grid")
    public void iShouldBeGivenAGrid() {
        assertThat(response.getGrid()).isNotEmpty();
    }

    @Given("a grid picture that takes too much time to converts")
    public void aGridPictureThatTakesTooMuchTimeToConverts() {
        gridPicture = FileUtil.getFile("/images/large-picture.jpg");

        given(textDetection.detectBoundingBoxes(gridPicture, DetectionMode.GRID))
                .willReturn(TextBoundingBoxFixtures.someTextBoundingBoxes());

        given(textRecognition.recognizeTextInside(any(), eq(gridPicture)))
                .will(answersWithDelay(Duration.ofMillis(300).toMillis(), invocation -> GridFixtures.aValidGrid()));
    }

    @When("I try to convert the grid picture into text within the time limit")
    public void iTryToConvertTheGridPictureIntoTextWithinTheTimeLimit() {
        assertTimeout(
                Duration.ofMillis(500),
                this::iConvertsTheGridPictureIntoText
        );
    }

    @Then("I should be given a partial grid")
    public void iShouldBeGivenAPartialGrid() {
        assertThat(response.isComplete()).isFalse();
    }

    @Given("a grid picture with format {string}")
    public void aGridPictureWithFormat(String pictureFormat) {
        String picturePath = FileUtil.exists("/images/valid-picture." + pictureFormat)
                ? "/images/valid-picture." + pictureFormat
                : "/images/invalid-picture." + pictureFormat;
        gridPicture = FileUtil.getFile(picturePath);

        List<TextBoundingBox> boundingBoxes = TextBoundingBoxFixtures.oneTextBoundingBox();
        given(textDetection.detectBoundingBoxes(gridPicture, DetectionMode.GRID))
                .willReturn(boundingBoxes);

        given(textRecognition.recognizeTextInside(boundingBoxes.get(0), gridPicture))
                .willReturn(GridFixtures.aValidGrid());
    }

    @When("I try to convert the grid picture into text")
    public void iTryToConvertTheGridPictureIntoText() {
        Throwable thrown = catchThrowable(this::iConvertsTheGridPictureIntoText);
        commonScenarioContext.setThrown(thrown);
    }

    @Given("a blank grid picture")
    public void aBlankGridPicture() {
        gridPicture = FileUtil.getFile("/images/blank-picture.jpg");

        given(textDetection.detectBoundingBoxes(gridPicture, DetectionMode.GRID))
                .willReturn(emptyList());
    }

    @Then("I should be given an empty grid")
    public void iShouldBeGivenAnEmptyGrid() {
        assertThat(response.getGrid()).isEmpty();
    }

}

