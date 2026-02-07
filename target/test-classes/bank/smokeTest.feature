Feature: Smoke Test for the Bank Game app

  Scenario: Game play smoke test
##################
##  GAME SETUP  ##
##################
    Given user navigates to Bank app
    Then verify bank game setup screen is displayed

    When user adds the following players to the game:
    | Beyonce  |
    | Andrea   |
    | Taylor   |
    | Sadie    |
    | Lucy     |
    | Hadley   |
    | Gracie   |

    When user selects 10 rounds for the game
    Then verify 10 rounds for the game has been selected "Step_1_User is setting up a new game - 10 rounds are selected by default"

    When user taps Start Game button

##########################################
##  GAME - INITIAL VALUES VERIFICATION  ##
##########################################

    Then verify ROUND 1 of 10 is displayed "Step_2a_User starts a new game - round 1 of 10 is displayed on game home screen"

    Then verify ROLL of 0 is displayed "Step_2b_User starts a new game - roll 0 is displayed on game home screen"

    Then verify CURRENT ROUND POINTS of 0 is displayed "Step_2c_User starts a new game - current round points of 0 is displayed on game home screen"

    Then verify the game leaderboard displayed as follows "Step_2d_User starts a new game - leaderboard with correct values is displayed on game home screen":
      | Beyonce  | 0      |
      | Andrea   | 0      |
      | Taylor   | 0      |
      | Sadie    | 0      |
      | Lucy     | 0      |
      | Hadley   | 0      |
      | Gracie   | 0      |

###################
##  GAME - ROLL  ##
###################

## INITIAL ROLLS - 7 = 70 points
    When user taps Roll button
    Then verify initial roll screen is displayed "Step_3a_User taps roll button - Initial roll screen is displayed"
    Then verify current player turn is displayed as "Beyonce" "Step_3b_User taps roll button - current player of Beyonce is displayed"

    When current player rolls "7"
    Then verify CURRENT ROUND POINTS of 70 is displayed "Step_4a_User rolls a 7 - 70 points are added to the current round points"

## INITIAL ROLLS - 2 is enabled
    Then verify ROLL of 1 is displayed "Step_4b_User rolls a 7 - roll is incremented to 1"

    Then verify current player turn is displayed as "Andrea" "Step_4c_User rolls a 7 - current player is updated to next player"

    When current player rolls "2"
    Then verify CURRENT ROUND POINTS of 72 is displayed "Step_5a_User rolls a 2 - 2 points are added to the current round points"

## INITIAL ROLLS - 12 is enabled
    Then verify ROLL of 2 is displayed "Step_5b_User rolls a 2 - roll is incremented to 2"
    Then verify current player turn is displayed as "Taylor" "Step_5c_User rolls a 2 - current player is updated to next player"

    When current player rolls "12"

## LIVE ROUND
    Then verify "we're LIVE!" full screen message is displayed "Step_6a_User rolls a 12 - full screen message indicating game is live is displayed"
    Then verify CURRENT ROUND POINTS of 84 is displayed "Step_6b_User rolls a 12 - 12 points are added to the current round points"
    Then verify ROLL of 3 is displayed "Step_6c_User rolls a 12 - roll is incremented to 3"
    Then verify current player turn is displayed as "Sadie" "Step_6d_User rolls a 12 - current player is updated to next player"

## LIVE ROUND - roll DBL = double the current round points
    When current player rolls "DBL"
    Then verify CURRENT ROUND POINTS of 168 is displayed "Step_7_User rolls DBL - current rounds points are doubled to 168"

## LIVE ROUND - 7 ends the round
    When current player rolls "7"

## NEXT ROUND
    Then verify "ROUND 2" full screen message is displayed "Step_8a_User rolls a 7 - rpund 2 full screen message is displayed"
    When user taps close button on roll screen
    Then verify CURRENT ROUND POINTS of 0 is displayed "Step_8b_User rolls a 7 - current round points are reset to 0 for the new round"
    Then verify ROLL of 0 is displayed "Step_8c_User rolls a 7 - roll is reset to 0 for the new round"
    Then verify ROUND 2 of 10 is displayed "Step_8d_User rolls a 7 - round is incremented to 2 of 10"
    Then verify the game leaderboard displayed as follows "Step_8e_User rolls a 7 - leaderboard remains unchanged":
      | Beyonce  | 0      |
      | Andrea   | 0      |
      | Taylor   | 0      |
      | Sadie    | 0      |
      | Lucy     | 0      |
      | Hadley   | 0      |
      | Gracie   | 0      |

###################
##  GAME - BANK  ##
###################
### Initial rolls needed before a player can bank
#    When user taps Roll button
#    And current player rolls "7"
#    And current player rolls "2"
#    And current player rolls "12"
#    Then verify "we're LIVE!" full screen message is displayed "Step_9a_3 users roll - full screen message indicating game is live is displayed"
#    Then verify CURRENT ROUND POINTS of 84 is displayed "Step_9b_3 users roll - the current round points are displayed as 84"
#    Then verify current player turn is displayed as "Andrea" "Step_9c_3 users roll - current player is displayed as Andrea"
#
### Player points updated on leaderboard when player banks
#    When user taps bank button on roll screen
#    Then verify the bank screen is displayed as follows "Step_10_User taps bank button on roll screen - bank screen is displayed with all players listed":
#      | Beyonce  |
#      | Andrea   |
#      | Taylor   |
#      | Sadie    |
#      | Lucy     |
#      | Hadley   |
#      | Gracie   |
#
#    And user taps player name "Andrea" on bank screen
#    And user taps bank players button on bank screen
#
#    Then verify the game leaderboard displayed as follows "Step_11_User banks player Andrea - leaderboard is updated with banked points":
#      | Andrea   | 168    |
#      | Beyonce  | 0      |
#      | Taylor   | 0      |
#      | Sadie    | 0      |
#      | Lucy     | 0      |
#      | Hadley   | 0      |
#      | Gracie   | 0      |

## Banked players turn is skipped

## Leaderboard ranking updated when a second player banks

## Round ends when all players bank


#################
##  GAME OVER  ##
#################
