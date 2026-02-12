
# Bank Dice Game Test Suite

This is an automation test suite for the **Bank Dice Game**, a web application, built using Java, Appium, and Cucumber. It facilitates automated testing to verify the application's functionality and user experience.

---

## Prerequisites

Before running the test suite, ensure you have the following installed:

- **Java Development Kit (JDK)** version 11 or higher
- **Appium Server** (latest stable version)
- **Web Browser Drivers** (e.g., ChromeDriver for Chrome, GeckoDriver for Firefox) compatible with your browser
- **Maven** (for build and dependency management)
- A supported web browser (Chrome, Firefox, etc.)
- Optional: an emulator or physical device if testing on mobile (though for web testing, a desktop browser is typical)

---

## Setup Instructions

### Clone the Repository

```bash
git clone https://github.com/yourusername/bank-dice-game-test-suite.git
cd bank-game-test-suite-appium
```

### Install Dependencies

Use Maven to install dependencies:

```bash
mvn clean install
```

### Configure Environment

- Ensure that the appropriate web driver (e.g., ChromeDriver) is available in your system PATH or configured in your project.
- Make sure the Appium server is running if you plan to run mobile tests.
- No additional environment configuration is typically required unless specified.

---

## Running Tests

### Using IntelliJ IDEA

- Open the project in IntelliJ IDEA
- Navigate to the feature files `src/test/resources/bank`
- Right-click on the desired feature file and select **Run** to execute the tests.
- The tests will run and output results in the console.
- Screenshots of the test execution will be saved in `reports/screenshots`.

### Using Maven Command Line

Alternatively, you can run all tests via Maven:

```bash
mvn test
```

### Running Specific Scenarios

Use Cucumber tags to execute particular tests:

```bash
mvn test -Dcucumber.options="--tags @smoke"
```

---

## Test Structure

- **Features:** Contains Cucumber feature files describing test scenarios.
- **Step Definitions:** Java classes that implement the steps.

---

## Reports and Artifacts

- Test results are printed in the console.
- Screenshots are saved under `reports/screenshots` for each verification step.
- Reports can be further customized as needed.

---

## Troubleshooting

- **Web driver issues:** Ensure the correct driver is installed and accessible.
- **Browser not opening:** Verify browser installation and driver configuration.
- **Appium server issues:** Confirm Appium is running if testing mobile.
