package bank.testsuite;

public class RollTestSuite {
    private final BankTestContext context;
    private final GeneralTestSuite generalTestSuite;
    private final GameHomeTestSuite gameHomeTestSuite;

    public RollTestSuite(
            BankTestContext context,
            GeneralTestSuite generalTestSuite,
            GameHomeTestSuite gameHomeTestSuite
    ) {
        this.context = context;
        this.generalTestSuite = generalTestSuite;
        this.gameHomeTestSuite = gameHomeTestSuite;
    }

    public void playerRoll(String rollValue) {
        try {
            context.rollScreen().clickRollValue(rollValue);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void playThroughInitialRolls() {
        playerRoll("7");
        playerRoll("7");
        playerRoll("7");
    }

    public void playThroughRound(int currentRound, int totalRounds, int stepNum) {
        playThroughInitialRolls();
        generalTestSuite.verifyFullScreenMessage(
                "we're LIVE!",
                "Step_" + stepNum + "a_Users play 3 turns - full screen message indicating game is live is displayed"
        );

        playerRoll("7");
        int newRound = currentRound + 1;
        String newRoundMessage = totalRounds == newRound ? "LAST ROUND" : String.format("ROUND %d", newRound);

        generalTestSuite.verifyFullScreenMessage(
                newRoundMessage,
                String.format("Step_%db_User rolls a 7 - round ends and %s full screen message is displayed", stepNum, newRoundMessage)
        );

        tapOnCloseButtonOnRollScreen();
        gameHomeTestSuite.verifyRoundDuringGame(
                newRound,
                totalRounds,
                String.format("Step_%dc_User rolls a 7 - round is incremented to %d of %d", stepNum, newRound, totalRounds)
        );
    }

    public void tapOnBankButtonOnRollScreen() {
        context.rollScreen().tapBankButton();
    }

    public void tapOnCloseButtonOnRollScreen() {
        context.rollScreen().tapCloseButton();
    }
}
