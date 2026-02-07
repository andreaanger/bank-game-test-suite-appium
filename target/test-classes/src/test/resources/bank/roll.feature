Feature: Bank Game - Roll Feature

Background:
    Given user navigates to Bank app
    And user adds the following players to the game:
    | Beyonce  |
    | Andrea   |
    | Taylor   |
    And user selects 10 rounds for the game
    And user taps Start Game button

  Scenario: rolling a 7 is 70 points in free roll, ends round when live
    ## ROLL 1
    When user taps Roll button
    And current player rolls "7"
    Then verify CURRENT ROUND POINTS of 70 is displayed

    ## ROLL 2
    When current player rolls "7"
    Then verify CURRENT ROUND POINTS of 140 is displayed

    ## ROLL 3
    When current player rolls "7"
    Then verify CURRENT ROUND POINTS of 210 is displayed

    ## Roll 4 - We're Live!
    When current player rolls "7"
    Then verify ROLL of 0 is displayed
    Then verify ROUND 2 of 10 is displayed
    Then verify CURRENT ROUND POINTS of 0 is displayed


  Scenario: rolling a 2 is 2 points in free roll, disabled once we're live

    ## ROLL 1
    When user taps Roll button
    Then verify the 2 roll button is enabled

    When current player rolls "2"
    Then verify CURRENT ROUND POINTS of 2 is displayed

    ## ROLL 2
    Then verify the 2 roll button is enabled

    When current player rolls "2"
    Then verify CURRENT ROUND POINTS of 4 is displayed
    Then verify the 2 roll button is enabled

    ## ROLL 3
    Then verify the 2 roll button is enabled

    When current player rolls "2"
    Then verify CURRENT ROUND POINTS of 6 is displayed

    ## Roll 4 - We're Live!
    Then verify the 2 roll button is disabled