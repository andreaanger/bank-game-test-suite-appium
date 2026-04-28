package bank.testsuite;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameResultsTestSuite {
    private final BankTestContext context;

    public GameResultsTestSuite(BankTestContext context) {
        this.context = context;
    }

    public void verifyGameResultsScreen(String stepId) {
        assertTrue(context.gameOverScreen().isDisplayed());
        context.takeScreenshot(stepId);
    }

    public void verifyGameWinner(String expectedWinnerName, int expectedWinnerPoints, String stepId) {
        assertEquals(expectedWinnerName, context.gameOverScreen().getWinnerName());
        assertEquals(expectedWinnerPoints, context.gameOverScreen().getWinnerScore());
        context.takeScreenshot(stepId);
    }

    public void verifyGameResultsLeaderboard(String stepId, Map<String, String> expectedLeaderboardData) {
        context.takeScreenshot(stepId);
        try {
            context.gameOverScreen().waitForFinalLeaderboardVisible();
            int i = 2;
            for (Map.Entry<String, String> entry : expectedLeaderboardData.entrySet()) {
                String expectedName = entry.getKey();
                String expectedScore = entry.getValue();
                String actualName = context.gameOverScreen().getLeaderboardPlayerName(i);
                String actualScore = context.gameOverScreen().getLeaderboardPlayerScore(i);
                assertEquals(expectedName, actualName, String.format("Leaderboard player name at index: %d", i - 2));
                assertEquals(expectedScore, actualScore, String.format("Leaderboard player score at index: %d", i - 2));
                i++;
            }
        } catch (AssertionError e) {
            System.out.println("Assertion failed: " + e.getMessage());
        }
    }
}
