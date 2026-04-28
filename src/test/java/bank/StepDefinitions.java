package bank;

import bank.testsuite.BankPlayerTestSuite;
import bank.testsuite.BankTestContext;
import bank.testsuite.GameHomeTestSuite;
import bank.testsuite.GameResultsTestSuite;
import bank.testsuite.GameSetupTestSuite;
import bank.testsuite.GeneralTestSuite;
import bank.testsuite.RollTestSuite;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.*;

/// /////////////////////////////////////////////////
// TODO:
// generate pdf of screenshots at end of execution
// add catch for element not found
// assertions fail appropriately and reported in results
/// /////////////////////////////////////////////////

public class StepDefinitions {
    private WebDriver driver;
    public final int QUICK_WAIT = 1;
    public final int SMALL_WAIT = 5;
    private BankTestContext context;
    private GeneralTestSuite generalTestSuite;
    private GameSetupTestSuite gameSetupTestSuite;
    private GameHomeTestSuite gameHomeTestSuite;
    private RollTestSuite rollTestSuite;
    private BankPlayerTestSuite bankPlayerTestSuite;
    private GameResultsTestSuite gameResultsTestSuite;

    //region HOOKS  ----------------------------------------------------------------
    @Before(order = 0)
    public void createDriver() {
        driver = new ChromeDriver();
        context = new BankTestContext(driver, QUICK_WAIT, SMALL_WAIT);
        generalTestSuite = new GeneralTestSuite(context);
        gameSetupTestSuite = new GameSetupTestSuite(context);
        gameHomeTestSuite = new GameHomeTestSuite(context);
        rollTestSuite = new RollTestSuite(context, generalTestSuite, gameHomeTestSuite);
        bankPlayerTestSuite = new BankPlayerTestSuite(context);
        gameResultsTestSuite = new GameResultsTestSuite(context);
    }

    @Before(order = 1)
    public void deleteScreenshots() {
        context.deleteScreenshots();
    }

    @After
    public void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
    //endregion  ----------------------------------------------------------------------------

    //region GENERAL  ----------------------------------------------------------------
    @Given("user navigates to Bank app")
    public void navigateToBankApp() {
        generalTestSuite.navigateToBankApp();
    }

    @Then("verify {string} full screen message is displayed {string}")
    public void verifyFullScreenMessage(String expectedMessage, String stepId) {
        generalTestSuite.verifyFullScreenMessage(expectedMessage, stepId);
    }
    //endregion  ----------------------------------------------------------------

    //region SETUP SCREEN  ----------------------------------------------------------------
    @When("user adds player name {string}")
    public void addPlayerName(String playerName) {
        gameSetupTestSuite.addPlayerName(playerName);
    }

    @When("user adds the following players to the game:")
    public void addPlayersToGame(List<String> playerNames) {
        gameSetupTestSuite.addPlayersToGame(playerNames);
    }

    @When("^user selects (10|15|20) rounds for the game")
    public void selectRoundsForGame(String rounds) {
        gameSetupTestSuite.selectRoundsForGame(rounds);
    }

    @When("user taps Start Game button")
    public void startGame() {
        gameSetupTestSuite.startGame();
    }

    @Then("verify bank game setup screen is displayed")
    public void verifyGameSetupScreen() {
        gameSetupTestSuite.verifyGameSetupScreen();
    }

    @Then("^verify (10|15|20) rounds for the game has been selected \"([^\"]+)\"")
    public void verifyRoundsSelected(String rounds, String stepID) {
        gameSetupTestSuite.verifyRoundsSelected(rounds, stepID);
    }
    //endregion  ----------------------------------------------------------------

    //region GAME HOME SCREEN  ----------------------------------------------------------------
    @When("user taps Roll button")
    public void tapRollButton() {
        gameHomeTestSuite.tapRollButton();
    }

    @Then("verify ROUND {int} of {int} is displayed {string}")
    public void verifyRoundDuringGame(int currentRound, int totalRounds, String stepId) {
        gameHomeTestSuite.verifyRoundDuringGame(currentRound, totalRounds, stepId);
    }

