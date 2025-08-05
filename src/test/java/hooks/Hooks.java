package hooks;

import core.DriverManager;
import core.Reporter;
import io.cucumber.java.*;

public class Hooks {

    @Before(order = 0)
    public void initScenario() {
        // Initialize driver and reporter for each scenario
        DriverManager.getDriver();
        Reporter.initReporter();
    }

    @Before(order = 1)
    public void createTest(Scenario scenario) {
        Reporter.createTest(scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            Reporter.logStep("Scenario failed: " + scenario.getName(), Status.FAILED);
        }
        DriverManager.quitDriver();
    }

    @AfterAll
    public static void finalCleanup() {
        Reporter.flushReports();
    }
}