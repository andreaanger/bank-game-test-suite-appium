package bank.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BaseScreen {
    protected final WebDriver driver;
    private final int quickWaitSeconds;
    private final int defaultWaitSeconds;

    public BaseScreen(WebDriver driver, int quickWaitSeconds, int defaultWaitSeconds) {
        this.driver = driver;
        this.quickWaitSeconds = quickWaitSeconds;
        this.defaultWaitSeconds = defaultWaitSeconds;
    }

    protected WebElement find(By locator) {
        return driver.findElement(locator);
    }

    protected List<WebElement> findAll(By locator) {
        return driver.findElements(locator);
    }

    protected WebElement waitForVisible(By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(defaultWaitSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForVisible(By locator, int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public String waitForText(By locator, int seconds) {
        return waitForVisible(locator, seconds).getText();
    }

    public void waitForInvisible(By locator, int seconds) {
        new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected void click(By locator) {
        find(locator).click();
    }

    protected void type(By locator, String text) {
        find(locator).sendKeys(text);
    }

    protected String text(By locator) {
        return find(locator).getText();
    }

    protected boolean isDisplayed(By locator) {
        return find(locator).isDisplayed();
    }

    protected boolean isEnabled(By locator) {
        return find(locator).isEnabled();
    }

    public int getQuickWaitSeconds() {
        return quickWaitSeconds;
    }
}