    @Then("verify ROLL of {int} is displayed {string}")
    public void verifyRollNumberForRound(int currentRoll, String stepId) {
        gameHomeTestSuite.verifyRollNumberForRound(currentRoll, stepId);
    }

    @Then("verify CURRENT ROUND POINTS of {int} is displayed {string}")
    public void verifyCurrentRoundPoints(int expectedRoundPoints, String stepId) {
        gameHomeTestSuite.verifyCurrentRoundPoints(expectedRoundPoints, stepId);
    }

    @Then("verify the game leaderboard displayed as follows {string}:")
    public void verifyLeaderboardValues(String stepId, Map<String, String> expectedLeaderboardData) {
        gameHomeTestSuite.verifyLeaderboardValues(stepId, expectedLeaderboardData);
    }

    @Then("^verify the (2|12|DBL) roll button is (enabled|disabled)")
    public void verifyButtonEnabledState(String rollValue, String enabledOrDisabled) {
        gameHomeTestSuite.verifyButtonEnabledState(rollValue, enabledOrDisabled);
    }

    @Then("verify initial roll screen is displayed {string}")
    public void verifyInitialRollScreen(String stepId) {
        gameHomeTestSuite.verifyInitialRollScreen(stepId);
    }

    @Then("verify current player turn is displayed as {string} {string}")
    public void verifyPlayerTurn(String playerName, String stepId) {
        gameHomeTestSuite.verifyPlayerTurn(playerName, stepId);
    }
    //endregion  ----------------------------------------------------------------

    //region ROLL SCREEN  ----------------------------------------------------------------
    @When("current player rolls {string}")
    public void playerRoll(String rollValue) {
        rollTestSuite.playerRoll(rollValue);
    }

    @When("users play through initial rolls")
    public void playThroughInitialRolls() {
        rollTestSuite.playThroughInitialRolls();
    }

    @When("users play through round {int} of {int} - Step {int}")
    public void playThroughRound(int currentRound, int totalRounds, int stepNum) {
        rollTestSuite.playThroughRound(currentRound, totalRounds, stepNum);
    }

    @When("user taps bank button on roll screen")
    public void tapOnBankButtonOnRollScreen() {
        rollTestSuite.tapOnBankButtonOnRollScreen();
    }

    @When("user taps close button on roll screen")
    public void tapOnCloseButtonOnRollScreen() {
        rollTestSuite.tapOnCloseButtonOnRollScreen();
    }
    //endregion ----------------------------------------------------------------

    //region BANK SCREEN ----------------------------------------------------------------
    @When("user taps bank players button on bank screen")
    public void tapOnBankButtonOnBankScreen() {
        bankPlayerTestSuite.tapOnBankButtonOnBankScreen();
    }

    @When("user taps player name {string} on bank screen")
    public void selectPlayerToBank(String playerName) {
        bankPlayerTestSuite.selectPlayerToBank(playerName);
    }

    @Then("verify the bank screen is displayed as follows {string}:")
    public void verifyBankablePlayers(String stepId, List<String> bankablePlayers) {
        bankPlayerTestSuite.verifyBankablePlayers(stepId, bankablePlayers);
    }
    //endregion ----------------------------------------------------------------

    //region GAME OVER SCREEN ----------------------------------------------------------------
    @Then("verify Game Results screen is displayed {string}")
    public void verifyGameResultsScreen(String stepId) {
        gameResultsTestSuite.verifyGameResultsScreen(stepId);
    }

    @Then("verify Game Result displays winner as {string} with {int} points {string}")
    public void verifyGameWinner(String expectedWinnerName, int expectedWinnerPoints, String stepId) {
        gameResultsTestSuite.verifyGameWinner(expectedWinnerName, expectedWinnerPoints, stepId);
    }

    @Then("verify the Game Results leaderboard displayed as follows {string}:")
    public void verifyGameResultsLeaderboard(String stepId, Map<String, String> expectedLeaderboardData) {
        gameResultsTestSuite.verifyGameResultsLeaderboard(stepId, expectedLeaderboardData);
    }
    //endregion ----------------------------------------------------------------
}
