package hooks;

import core.DriverManager;
import core.Reporter;
import io.cucumber.java.*;

import com.aventstack.extentreports.Status;

import static core.Reporter.*;

public class Hooks {

    @BeforeAll
    public static void beforeAll() {
        System.out.println("[HOOKS] Test run started.");
        Reporter.init(); // Initialize report once per run
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        System.out.println("🔹 [HOOKS] Scenario started: " + scenario.getName());
        DriverManager.getDriver();
        Reporter.createTest(scenario.getName());
        Reporter.logStep("Scenario started: " + scenario.getName(), Status.INFO);
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = DriverManager.captureScreenshot();
            scenario.attach(screenshot, "image/png", scenario.getName());
            Reporter.logStep("Scenario FAILED: " + scenario.getName(), Status.FAIL);
        } else {
            Reporter.logStep("Scenario PASSED: " + scenario.getName(), Status.PASS);
        }
     //   DriverManager.quitDriver();
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("[HOOKS] Test run completed.");
        Reporter.flush(); // Flush report after all tests
    }
}
