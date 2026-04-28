package bank.testsuite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankPlayerTestSuite {
    private final BankTestContext context;

    public BankPlayerTestSuite(BankTestContext context) {
        this.context = context;
    }

    public void tapOnBankButtonOnBankScreen() {
        context.bankPlayerScreen().tapBankPlayersButton();
    }

    public void selectPlayerToBank(String playerName) {
        context.bankPlayerScreen().selectPlayer(playerName);
    }

    public void verifyBankablePlayers(String stepId, List<String> bankablePlayers) {
        bankablePlayers = new ArrayList<>(bankablePlayers);
        List<String> actualBankablePlayers = context.bankPlayerScreen().getBankablePlayers();

        Collections.sort(bankablePlayers);
        Collections.sort(actualBankablePlayers);

        String expected = String.join(",", bankablePlayers);
        String actual = String.join(",", actualBankablePlayers);

        assertEquals(expected, actual);
        context.takeScreenshot(stepId);
    }
}
