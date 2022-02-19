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
import io.github.nicolasdesnoust.wordsearch.ocr.usecases.ConvertsWordsPictureUseCase.ConvertsWordsPictureRequest;
import io.github.nicolasdesnoust.wordsearch.ocr.usecases.ConvertsWordsPictureUseCase.ConvertsWordsPictureResponse;
import io.github.nicolasdesnoust.wordsearch.ocr.util.FileUtil;
import io.github.nicolasdesnoust.wordsearch.ocr.util.TextBoundingBoxFixtures;
import io.github.nicolasdesnoust.wordsearch.solver.domain.WordsFactory;
import io.github.nicolasdesnoust.wordsearch.solver.util.WordFixtures;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
public class ConvertsWordsPictureStepDefinitions {

    public static final Duration MAXIMUM_DURATION = Duration.ofMillis(100);

    private final OcrCommonScenarioContext commonScenarioContext;
    private File wordsPicture;
    private TextDetection textDetection;
    private TextRecognition textRecognition;
    private ConvertsWordsPictureUseCase underTest;
    private ConvertsWordsPictureResponse response;

    @Before("@ConvertsWordsPicture")
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

        ConvertsWordsPictureUseCase useCase = new ConvertsWordsPictureUseCase(ocr, new WordsFactory());

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        ValidateRequestAspect aspect = new ValidateRequestAspect(validator);
        AspectJProxyFactory factory = new AspectJProxyFactory(useCase);
        factory.addAspect(aspect);

        underTest = factory.getProxy();
    }

    @Given("a words picture")
    public void aWordsPicture() {
        aWordsPictureWithFormat("jpg");
    }

    @When("I converts the words picture into text")
    public void iConvertsTheWordsPictureIntoText() {
        var request = new ConvertsWordsPictureRequest(wordsPicture);
        response = underTest.convertsWordsPicture(request);
    }

    @Then("I should be given a word list")
    public void iShouldBeGivenAWordList() {
        assertThat(response.getWords()).isNotEmpty();
    }

    @Given("a words picture that takes too much time to converts")
    public void aWordsPictureThatTakesTooMuchTimeToConverts() {
        wordsPicture = FileUtil.getFile("/images/large-picture.jpg");

        when(textDetection.detectBoundingBoxes(wordsPicture, DetectionMode.WORD))
                .thenReturn(TextBoundingBoxFixtures.someTextBoundingBoxes());

        when(textRecognition.recognizeTextInside(any(), eq(wordsPicture)))
                .then(answersWithDelay(Duration.ofMillis(300).toMillis(), invocation -> WordFixtures.someValidWords()));
    }

    @When("I try to convert the words picture into text within the time limit")
    public void iTryToConvertTheWordsPictureIntoTextWithinTheTimeLimit() {
        assertTimeout(
                Duration.ofMillis(500),
                this::iConvertsTheWordsPictureIntoText
        );
    }

    @Then("I should be given a partial word list")
    public void iShouldBeGivenAPartialWordList() {
        assertThat(response.isComplete()).isFalse();
    }

    @Given("a words picture with format {string}")
    public void aWordsPictureWithFormat(String pictureFormat) {
        String picturePath = FileUtil.exists("/images/valid-picture." + pictureFormat)
                ? "/images/valid-picture." + pictureFormat
                : "/images/invalid-picture." + pictureFormat;
        wordsPicture = FileUtil.getFile(picturePath);

        List<TextBoundingBox> boundingBoxes = TextBoundingBoxFixtures.oneTextBoundingBox();
        when(textDetection.detectBoundingBoxes(wordsPicture, DetectionMode.WORD))
                .thenReturn(boundingBoxes);

        when(textRecognition.recognizeTextInside(boundingBoxes.get(0), wordsPicture))
                .thenReturn(WordFixtures.someValidWords());
    }

    @When("I try to convert the words picture into text")
    public void iTryToConvertTheWordsPictureIntoText() {
        Throwable thrown = catchThrowable(this::iConvertsTheWordsPictureIntoText);
        commonScenarioContext.setThrown(thrown);
    }

    @Given("a blank words picture")
    public void aBlankWordsPicture() {
        wordsPicture = FileUtil.getFile("/images/blank-picture.jpg");

        when(textDetection.detectBoundingBoxes(wordsPicture, DetectionMode.WORD))
                .thenReturn(emptyList());
    }

    @Then("I should be given an empty word list")
    public void iShouldBeGivenAnEmptyWordList() {
        assertThat(response.getWords()).isEmpty();
    }

}

