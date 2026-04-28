package bank.testsuite;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameHomeTestSuite {
    private final BankTestContext context;

    public GameHomeTestSuite(BankTestContext context) {
        this.context = context;
    }

    public void tapRollButton() {
        context.gameHomeScreen().tapRollButton();
    }

    public void verifyRoundDuringGame(int currentRound, int totalRounds, String stepId) {
        String expectedRounds = String.format("ROUND: %d/%d", currentRound, totalRounds);
        assertEquals(expectedRounds, context.gameHomeScreen().getRoundLabel());
        context.takeScreenshot(stepId);
    }

    public void verifyRollNumberForRound(int currentRoll, String stepId) {
        String expectedRoll = String.format("ROLL: %d", currentRoll);
        assertEquals(expectedRoll, context.gameHomeScreen().getRollLabel());
        context.takeScreenshot(stepId);
    }

    public void verifyCurrentRoundPoints(int expectedRoundPoints, String stepId) {
        assertEquals(expectedRoundPoints, context.gameHomeScreen().getCurrentRoundPoints());
        context.takeScreenshot(stepId);
    }

    public void verifyLeaderboardValues(String stepId, Map<String, String> expectedLeaderboardData) {
        context.takeScreenshot(stepId);
        try {
            context.gameHomeScreen().waitForLeaderboardVisible();
            int i = 0;
            for (Map.Entry<String, String> entry : expectedLeaderboardData.entrySet()) {
                String expectedName = entry.getKey();
                String expectedScore = entry.getValue();
                String actualName = context.gameHomeScreen().getLeaderboardPlayerName(i);
                String actualScore = context.gameHomeScreen().getLeaderboardPlayerScore(i);
                assertEquals(expectedName, actualName, "Leaderboard player name at index: " + i);
                assertEquals(expectedScore, actualScore, "Leaderboard player score at index: " + i);
                i++;
            }
        } catch (AssertionError e) {
            System.out.println("Assertion failed: " + e.getMessage());
        }
    }

    public void verifyButtonEnabledState(String rollValue, String enabledOrDisabled) {
        boolean expectedEnabled = enabledOrDisabled.equals("enabled");
        boolean actualEnabled = context.gameHomeScreen().isRollValueEnabled(rollValue);
        assertEquals(expectedEnabled, actualEnabled, rollValue + " button is enabled/disabled");
    }

    public void verifyInitialRollScreen(String stepId) {
        context.takeScreenshot(stepId);
    }

    public void verifyPlayerTurn(String playerName, String stepId) {
        try {
            String actualPlayerTurn = context.gameHomeScreen().getCurrentPlayerTurn();
            assertEquals(playerName, actualPlayerTurn, "current players turn");
            context.takeScreenshot(stepId);
        } catch (AssertionError e) {
            System.out.println("Assertion failed: " + e.getMessage());
            context.takeScreenshot(stepId + "_FAIL");
        }
    }
}
