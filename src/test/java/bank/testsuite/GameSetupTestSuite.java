package bank.testsuite;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameSetupTestSuite {
    private final BankTestContext context;

    public GameSetupTestSuite(BankTestContext context) {
        this.context = context;
    }

    public void addPlayerName(String playerName) {
        context.gameSetupScreen().addPlayerName(playerName);
    }

    public void addPlayersToGame(List<String> playerNames) {
        context.gameSetupScreen().addPlayers(playerNames);
    }

    public void selectRoundsForGame(String rounds) {
        context.gameSetupScreen().selectRounds(rounds);
    }

    public void startGame() {
        context.gameSetupScreen().startGame();
    }

    public void verifyGameSetupScreen() {
        assertEquals("\uD83E\uDD11 BANK \uD83E\uDD11", context.gameSetupScreen().getTitle());
    }

    public void verifyRoundsSelected(String rounds, String stepId) {
        assertEquals(rounds, context.gameSetupScreen().getSelectedRound());
        context.takeScreenshot(stepId);
    }
}
