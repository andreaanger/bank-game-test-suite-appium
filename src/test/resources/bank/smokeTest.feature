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
## Initial rolls needed before a player can bank
    When user taps Roll button
    And current player rolls "7"
    And current player rolls "2"
    And current player rolls "12"
    Then verify "we're LIVE!" full screen message is displayed "Step_9a_3 users roll - full screen message indicating game is live is displayed"
    Then verify CURRENT ROUND POINTS of 84 is displayed "Step_9b_3 users roll - the current round points are displayed as 84"
    Then verify current player turn is displayed as "Andrea" "Step_9c_3 users roll - current player is displayed as Andrea"

## Player points updated on leaderboard when player banks
    When user taps bank button on roll screen
    Then verify the bank screen is displayed as follows "Step_10_User taps bank button on roll screen - bank screen is displayed with all players listed":
      | Beyonce  |
      | Andrea   |
      | Taylor   |
      | Sadie    |
      | Lucy     |
      | Hadley   |
      | Gracie   |

    And user taps player name "Andrea" on bank screen
    And user taps bank players button on bank screen
    And user taps close button on roll screen

    Then verify the game leaderboard displayed as follows "Step_11a_User banks player Andrea - leaderboard is updated with banked points":
      | Andrea   | 84     |
      | Beyonce  | 0      |
      | Taylor   | 0      |
      | Sadie    | 0      |
      | Lucy     | 0      |
      | Hadley   | 0      |
      | Gracie   | 0      |

## Turn updates to next player
    When user taps Roll button
    Then verify current player turn is displayed as "Taylor" "Step_11b_User banks player Andrea - current player is updated to next player"


## Banked players turn is skipped
    When current player rolls "3"
    And current player rolls "4"
    And current player rolls "5"
    And current player rolls "6"
    And current player rolls "8"
    Then verify current player turn is displayed as "Beyonce" "Step_12_Users play 5 turns - current player turn is displayed as Beyonce"

    And user taps close button on roll screen
    Then verify CURRENT ROUND POINTS of 110 is displayed "Step_13_User taps close button - the current round points are displayed as 110"

    When user taps Roll button
    And current player rolls "11"
    Then verify current player turn is displayed as "Taylor" "Step_14_User takes turn - banked player turn is skipped"

## Leaderboard ranking updated when a second player banks
    When user taps bank button on roll screen
    Then verify the bank screen is displayed as follows "Step_15_User taps bank button on roll screen - bank screen is displayed with all active players listed":
      | Beyonce  |
      | Taylor   |
      | Sadie    |
      | Lucy     |
      | Hadley   |
      | Gracie   |

    When user taps player name "Beyonce" on bank screen
    And user taps bank players button on bank screen
    And user taps close button on roll screen
    Then verify the game leaderboard displayed as follows "Step_16_User banks player Beyonce - leaderboard is updated with banked points":
      | Beyonce  | 121    |
      | Andrea   | 84     |
      | Taylor   | 0      |
      | Sadie    | 0      |
      | Lucy     | 0      |
      | Hadley   | 0      |
      | Gracie   | 0      |

## Round ends when all players bank
    When user taps Roll button
    Then verify current player turn is displayed as "Taylor" "Step_17_User taps roll button - current player turn is still Taylor"

    When user taps bank button on roll screen
    And user taps player name "Taylor" on bank screen
    And user taps player name "Sadie" on bank screen
    And user taps player name "Lucy" on bank screen
    And user taps player name "Hadley" on bank screen
    And user taps player name "Gracie" on bank screen
    And user taps bank players button on bank screen
    Then verify "ROUND 3" full screen message is displayed "Step_18a_Users all bank - round ends and round 3 full screen message is displayed"

    When user taps close button on roll screen
    Then verify ROLL of 0 is displayed "Step_18b_Users all bank - roll is reset to 0 for the new round"
    Then verify ROUND 3 of 10 is displayed "Step_18c_Users all bank - round is incremented to 3 of 10"
    Then verify the game leaderboard displayed as follows "Step_18d_Users all bank - leaderboard is updated with banked points":
      | Beyonce  | 121    |
      | Taylor   | 121    |
      | Sadie    | 121    |
      | Lucy     | 121    |
      | Hadley   | 121    |
      | Gracie   | 121    |
      | Andrea   | 84     |

## Turn is updated to the next player
    When user taps Roll button
    Then verify current player turn is displayed as "Sadie" "Step_19_User taps roll button - current player turn is Sadie"

#################
##  GAME OVER  ##
#################

## Round 3
    Then users play through round 3 of 10 - Step 20

## Round 4
    When user taps Roll button
    Then users play through round 4 of 10 - Step 21

## Round 5
    When user taps Roll button
    Then users play through round 5 of 10 - Step 22

## Round 6
    When user taps Roll button
    Then users play through round 6 of 10 - Step 23

## Round 7
    When user taps Roll button
    Then users play through round 7 of 10 - Step 24

## Round 8
    When user taps Roll button
    Then users play through round 8 of 10 - Step 25

## Round 9
    When user taps Roll button
    Then users play through round 9 of 10 - Step 26

## Round 10 - Game Over
    When user taps Roll button
    And users play through initial rolls
    Then verify "we're LIVE!" full screen message is displayed "Step_27_Users play 3 turns - full screen message indicating game is live is displayed"

    When current player rolls "7"
    Then verify "GAME OVER!" full screen message is displayed "Step_28a_User rolls a 7 - round ends and GAME OVER full screen message is displayed"

    Then verify Game Results screen is displayed "Step_28b_User rolls a 7 - round ends and Game Results screen is displayed"
    Then verify Game Result displays winner as "Beyonce" with 121 points "Step_28c_User rolls a 7 - round ends and Game Results displays winner as Beyonce with 121 points"

    Then verify the Game Results leaderboard displayed as follows "Step_28d_User rolls a 7 - round ends and Game Results displays leaderboard with points":
      | Taylor   | 121     |
      | Sadie    | 121     |
      | Lucy     | 121     |
      | Hadley   | 121     |
      | Gracie   | 121     |
      | Andrea   | 84      |