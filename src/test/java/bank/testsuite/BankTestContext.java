package bank.testsuite;

import bank.screens.BankPlayerScreen;
import bank.screens.GameHomeScreen;
import bank.screens.GameOverScreen;
import bank.screens.GameSetupScreen;
import bank.screens.RollScreen;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankTestContext {
    private final WebDriver driver;
    private final String pathRoot;
    private final String pathScreenshots;
    private final Properties appConfigs;
    private final String appUrl;
    private final int quickWaitSeconds;
    private final int smallWaitSeconds;

    private GameSetupScreen gameSetupScreen;
    private GameHomeScreen gameHomeScreen;
    private RollScreen rollScreen;
    private BankPlayerScreen bankPlayerScreen;
    private GameOverScreen gameOverScreen;

    public BankTestContext(WebDriver driver, int quickWaitSeconds, int smallWaitSeconds) {
        this.driver = driver;
        this.quickWaitSeconds = quickWaitSeconds;
        this.smallWaitSeconds = smallWaitSeconds;
        this.pathRoot = System.getProperty("user.dir");
        this.pathScreenshots = pathRoot + "/reports/screenshots/";
        this.appConfigs = loadProperties();
        this.appUrl = appConfigs.getProperty("APP_URL");
    }

    public WebDriver getDriver() {
        return driver;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public int getQuickWaitSeconds() {
        return quickWaitSeconds;
    }

    public int getSmallWaitSeconds() {
        return smallWaitSeconds;
    }

    public GameSetupScreen gameSetupScreen() {
        if (gameSetupScreen == null) {
            gameSetupScreen = new GameSetupScreen(driver, quickWaitSeconds, smallWaitSeconds);
        }
        return gameSetupScreen;
    }

    public GameHomeScreen gameHomeScreen() {
        if (gameHomeScreen == null) {
            gameHomeScreen = new GameHomeScreen(driver, quickWaitSeconds, smallWaitSeconds);
        }
        return gameHomeScreen;
    }

    public RollScreen rollScreen() {
        if (rollScreen == null) {
            rollScreen = new RollScreen(driver, quickWaitSeconds, smallWaitSeconds);
        }
        return rollScreen;
    }

    public BankPlayerScreen bankPlayerScreen() {
        if (bankPlayerScreen == null) {
            bankPlayerScreen = new BankPlayerScreen(driver, quickWaitSeconds, smallWaitSeconds);
        }
        return bankPlayerScreen;
    }

    public GameOverScreen gameOverScreen() {
        if (gameOverScreen == null) {
            gameOverScreen = new GameOverScreen(driver, quickWaitSeconds, smallWaitSeconds);
        }
        return gameOverScreen;
    }

    public void takeScreenshot(String fileName) {
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path targetFile = Paths.get(pathScreenshots + fileName.replace(" ", "-") + ".png");
            Files.copy(srcFile.toPath(), targetFile);
        } catch (IOException e) {
            System.out.println("An I/O error occurred while taking a screenshot: " + e.getMessage());
        }
    }

    public void deleteScreenshots() {
        File directory = new File(pathScreenshots);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    boolean deleted = file.delete();
                    if (!deleted) {
                        System.out.println("Failed to delete screenshot: " + file.getName());
                    }
                }
            }
        }
    }

    public void verifyFullScreenMessage(String expectedMessage, String stepId) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(quickWaitSeconds));
        String actualMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("full-screen-message"))).getText();
        assertEquals(expectedMessage, actualMessage);
        takeScreenshot(stepId);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("full-screen-message")));
    }

    private Properties loadProperties() {
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(pathRoot + "/test.properties"));
            return appProps;
        } catch (FileNotFoundException e) {
            System.out.println("test properties file not found: " + e.getMessage());
            return appProps;
        } catch (IOException e) {
            System.out.println("Error occured while loading properties: " + e.getMessage());
            return appProps;
        }
    }
}
