package bank;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
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
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/// /////////////////////////////////////////////////
// TODO:
// generate pdf of screenshots at end of execution
// break out into screens (setup, game home, and roll) - locators, actions, verification
// add catch for element not found
// assertions fail appropriately and reported in results
/// /////////////////////////////////////////////////

public class StepDefinitions {
    // region BANK TEST BASE ----------------------------------------------------------------
    public WebDriver driver;
    public final String PATH_ROOT = System.getProperty("user.dir");
    public final String PATH_SCREENSHOTS = PATH_ROOT + "/reports/screenshots/";
    public Properties appConfigs = loadProperties();
    public final String APP_URL = appConfigs.getProperty("APP_URL");
    public final int QUICK_WAIT = 1;
    public final int SMALL_WAIT = 5;

    private Properties loadProperties() {
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(PATH_ROOT + "/test.properties"));
            return appProps;
        } catch (FileNotFoundException e) {
            System.out.println("test properties file not found: " + e.getMessage());
            return appProps;
        } catch (IOException e) {
            System.out.println("Error occured while loading properties: " + e.getMessage());
            return appProps;
        }
    }

    private void takeScreenshot(String fileName) {
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path targetFile = Paths.get(PATH_SCREENSHOTS + fileName.replace(" ", "-") + ".png");
            Files.copy(srcFile.toPath(), targetFile);
        } catch (IOException e) {
            System.out.println("An I/O error occurred while taking a screenshot: " + e.getMessage());
        }
    }
    //endregion  ----------------------------------------------------------------

    //region HOOKS  ----------------------------------------------------------------
    @Before
    public void createDriver() {
        driver = new ChromeDriver();
    }

    @Before
    public void deleteScreenshots() {
        File directory = new File(PATH_SCREENSHOTS);
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

    @After
    public void quitDriver() {
        driver.quit();
    }
    //endregion  ----------------------------------------------------------------------------

    //region GENERAL  ----------------------------------------------------------------
    @Given("user navigates to Bank app")
    public void navigateToBankApp() {
        driver.get(APP_URL);
    }

    @Then("verify {string} full screen message is displayed {string}")
    public void verifyFullScreenMessage(String expectedMessage, String stepId) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(QUICK_WAIT));
        String actualMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("full-screen-message"))).getText();
        assertEquals(expectedMessage, actualMessage);
        takeScreenshot(stepId);
        // wait until full screen message is dismissed
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("full-screen-message")));
    }
    //endregion  ----------------------------------------------------------------

    //region SETUP SCREEN  ----------------------------------------------------------------
    @When("user adds player name {string}")
    public void addPlayerName(String playerName) {
        WebElement playerInput = driver.findElement(By.xpath("//*[@class='playerName']"));
        playerInput.sendKeys(playerName);
    }

    @When("user adds the following players to the game:")
    public void addPlayersToGame(List<String> playerNames) {
        for (int i=0; i < playerNames.size(); i++) {
            WebElement playerInput = driver.findElement(By.xpath("(//*[@class='playerName'])[last()]"));
            playerInput.sendKeys(playerNames.get(i));
            // if not the last player, tap the plus button
            if (i != playerNames.size() - 1) {
                driver.findElement(By.id("addPlayerButton")).click();
            }
        }
    }

    @When("^user selects (10|15|20) rounds for the game")
    public void selectRoundsForGame(String rounds) {
        driver.findElement(By.xpath("//*[text()='" + rounds + "']")).click();
    }

    @When("user taps Start Game button")
    public void startGame() {
        driver.findElement(By.id("start-game")).click();
    }

    @Then("verify bank game setup screen is displayed")
    public void verifyGameSetupScreen() {
        String actualGameTitle = driver.findElement(By.id("title")).getText();
        assertEquals("\uD83E\uDD11 BANK \uD83E\uDD11", actualGameTitle);
    }

    @Then("^verify (10|15|20) rounds for the game has been selected \"([^\"]+)\"")
    public void verifyRoundsSelected(String rounds, String stepID) {
        String actualSelected = driver.findElement(By.className("selected-round-option")).getText();
        assertEquals(rounds, actualSelected);
        takeScreenshot(stepID);
    }
    //endregion  ----------------------------------------------------------------

    //region GAME HOME SCREEN  ----------------------------------------------------------------
    @When("user taps Roll button")
    public void tapRollButton() {
        driver.findElement(By.id("play-button")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(QUICK_WAIT));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("game-roll")));
    }

    @Then("verify ROUND {int} of {int} is displayed {string}")
    public void verifyRoundDuringGame(int currentRound, int totalRounds, String stepId) {
        String expectedRounds = String.format("ROUND: %d/%d", currentRound, totalRounds);
        String actualRounds = driver.findElement(By.id("rounds")).getText();
        assertEquals(expectedRounds, actualRounds);
        takeScreenshot(stepId);
    }

    @Then("verify ROLL of {int} is displayed {string}")
    public void verifyRollNumberForRound(int currentRoll, String stepId) {
        String expectedRoll = String.format("ROLL: %d", currentRoll);
        String actualRoll = driver.findElement(By.id("roll")).getText();
        assertEquals(expectedRoll, actualRoll);
        takeScreenshot(stepId);
    }

    @Then("verify CURRENT ROUND POINTS of {int} is displayed {string}")
    public void verifyCurrentRoundPoints(int expectedRoundPoints, String stepId) {
        int actualRoundPoints = Integer.parseInt(driver.findElement(By.id("round-points")).getText());
        assertEquals(expectedRoundPoints, actualRoundPoints);
        takeScreenshot(stepId);
    }

    @Then("verify the game leaderboard displayed as follows {string}:")
    public void verifyLeaderboardValues(String stepId, Map<String, String> expectedLeaderboardData) {
        takeScreenshot(stepId);
        try {
            //TODO: cleanup wait - separate from method
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(SMALL_WAIT));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("game-leaderboard")));
            int i = 0;
            for (Map.Entry<String, String> entry : expectedLeaderboardData.entrySet()) {
                String expectedName = entry.getKey();
                String expectedScore = entry.getValue();
                String actualName = driver.findElement(By.id(String.format("player-%d-name", i))).getText();
                String actualScore = driver.findElement(By.id(String.format("player-%d-score", i))).getText();
                assertEquals(expectedName, actualName, "Leaderboard player name at index: " + i);
                assertEquals(expectedScore, actualScore, "Leaderboard player score at index: " + i);
                i++;
            }
        } catch (AssertionError e) {
            System.out.println("Assertion failed: " + e.getMessage());
        }
    }

    @Then("^verify the (2|12|DBL) roll button is (enabled|disabled)")
    public void verifyButtonEnabledState(String rollValue, String enabledOrDisabled) {
        boolean expectedEnabled = enabledOrDisabled.equals("enabled");
        boolean actualEnabled = driver.findElement(By.xpath("//*[text()='" + rollValue + "']")).isEnabled();
        assertEquals(expectedEnabled, actualEnabled, rollValue + " button is enabled/disabled");
    }

    @Then("verify initial roll screen is displayed {string}")
    public void verifyInitialRollScreen(String stepId) {
        takeScreenshot(stepId);
    }

    @Then("verify current player turn is displayed as {string} {string}")
    public void verifyPlayerTurn(String playerName, String stepId) {
        try {
            String actualPlayerTurn = driver.findElement(By.id("currentPlayerToRoll")).getText();
            assertEquals(playerName, actualPlayerTurn, "current players turn");
            takeScreenshot(stepId);
        } catch (AssertionError e) {
            System.out.println("Assertion failed: " + e.getMessage());
            takeScreenshot(stepId + "_FAIL");
        }

    }
    //endregion  ----------------------------------------------------------------

    //region ROLL SCREEN  ----------------------------------------------------------------
    @When("current player rolls {string}")
    public void playerRoll(String rollValue) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(SMALL_WAIT));
            WebElement rollButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='" + rollValue + "']")));
            rollButton.click();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @When("users play through initial rolls")
    public void playThroughInitialRolls() {
        playerRoll("7");
        playerRoll("7");
        playerRoll("7");
    }

    @When("users play through round {int} of {int} - Step {int}")
    public void playThroughRound(int currentRound, int totalRounds, int stepNum) {
        // initial rolls and Live!
        playThroughInitialRolls();
        verifyFullScreenMessage("we're LIVE!", "Step_" + stepNum + "a_Users play 3 turns - full screen message indicating game is live is displayed");
        // end round
        playerRoll("7");
        int newRound = currentRound + 1;
        String newRoundMessage = totalRounds == newRound ? "LAST ROUND" : String.format("ROUND %d", newRound);
        verifyFullScreenMessage(newRoundMessage, String.format("Step_%db_User rolls a 7 - round ends and %s full screen message is displayed", stepNum, newRoundMessage));
        tapOnCloseButtonOnRollScreen();
        verifyRoundDuringGame(newRound, totalRounds, String.format("Step_%dc_User rolls a 7 - round is incremented to %d of %d", stepNum, newRound, totalRounds));
    }

    @When("user taps bank button on roll screen")
    public void tapOnBankButtonOnRollScreen() {
        driver.findElement(By.id("bank-button")).click();
    }

    @When("user taps close button on roll screen")
    public void tapOnCloseButtonOnRollScreen() {
        driver.findElement(By.id("close-roll")).click();
    }
    //endregion ----------------------------------------------------------------

    //region BANK SCREEN ----------------------------------------------------------------
    @When("user taps bank players button on bank screen")
    public void tapOnBankButtonOnBankScreen() {
        driver.findElement(By.id("bank-players")).click();
    }

    @When("user taps player name {string} on bank screen")
    public void selectPlayerToBank(String playerName) {
        driver.findElement(By.xpath("//label[text()='" + playerName + "']")).click();
    }

    @Then("verify the bank screen is displayed as follows {string}:")
    public void verifyBankablePlayers(String stepId, List<String> bankablePlayers) {
        bankablePlayers = new ArrayList<>(bankablePlayers); //convert to mutable list
        List<WebElement> actualPlayersList = driver.findElements(By.xpath("//div[@id='players-to-bank']/label"));
        List<String> actualBankablePlayers = new ArrayList<>();
        for (WebElement player : actualPlayersList) {
            actualBankablePlayers.add(player.getText());
        }
        // sort lists to account for different player orders
        Collections.sort(bankablePlayers);
        Collections.sort(actualBankablePlayers);
        String expected = String.join(",", bankablePlayers);
        String actual = String.join(",", actualBankablePlayers);

        assertEquals(expected, actual);
        takeScreenshot(stepId);
    }
    //endregion ----------------------------------------------------------------

    //region GAME OVER SCREEN ----------------------------------------------------------------
    @Then("verify Game Results screen is displayed {string}")
    public void verifyGameResultsScreen(String stepId) {
        assertTrue(driver.findElement(By.id("game-end")).isDisplayed());
        takeScreenshot(stepId);
    }

    @Then("verify Game Result displays winner as {string} with {int} points {string}")
    public void verifyGameWinner(String expectedWinnerName, int expectedWinnerPoints, String stepId) {
        assertEquals(expectedWinnerName, driver.findElement(By.id("winner-name")).getText());
        assertEquals(expectedWinnerPoints, Integer.parseInt(driver.findElement(By.id("winner-score")).getText()));
        takeScreenshot(stepId);
    }

    @Then("verify the Game Results leaderboard displayed as follows {string}:")
    public void verifyGameResultsLeaderboard(String stepId, Map<String, String> expectedLeaderboardData) {
        takeScreenshot(stepId);
        try {
            //TODO: cleanup wait - separate from method
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(SMALL_WAIT));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("game-final-leaderboard")));
            int i = 2;
            for (Map.Entry<String, String> entry : expectedLeaderboardData.entrySet()) {
                String expectedName = entry.getKey();
                String expectedScore = entry.getValue();
                String actualName = driver.findElement(By.xpath(String.format("//*[@id=\"leaderboardPlayer-%d\"]/descendant::*[@class=\"leaderboardPlayer-name\"]", i))).getText();
                String actualScore = driver.findElement(By.xpath(String.format("//*[@id=\"leaderboardPlayer-%d\"]/descendant::*[@class=\"leaderboardPlayer-score\"]", i))).getText();
                assertEquals(expectedName, actualName, String.format("Leaderboard player name at index: %d", i - 2));
                assertEquals(expectedScore, actualScore, String.format("Leaderboard player score at index: %d", i - 2));
                i++;
            }
        } catch (AssertionError e) {
            System.out.println("Assertion failed: " + e.getMessage());
        }
    }
    //endregion ----------------------------------------------------------------
}
