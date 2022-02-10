package io.github.nicolasdesnoust.wordsearch.ocr.domain;

public interface OcrConfiguration {

	/**
	 * OCR jobs are automatically stopped if they exceed this maximum duration.
	 * It is expressed is milliseconds.
	 */
	long getMaximumDuration();

	/**
	 * Location of trained data models.
	 */
	String getDataPath();

	/**
	 * The language of pictures to convert.
	 * 
	 * This language must have a corresponding trained data model in the folder
	 * specified by {@link #getDataPath()}.
	 */
	String getLanguage();
	
}
