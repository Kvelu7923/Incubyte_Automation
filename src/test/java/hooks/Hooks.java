package hooks;

import com.aventstack.extentreports.Status;
import core.DriverManager;
import core.Reporter;
import io.cucumber.java.*;
import org.openqa.selenium.OutputType;
import static core.Reporter.logStep;

public class Hooks {

    @Before
    public void beforeScenario(Scenario scenario) {
        // Initialize driver
        DriverManager.getDriver();

        Reporter.init();
        Reporter.createTest(scenario.getName());

        logStep("Scenario started: " + scenario.getName(), Status.INFO);
    }

    @After
    public void afterScenario(Scenario scenario) {
        // Cucumber-native screenshot attachment
        if (scenario.isFailed()) {
            byte[] screenshot = DriverManager.captureScreenshot();
            scenario.attach(screenshot, "image/png", scenario.getName());
            logStep("Scenario FAILED: " + scenario.getName(), Status.FAIL);
        } else {
            logStep("Scenario PASSED: " + scenario.getName(), Status.PASS);
        }

        // Clean up driver
        DriverManager.quitDriver();
    }

    @AfterAll
    public static void afterAll() {
        Reporter.flush();
    }
}