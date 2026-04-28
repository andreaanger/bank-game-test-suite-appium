package bank.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GameOverScreen extends BaseScreen {
    private final By gameEndContainer = By.id("game-end");
    private final By winnerName = By.id("winner-name");
    private final By winnerScore = By.id("winner-score");
    private final By finalLeaderboard = By.id("game-final-leaderboard");

    public GameOverScreen(WebDriver driver, int quickWaitSeconds, int defaultWaitSeconds) {
        super(driver, quickWaitSeconds, defaultWaitSeconds);
    }

    public boolean isDisplayed() {
        return isDisplayed(gameEndContainer);
    }

    public String getWinnerName() {
        return text(winnerName);
    }

    public int getWinnerScore() {
        return Integer.parseInt(text(winnerScore));
    }

    public void waitForFinalLeaderboardVisible() {
        waitForVisible(finalLeaderboard);
    }

    public String getLeaderboardPlayerName(int index) {
        return text(By.xpath(String.format("//*[@id=\"leaderboardPlayer-%d\"]/descendant::*[@class=\"leaderboardPlayer-name\"]", index)));
    }

    public String getLeaderboardPlayerScore(int index) {
        return text(By.xpath(String.format("//*[@id=\"leaderboardPlayer-%d\"]/descendant::*[@class=\"leaderboardPlayer-score\"]", index)));
    }
}
