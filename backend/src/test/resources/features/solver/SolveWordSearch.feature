@SolveWordSearch
Feature: Solve Word Search
  Find all words in word search puzzles.
  Word search puzzles are a letter puzzle game whose goal is to find in a grid of letters a given list of words.

  Scenario Outline: Should find a word in each direction
    Given a grid of letters that contains a word in direction "<direction>"
    When I solve the word search puzzle
    Then I should be given the direction "<direction>"
    And I should be given the word for the direction "<direction>"
    And I should be given the word location for the direction "<direction>"
    Examples:
      | direction                |
      | LEFT_TO_RIGHT            |
      | RIGHT_TO_LEFT            |
      | TOP_TO_BOTTOM            |
      | BOTTOM_TO_TOP            |
      | TOP_LEFT_TO_BOTTOM_RIGHT |
      | BOTTOM_RIGHT_TO_TOP_LEFT |
      | TOP_RIGHT_TO_BOTTOM_LEFT |
      | BOTTOM_LEFT_TO_TOP_RIGHT |

  Scenario: Should return back the grid of letters
    Given a grid of letters
    When I solve the word search puzzle
    Then I should be given back the grid

  Scenario: Should tell that grids with words everywhere have no unused letters
    Given a grid with words everywhere
    When I solve the word search puzzle
    Then I should be told that there is no unused letters

  Scenario: Should tell which letters are unused
    Given a grid that contains one word
    When I solve the word search puzzle
    Then I should be told which letters are unused

  Scenario: Should not find words in grids that contains no words
    Given a grid that contains no words
    When I solve the word search puzzle
    Then I should be given no word locations

  Scenario: Should tell all letters are unused in grids that contains no words
    Given a grid that contains no words
    When I solve the word search puzzle
    Then all letters of the grid should be unused

  Scenario: Should fill holes in grids
    Given a grid with rows of different sizes
    When I solve the word search puzzle
    Then I should be given back the grid with the holes filled with spaces

  Scenario: Should uppercase grid letters
    Given a grid with lowercase letters
    When I solve the word search puzzle
    Then I should be given back the grid with uppercase letters

  Scenario: Should ignore case
    Given a grid and a list of words of different case
    When I solve the word search puzzle
    Then I should be given the word locations regardless of their case

  Scenario Outline: Should reject invalid grids
    Given an invalid grid that is "<grid>"
    When I try to solve the word search puzzle
    Then I should be told "grid" is invalid
    Examples:
      | grid      |
      | EMPTY     |
      | BLANK     |
      | NULL      |
      | TOO_LARGE |

  Scenario Outline: Should reject invalid word lists
    Given an invalid word list that is "<words>"
    When I try to solve the word search puzzle
    Then I should be told "words" are invalid
    Examples:
      | words     |
      | EMPTY     |
      | BLANK     |
      | NULL      |
      | TOO_LARGE |

  Scenario: Should strip accents of grid letters
    Given a grid with accents on its letters
    When I solve the word search puzzle
    Then I should be given back the grid without accents

  Scenario: Should strip accents of words
    Given a list of words with accents on their letters
    When I solve the word search puzzle
    Then I should be given the words found without accents

  Scenario: Should ignore duplicate words
    Given a list of words with duplicates
    When I solve the word search puzzle
    Then I should be given the duplicates location only once