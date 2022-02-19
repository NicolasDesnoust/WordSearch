@ConvertsWordsPicture
Feature: Converts Words Picture
  Use Optical Character Recognition (OCR) to convert a picture of a list of words into text.

  As a user,
  I want to converts words pictures into text automatically
  so that I don't have to copy the words by hand.

  Scenario: Should converts words pictures
    Given a words picture
    When I converts the words picture into text
    Then I should be given a word list

  Scenario: Should interrupt picture conversions that takes too long
    Given a words picture that takes too much time to converts
    When I try to convert the words picture into text within the time limit
    Then I should be given a partial word list

  Scenario Outline: Should accepts whitelisted picture formats
    Given a words picture with format "<picture format>"
    When I converts the words picture into text
    Then I should be given a word list
    Examples:
      | picture format |
      | jpg            |
      | jpeg           |
      | bmp            |
      | png            |

  Scenario Outline: Should reject all file formats that are not in the white list
  This scenario obviously cannot test all file formats,
  so we have to make sure that the allowed formats are whitelisted and that all others are rejected by default.
    Given a words picture with format "<given file format>"
    When I try to convert the words picture into text
    Then I should be told that the file format "<actual file format>" is unsupported
    Examples:
      | given file format | actual file format |
      | exe               | exe                |
      | gif               | gif                |
      | rar               | rar                |
      | jsp               | jsp                |
      | html              | html               |
      | jpg.exe           | exe                |

  Scenario: Should converts words pictures with no text
    Given a blank words picture
    When I converts the words picture into text
    Then I should be given an empty word list