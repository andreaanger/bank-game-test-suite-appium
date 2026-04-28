package bank.testsuite;

public class GeneralTestSuite {
    private final BankTestContext context;

    public GeneralTestSuite(BankTestContext context) {
        this.context = context;
    }

    public void navigateToBankApp() {
        context.getDriver().get(context.getAppUrl());
    }

    public void verifyFullScreenMessage(String expectedMessage, String stepId) {
        context.verifyFullScreenMessage(expectedMessage, stepId);
    }
}
