package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefinitions", "core", "hooks"},  // ✅ added "hooks"
        tags = "@positive and @Login",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/Cucumber.json"
        }
)
public final class TestRunner extends AbstractTestNGCucumberTests {}