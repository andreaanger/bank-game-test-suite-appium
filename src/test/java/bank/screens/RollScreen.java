package bank.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RollScreen extends BaseScreen {
    private final By bankButton = By.id("bank-button");
    private final By closeRollButton = By.id("close-roll");

    public RollScreen(WebDriver driver, int quickWaitSeconds, int defaultWaitSeconds) {
        super(driver, quickWaitSeconds, defaultWaitSeconds);
    }

    public void clickRollValue(String rollValue) {
        waitForVisible(By.xpath("//*[text()='" + rollValue + "']")).click();
    }

    public void tapBankButton() {
        click(bankButton);
    }

    public void tapCloseButton() {
        click(closeRollButton);
    }
}
