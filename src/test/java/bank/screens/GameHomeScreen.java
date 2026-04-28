package bank.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GameHomeScreen extends BaseScreen {
    private final By playButton = By.id("play-button");
    private final By gameRoll = By.id("game-roll");
    private final By rounds = By.id("rounds");
    private final By roll = By.id("roll");
    private final By roundPoints = By.id("round-points");
    private final By gameLeaderboard = By.id("game-leaderboard");
    private final By currentPlayerToRoll = By.id("currentPlayerToRoll");

    public GameHomeScreen(WebDriver driver, int quickWaitSeconds, int defaultWaitSeconds) {
        super(driver, quickWaitSeconds, defaultWaitSeconds);
    }

    public void tapRollButton() {
        click(playButton);
        waitForVisible(gameRoll, getQuickWaitSeconds());
    }

    public String getRoundLabel() {
        return text(rounds);
    }

    public String getRollLabel() {
        return text(roll);
    }

    public int getCurrentRoundPoints() {
        return Integer.parseInt(text(roundPoints));
    }

    public void waitForLeaderboardVisible() {
        waitForVisible(gameLeaderboard);
    }

    public String getLeaderboardPlayerName(int index) {
        return text(By.id(String.format("player-%d-name", index)));
    }

    public String getLeaderboardPlayerScore(int index) {
        return text(By.id(String.format("player-%d-score", index)));
    }

    public boolean isRollValueEnabled(String rollValue) {
        return isEnabled(By.xpath("//*[text()='" + rollValue + "']"));
    }

    public String getCurrentPlayerTurn() {
        return text(currentPlayerToRoll);
    }
}
