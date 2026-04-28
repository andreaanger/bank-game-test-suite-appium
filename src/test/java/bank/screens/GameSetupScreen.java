package bank.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class GameSetupScreen extends BaseScreen {
    private final By title = By.id("title");
    private final By addPlayerButton = By.id("addPlayerButton");
    private final By startGameButton = By.id("start-game");
    private final By selectedRound = By.className("selected-round-option");
    private final By latestPlayerInput = By.xpath("(//*[@class='playerName'])[last()]");

    public GameSetupScreen(WebDriver driver, int quickWaitSeconds, int defaultWaitSeconds) {
        super(driver, quickWaitSeconds, defaultWaitSeconds);
    }

    public void addPlayerName(String playerName) {
        type(latestPlayerInput, playerName);
    }

    public void addPlayers(List<String> playerNames) {
        for (int i = 0; i < playerNames.size(); i++) {
            addPlayerName(playerNames.get(i));
            if (i != playerNames.size() - 1) {
                click(addPlayerButton);
            }
        }
    }

    public void selectRounds(String rounds) {
        click(By.xpath("//*[text()='" + rounds + "']"));
    }

    public void startGame() {
        click(startGameButton);
    }

    public String getTitle() {
        return text(title);
    }

    public String getSelectedRound() {
        return text(selectedRound);
    }
}
