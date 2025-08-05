package runners;

import core.DriverManager;
import core.Reporter;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepdefinitions",
        dryRun = true,
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/Cucumber.json"
        }
)
public class TestRunner extends AbstractTestNGCucumberTests {
    @AfterSuite
    public void tearDown() {
        Reporter.flushReports();
        DriverManager.quitDriver();
    }
}