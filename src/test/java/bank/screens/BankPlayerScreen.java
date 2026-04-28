package bank.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class BankPlayerScreen extends BaseScreen {
    private final By bankPlayersButton = By.id("bank-players");
    private final By playersToBankLabels = By.xpath("//div[@id='players-to-bank']/label");

    public BankPlayerScreen(WebDriver driver, int quickWaitSeconds, int defaultWaitSeconds) {
        super(driver, quickWaitSeconds, defaultWaitSeconds);
    }

    public void tapBankPlayersButton() {
        click(bankPlayersButton);
    }

    public void selectPlayer(String playerName) {
        click(By.xpath("//label[text()='" + playerName + "']"));
    }

    public List<String> getBankablePlayers() {
        List<WebElement> playerElements = findAll(playersToBankLabels);
        List<String> names = new ArrayList<>();
        for (WebElement playerElement : playerElements) {
            names.add(playerElement.getText());
        }
        return names;
    }
}
