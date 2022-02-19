@ConvertsGridPicture
Feature: Converts Grid Picture
  Use Optical Character Recognition (OCR) to convert a picture of a grid of letters into text.

  As a user,
  I want to converts grid pictures into text automatically
  so that I don't have to copy the grids by hand.

  Scenario: Should converts grid pictures
    Given a grid picture
    When I converts the grid picture into text
    Then I should be given a grid

  Scenario: Should interrupt picture conversions that takes too long
    Given a grid picture that takes too much time to converts
    When I try to convert the grid picture into text within the time limit
    Then I should be given a partial grid

  Scenario Outline: Should accepts whitelisted picture formats
    Given a grid picture with format "<picture format>"
    When I converts the grid picture into text
    Then I should be given a grid
    Examples:
      | picture format |
      | jpg            |
      | jpeg           |
      | bmp            |
      | png            |

  Scenario Outline: Should reject all file formats that are not in the white list
  This scenario obviously cannot test all file formats,
  so we have to make sure that the allowed formats are whitelisted and that all others are rejected by default.
    Given a grid picture with format "<given file format>"
    When I try to convert the grid picture into text
    Then I should be told that the file format "<actual file format>" is unsupported
    Examples:
      | given file format | actual file format |
      | exe               | exe                |
      | gif               | gif                |
      | rar               | rar                |
      | jsp               | jsp                |
      | html              | html               |
      | jpg.exe           | exe                |

  Scenario: Should converts grid pictures with no text
    Given a blank grid picture
    When I converts the grid picture into text
    Then I should be given an empty grid